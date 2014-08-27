/*******************************************************************************
 * Copyright (c) 2010, 2014 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.tests.swtbot;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.editparts.LayerManager;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramRootEditPart;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoration;
import org.eclipse.sirius.tests.swtbot.support.api.AbstractSiriusSwtBotGefTestCase;
import org.eclipse.sirius.tests.swtbot.support.api.business.UIDiagramRepresentation;
import org.eclipse.sirius.tests.swtbot.support.api.business.UIResource;
import org.eclipse.sirius.tests.swtbot.support.api.condition.CheckSelectedCondition;
import org.eclipse.sirius.tests.swtbot.support.api.editor.SWTBotSiriusHelper;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditPart;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.waits.ICondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.junit.Assert;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * Test class that after semantic element deletion in REFRESH_AUTO mode to
 * false, the Property view should not display properties on the remaining
 * orphan View.
 * 
 * @author edugueperoux
 */
public class EmptyPropertyViewAfterDeletionTest extends AbstractSiriusSwtBotGefTestCase {

    private static final String DATA_UNIT_DIR = "data/unit/refresh/VP-1950/";

    private static final String REPRESENTATION_DEFINITION_NAME = "VP_1950_Diagram";

    private static final String REPRESENTATION_NAME = "new " + REPRESENTATION_DEFINITION_NAME;

    private static final String VIEWPOINT_NAME = "vp_1950";

    private static final String SEMANTIC_RESOURCE_NAME = "VP-1950.ecore";

    private static final String SESSION_RESOURCE_NAME = "VP-1950.aird";

    private static final String MODELER_RESOURCE_NAME = "VP-1950.odesign";

    private UIDiagramRepresentation diagram;

    private SWTBotGefEditPart node1Bot;

    private SWTBotGefEditPart node2Bot;

    private SWTBotGefEditPart edgeBot;

    private int nbStatusInErrorLogBefore;

