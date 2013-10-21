/*******************************************************************************
 * Copyright (c) 2007, 2012 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.diagram.tools.internal.preferences;

import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent;

import org.eclipse.sirius.business.api.preferences.SiriusPreferencesKeys;
import org.eclipse.sirius.tools.api.command.IDiagramCommandFactory;

/**
 * Listener of the
 * {@link org.eclipse.sirius.diagram.part.SiriusDiagramEditor}
 * preferences.
 * 
 * @author cbrun
 * 
 */
public class SiriusPreferenceChangeListener implements IPreferenceChangeListener {

    IDiagramCommandFactory factory;

    /**
     * Build a new listener.
     * 
     * @param factory
     *            editor's command factory.
     */
    public SiriusPreferenceChangeListener(final IDiagramCommandFactory factory) {
        this.factory = factory;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.core.runtime.Preferences.IPreferenceChangeListener#propertyChange(org.eclipse.core.runtime.Preferences.PreferenceChangeEvent)
     */
    public void preferenceChange(PreferenceChangeEvent event) {
        if (SiriusPreferencesKeys.PREF_AUTO_REFRESH.name().equals(event.getKey()) && event.getNewValue() instanceof String && !event.getNewValue().equals(event.getOldValue())) {
            factory.setAutoRefreshDView(Boolean.parseBoolean((String) event.getNewValue()));
        }
    }

}