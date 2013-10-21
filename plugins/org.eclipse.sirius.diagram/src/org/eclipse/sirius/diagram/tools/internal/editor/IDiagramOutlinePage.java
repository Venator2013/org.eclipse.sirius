/*******************************************************************************
 * Copyright (c) 2007, 2011 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.diagram.tools.internal.editor;

import org.eclipse.jface.resource.ImageDescriptor;

import org.eclipse.sirius.diagram.part.SiriusDiagramEditorPlugin;

/**
 * This interface store Resources handle for {@link DiagramOutlinePage}.
 * 
 * @author Mariot Chauvin (mchauvin)
 */
public interface IDiagramOutlinePage {

    /** The outline tip text. */
    String OUTLINE_VIEW_OUTLINE_TIP_TEXT = "Outline"; //$NON-NLS-1$

    /** The overview tip text. */
    String OUTLINE_VIEW_OVERVIEW_TIP_TEXT = "Overview"; //$NON-NLS-1$

    /** The outline icon descriptor. */
    ImageDescriptor DESC_OUTLINE = SiriusDiagramEditorPlugin.getBundledImageDescriptor("icons/outline.gif");

    /** the overview icon descriptor. */
    ImageDescriptor DESC_OVERVIEW = SiriusDiagramEditorPlugin.getBundledImageDescriptor("icons/overview.gif");

}