    /**
     * This test must be done in AUTO_REFRESH mode to false.
     * 
     * {@inheritDoc}
     */
    @Override
    protected boolean getAutoRefreshMode() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onSetUpBeforeClosingWelcomePage() throws Exception {
        copyFileToTestProject(Activator.PLUGIN_ID, DATA_UNIT_DIR, SEMANTIC_RESOURCE_NAME, SESSION_RESOURCE_NAME, MODELER_RESOURCE_NAME);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onSetUpAfterOpeningDesignerPerspective() throws Exception {
        sessionAirdResource = new UIResource(designerProject, "/", SESSION_RESOURCE_NAME);
        localSession = designerPerspective.openSessionFromFile(sessionAirdResource, true);
        diagram = localSession.getLocalSessionBrowser().perCategory().selectViewpoint(VIEWPOINT_NAME).selectRepresentation(REPRESENTATION_DEFINITION_NAME)
                .selectRepresentationInstance(REPRESENTATION_NAME, UIDiagramRepresentation.class).open();

        initEditor();

        node1Bot = editor.getEditPart("Node1").parent();
        node2Bot = editor.getEditPart("Node2").parent();
        edgeBot = editor.getEditPart("node2").parent();

        nbStatusInErrorLogBefore = getNbStatusInErrorLog();
    }

    private void initEditor() {
        if (diagram != null) {
            editor = diagram.getEditor();

            editor.setSnapToGrid(false);

            editor.setFocus();
        }
    }

    /**
     * Test class that after semantic node deletion in REFRESH_AUTO mode to
     * false, the Property view should not display properties on the remaining
     * orphan View.
     */
    public void testEmptyPropertyViewAfterNodeDeletionTest() {
        // Eclipse 4.x setFocus
        editor.click(0, 0);

        node1Bot.select();
        deleteSelectedElement();

        Assert.assertEquals(2, getRedCrossDecoratorNumbers());

        // DSemanticDecorator.target is not unsetted by the
        // DanglingRevRemovalTrigger, so the property section is no more
        // notified of a semantic change.
        // It is consistent with the other tab.
        node2Bot.select();
        node1Bot.select();

        // Checks that property view tabs content is empty, nothing is
        // displaying about the deleted element
        checkEmptyPropertyTabs();

        // Checks that change selection to another view with existing associated
        // semantic element show its properties in the property view
        editor.show();
        node2Bot.select();
        checkNotEmptyPropertyTabs();

        editor.show();
        edgeBot.select();
        checkEmptyPropertyTabs();

        // Undo
        undo("Delete");

        Assert.assertEquals(0, getRedCrossDecoratorNumbers());

        // Rechecks properties display on selection
        editor.show();
        node1Bot.select();
        checkNotEmptyPropertyTabs();

        editor.show();
        node2Bot.select();
        checkNotEmptyPropertyTabs();

        editor.show();
        edgeBot.select();
        checkNotEmptyPropertyTabs();

        // Delete when VP-2014 will be resolved
        setErrorCatchActive(false);
        // Redo
        redo("Delete");

        // Decomment when VP-2014 will be resolved
        // Assert.assertEquals(2, getRedCrossDecoratorNumbers());

        // Rechecks properties display on selection
        editor.show();
        node1Bot.select();
        checkEmptyPropertyTabs();

        editor.show();
        node2Bot.select();
        checkNotEmptyPropertyTabs();

        editor.show();
        edgeBot.select();
        checkEmptyPropertyTabs();

        // Decomment when VP-2014 will be resolved
        // Checks that not new Status has appeared in error log
        // Assert.assertEquals(nbStatusInErrorLogBefore,
        // getNbStatusInErrorLog());
    }

    /**
     * Test class that after semantic edge deletion in REFRESH_AUTO mode to
     * false, the Property view should not display properties on the remaining
     * orphan View.
     */
    public void testEmptyPropertyViewAfterEdgeDeletionTest() {
        // Eclipse 4.x setFocus
        editor.click(0, 0);

        ICondition checkSelected = new CheckSelectedCondition(editor, edgeBot.part());
        edgeBot.select();
        bot.waitUntil(checkSelected);
        deleteSelectedElement();

        Assert.assertEquals(1, getRedCrossDecoratorNumbers());

        // DSemanticDecorator.target is not unsetted by the
        // DanglingRevRemovalTrigger, so the property section is no more
        // notified of a semantic change.
        // It is consistent with the other tab.
        node1Bot.select();
        edgeBot.select();

        // Checks that property view tabs content is empty, nothing is
        // displaying about the deleted element
        checkEmptyPropertyTabs();

        // Checks that change selection to another view with existing associated
        // semantic element show its properties in the property view
        editor.show();
        node1Bot.select();
        checkNotEmptyPropertyTabs();

        editor.show();
        node2Bot.select();
        checkNotEmptyPropertyTabs();

        // Undo
        undo();

        Assert.assertEquals(0, getRedCrossDecoratorNumbers());

        // Rechecks properties display on selection
        editor.show();
        edgeBot.select();
        checkNotEmptyPropertyTabs();

        editor.show();
        node1Bot.select();
        checkNotEmptyPropertyTabs();

        editor.show();
        node2Bot.select();
        checkNotEmptyPropertyTabs();

        // Delete when VP-2014 will be resolved
        setErrorCatchActive(false);

        // Redo
        redo();

        // Decomment when VP-2014 will be resolved
        // Assert.assertEquals(1, getRedCrossDecoratorNumbers());

        // Rechecks properties display on selection
        editor.show();
        edgeBot.select();
        checkEmptyPropertyTabs();

        editor.show();
        node1Bot.select();
        checkNotEmptyPropertyTabs();

        editor.show();
        node2Bot.select();
        checkNotEmptyPropertyTabs();

        // Decomment when VP-2014 will be resolved
        // Checks that not new Status has appeared in error log
        // Assert.assertEquals(nbStatusInErrorLogBefore,
        // getNbStatusInErrorLog());
    }

    /**
     * Get the numbers of Red cross decorators (used to indicate a delete
     * semantic element of a figure in REFRESH_AUTO mode to false).
     * 
     * @return the numbers of Red cross decorators
     */
    private int getRedCrossDecoratorNumbers() {
        int redCrossDecoratorNumbers = 0;
        EditPart rootEditPart = editor.rootEditPart().part();
        LayerManager layerManager = LayerManager.Helper.find(rootEditPart);
        IFigure decorationLayer = layerManager.getLayer(DiagramRootEditPart.DECORATION_PRINTABLE_LAYER);
        Iterable<IDecoration> decorations = Iterables.filter(decorationLayer.getChildren(), IDecoration.class);
        redCrossDecoratorNumbers += Lists.newArrayList(decorations).size();
        return redCrossDecoratorNumbers;
    }

    /**
     * Checks that on current diagram selection the Property view tabs are noy
     * empty.
     */
    private void checkNotEmptyPropertyTabs() {
        SWTBotView propertyView = bot.viewByTitle("Properties");
        propertyView.setFocus();
        SWTBot propertyViewBot = propertyView.bot();

        SWTBotSiriusHelper.selectPropertyTabItem("Semantic");
        SWTBotTree swtBotTree = propertyViewBot.tree();
        Assert.assertNotSame(0, swtBotTree.getAllItems().length);

        SWTBotSiriusHelper.selectPropertyTabItem("Style");
        swtBotTree = propertyViewBot.tree();
        Assert.assertNotSame(0, swtBotTree.getAllItems().length);

        SWTBotSiriusHelper.selectPropertyTabItem("Appearance");
        // Checks if the Appearance tab is empty, if not the focused widget is a
        // text widget otherwise it is a ScrolledComposite
        Assert.assertTrue(propertyViewBot.getFocusedWidget() instanceof Text);

    }

    /**
     * Checks that on current diagram selection the Property view tabs are
     * empty.
     */
    private void checkEmptyPropertyTabs() {
        SWTBotView propertyView = bot.viewByTitle("Properties");
        propertyView.setFocus();
        SWTBot propertyViewBot = propertyView.bot();

        SWTBotSiriusHelper.selectPropertyTabItem("Semantic");
        SWTBotTree swtBotTree = propertyViewBot.tree();
        Assert.assertEquals(0, swtBotTree.getAllItems().length);

        SWTBotSiriusHelper.selectPropertyTabItem("Style");
        swtBotTree = propertyViewBot.tree();
        Assert.assertEquals(0, swtBotTree.getAllItems().length);

        SWTBotSiriusHelper.selectPropertyTabItem("Appearance");
        // Checks if the Appearance tab is empty, i.e. if not the focused widget
        // is a
        // text widget otherwise it is a ScrolledComposite
        Assert.assertTrue(propertyViewBot.getFocusedWidget() instanceof ScrolledComposite);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void tearDown() throws Exception {

        diagram = null;

        node1Bot = null;
        node2Bot = null;
        edgeBot = null;

        super.tearDown();
    }

}