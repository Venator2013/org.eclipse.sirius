/**
 * Copyright (c) 2009, 2014 THALES GLOBAL SERVICES
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *      Obeo - Initial API and implementation
 */
package org.eclipse.sirius.tests.swtbot.support.api.business;

import java.util.Collection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.business.api.session.SessionListener;
import org.eclipse.sirius.business.api.session.SessionManager;
import org.eclipse.sirius.business.api.session.SessionManagerListener;
import org.eclipse.sirius.tests.swtbot.support.api.business.UISessionCreationWizardFlow.SessionChoice;
import org.eclipse.sirius.tests.swtbot.support.api.condition.TreeItemAvailableCondition;
import org.eclipse.sirius.tests.swtbot.support.internal.business.UISessionCreationWizard;
import org.eclipse.sirius.tests.swtbot.support.utils.SWTBotUtils;
import org.eclipse.sirius.viewpoint.SiriusPlugin;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.waits.Conditions;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;

import com.google.common.collect.Lists;

/**
 * Object to manage graphical operations on perspectives.
 * 
 * @author dlecan
 */
public class UIPerspective {

    private static final String VIEWPOINT = "Sirius";

    // Seconds
    private static final int TIMEOUT = 60;

    private static final String WIZARDS_LIST_TITLE = "New";

    private static final String REPRESENTATIONS_FILE_LABEL = "Representations File";

    /**
     * Inner session listener to track session changes
     * 
     * @author dlecan
     */
    private final class OpenedSessionListener extends SessionManagerListener.Stub {

        private final CountDownLatch sessionOpenedSignal;

        private Session openedSession;

