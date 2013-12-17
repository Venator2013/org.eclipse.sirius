/*******************************************************************************
 * Copyright (c) 2007, 2008 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.diagram.internal.view.factories;

import org.eclipse.sirius.diagram.internal.edit.parts.DNode3EditPart;
import org.eclipse.sirius.diagram.internal.edit.parts.NotationViewIDs;

/**
 * @was-generated
 */
public class DNode3ViewFactory extends AbstractDNodeViewFactory {

    @Override
    protected int getVisualId() {
        return DNode3EditPart.VISUAL_ID;
    }

    @Override
    protected int getNameVisualId() {
        return NotationViewIDs.DNODE_NAME_3_EDIT_PART_VISUAL_ID;
    }
}
