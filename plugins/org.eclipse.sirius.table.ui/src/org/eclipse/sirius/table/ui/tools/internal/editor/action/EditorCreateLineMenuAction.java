/*******************************************************************************
 * Copyright (c) 2008, 2012 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.table.ui.tools.internal.editor.action;

import java.util.List;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import org.eclipse.sirius.table.ui.tools.internal.editor.DTableViewerManager;

/**
 * This implementation is used to create the structure viewer's "Create Line"
 * action.
 * 
 * @author <a href="mailto:laurent.redor@obeo.fr">Laurent Redor</a>
 */
public class EditorCreateLineMenuAction extends AbstractEditorCreateMenuAction<CreateLineAction> {
    /** The Id of this action. */
    public static final String ID = "CreateLineMenu";

    /**
     * This default constructor will instantiate an action given the
     * {@link #BUNDLE bundle} resources prefixed by &quot;action.save&quot;.
     */
    public EditorCreateLineMenuAction() {
        super("Create root lines", DTableViewerManager.getImageRegistry().getDescriptor(DTableViewerManager.CREATE_LINE));
        setId(ID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected List<CreateLineAction> filter(List<AbstractToolAction> createActionsForTable) {
        return Lists.newArrayList(Iterables.filter(createActionsForTable, CreateLineAction.class));
    }
}