        /**
         * @param sessionOpenedSignal
         */
        private OpenedSessionListener(final CountDownLatch sessionOpenedSignal) {
            this.sessionOpenedSignal = sessionOpenedSignal;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void notifyAddSession(final Session newSession) {
            openedSession = newSession;
            sessionOpenedSignal.countDown();
        }

        /**
         * Returns the openedSession.
         * 
         * @return The openedSession.
         */
        public Session getOpenedSession() {
            return openedSession;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void notify(final Session updated, final int notification) {
            switch (notification) {
            case SessionListener.OPENED:
                if (openedSession == updated) {
                    sessionOpenedSignal.countDown();
                }
                break;
            case SessionListener.REPRESENTATION_CHANGE:
                openedSession = updated;
                // Twice here
                sessionOpenedSignal.countDown();
                sessionOpenedSignal.countDown();
                break;
            default:
                // Nothing
                break;
            }
        }
    }

    private final SWTWorkbenchBot bot;

    /**
     * Constructor.
     * 
     * @param bot
     *            SWTBot
     */
    public UIPerspective(final SWTWorkbenchBot bot) {
        this.bot = bot;
    }

    /**
     * Create a project.
     * 
     * @param projectName
     *            name of the created project
     * @return Current {@link UIProject}.
     */
    public UIProject createProject(final String projectName) {
        final IProjectDescription projectDescription = ResourcesPlugin.getWorkspace().newProjectDescription(projectName);
        final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
        try {
            if (!project.exists()) {
                project.create(projectDescription, new NullProgressMonitor());
            }
            project.open(new NullProgressMonitor());
        } catch (final CoreException e) {
            // Propagate as runtime exception
            throw new RuntimeException(e);
        }
        return new UIProject(projectName);
    }

    /**
     * Delete the project.
     * 
     * @param project
     *            name of the project to delete.
     */
    public void deleteProject(final UIProject project) {
        deleteProject(project.getName());
    }

    /**
     * Delete the project.
     * 
     * @param projectName
     *            name of the project to delete.
     */
    public void deleteProject(final String projectName) {
        final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
        try {
            if (project.exists()) {
                project.delete(true, true, new NullProgressMonitor());
            }
        } catch (final CoreException e) {
            // Propagate as runtime exception
            throw new RuntimeException(e);
        }
    }

    /**
     * Open the session creation wizard.
     * 
     * @return Current {@link UIPerspective}.
     */
    public SessionChoice openSessionCreationWizard() {

        openRepresentationsFileWizard();

        return new UISessionCreationWizard();
    }

    /**
     * Open the session creation wizard from a specific semantic resource.
     * 
     * @param uiResource
     *            {@link UIResource} to use to openthe wizard.
     * @return Current {@link UIPerspective}.
     */
    public SessionChoice openSessionCreationWizardFromSemanticResource(final UIResource uiResource) {

        uiResource.getProject().selectResource(uiResource);

        openRepresentationsFileWizard();

        return new UISessionCreationWizard(uiResource);

    }

    /**
     * Opens the "New Representation Files" wizard through the File > New >
     * Other... menu.
     */
    private void openRepresentationsFileWizard() {
        bot.menu("File").menu(UIPerspective.WIZARDS_LIST_TITLE).menu("Other...").click();

        SWTBot wizardListBot = bot.activeShell().bot();
        wizardListBot.waitUntil(Conditions.shellIsActive(UIPerspective.WIZARDS_LIST_TITLE));

        wizardListBot.text().setText(UIPerspective.REPRESENTATIONS_FILE_LABEL);

        SWTBotTree wizardsTree = wizardListBot.tree();

        wizardsTree.setFocus();

        SWTBotTreeItem viewpointCategory = null;
        try {
            viewpointCategory = wizardsTree.getTreeItem(UIPerspective.VIEWPOINT);
        } catch (WidgetNotFoundException e) {
            // Accessing the tree item can fail when launching the test suite
            // with jenkins. Adding the following condition in a catch section
            // seems to fix it.
            bot.waitUntil(new TreeItemAvailableCondition(wizardsTree, UIPerspective.VIEWPOINT, true));
            viewpointCategory = wizardsTree.getTreeItem(UIPerspective.VIEWPOINT);
        }
        viewpointCategory.expand();

        SWTBotTreeItem representationsFileNode = viewpointCategory.getNode(UIPerspective.REPRESENTATIONS_FILE_LABEL);

        representationsFileNode.select();

        wizardListBot.button("Next >").click();

        bot.waitUntil(Conditions.shellIsActive("New Representations File"));
    }

    /**
     * Open directly a session.
     * 
     * @param uiLocalSessionResource
     *            <code>.aird</code> file to use to open the local session.
     * @return UI local session.
     */
    public UILocalSession openSessionFromFile(final UIResource uiLocalSessionResource) {
        return openSessionFromFile(uiLocalSessionResource, false);
    }

    /**
     * Open directly a session.
     * 
     * @param uiLocalSessionResource
     *            <code>.aird</code> file to use to open the local session.
     * @param useMoreThanOneSemanticFiles
     *            true if the session uses more than one semantic files
     *            (fragmented file, more complex use case, ...).
     * @return UI local session.
     */
    public UILocalSession openSessionFromFile(final UIResource uiLocalSessionResource, final boolean useMoreThanOneSemanticFiles) {
        // Need to wait later opening of session.
        final SessionManager sessionManager = SessionManager.INSTANCE;

        final CountDownLatch sessionOpenedSignal = new CountDownLatch(2);

        final OpenedSessionListener openedSessionListener = new OpenedSessionListener(sessionOpenedSignal);
        sessionManager.addSessionsListener(openedSessionListener);

        // Open session
        uiLocalSessionResource.openSession();

        SWTBotUtils.waitProgressMonitorClose("Open session");

        try {
            // Wait until session is opened.
            final boolean awaitRes = sessionOpenedSignal.await(UIPerspective.TIMEOUT, TimeUnit.SECONDS);

            Session openedSession;
            if (awaitRes) {
                openedSession = openedSessionListener.getOpenedSession();
            } else {
                Assert.assertTrue("No session is opened!", sessionManager.getSessions().size() > 0);
                // There is a problem with listeners
                // Try to get "last" session anyway
                // This may lead to unexpected behavior as we are not sure to
                // get the expected session !
                openedSession = Lists.newLinkedList(sessionManager.getSessions()).getLast();
                SiriusPlugin.getDefault().warning("Session is graphically opened but session listener was not properly notified.", null);
            }

            final Collection<Resource> semanticResources = openedSession.getSemanticResources();

            if (useMoreThanOneSemanticFiles) {
                MatcherAssert.assertThat("Semantic resource not found", semanticResources.size(), Matchers.not(Matchers.is(0)));
            } else {
                // Only one semantic resource should be found !
                if (semanticResources.size() > 1) {
                    StringBuffer names = new StringBuffer();
                    for (Resource resource : semanticResources) {
                        names.append(resource.getURI().toPlatformString(true));
                        names.append(", ");
                    }
                    names.delete(names.length() - 2, names.length());
                    Assert.fail("Too many semantic resources, only one semantic resource is expected. List of semantic resources: " + names);
                }
            }

            final Resource semanticResource = semanticResources.iterator().next();

            final UIResource uiSemanticResource = UIResource.createFromResource(semanticResource);

            return new UILocalSession(uiSemanticResource, uiLocalSessionResource, openedSession);

        } catch (final InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            sessionManager.removeSessionsListener(openedSessionListener);
        }

    }

    /**
     * Get the opened session.
     * 
     * @param uiResource
     *            the aird resource (it could not exist)
     * @return UI local session.
     */
    public UILocalSession getOpenedSession(final UIResource uiResource) {

        final SessionManager sessionManager = SessionManager.INSTANCE;

        bot.waitUntil(new DefaultCondition() {

            @Override
            public boolean test() throws Exception {
                return !sessionManager.getSessions().isEmpty();
            }

            @Override
            public String getFailureMessage() {
                return "no session have been created";
            }
        });

        final Session session = sessionManager.getSessions().iterator().next();

        final Collection<Resource> semanticResources = session.getSemanticResources();

        final Resource semanticResource = semanticResources.iterator().next();
        final UIResource uiSemanticResource = UIResource.createFromResource(semanticResource);

        return new UILocalSession(uiSemanticResource, uiResource, session);

    }

}