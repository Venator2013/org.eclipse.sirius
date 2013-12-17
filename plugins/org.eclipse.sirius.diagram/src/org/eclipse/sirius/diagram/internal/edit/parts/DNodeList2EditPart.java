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
package org.eclipse.sirius.diagram.internal.edit.parts;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.core.edithelpers.CreateElementRequestAdapter;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CreationEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.sirius.diagram.edit.api.part.AbstractDiagramListEditPart;
import org.eclipse.sirius.diagram.internal.providers.SiriusElementTypes;
import org.eclipse.sirius.diagram.part.SiriusVisualIDRegistry;

/**
 * @was-generated
 */
public class DNodeList2EditPart extends AbstractDiagramListEditPart {

    /**
     * @was-generated
     */
    public static final int VISUAL_ID = 3009;

    /**
     * @was-generated
     */
    public DNodeList2EditPart(View view) {
        super(view);
    }

    /**
     * @not-generated
     */
    protected void createDefaultEditPolicies() {
        installEditPolicy(EditPolicyRoles.CREATION_ROLE, new CreationEditPolicy() {
            public Command getCommand(Request request) {
                if (understandsRequest(request)) {
                    if (request instanceof CreateViewAndElementRequest) {
                        CreateElementRequestAdapter adapter = ((CreateViewAndElementRequest) request).getViewAndElementDescriptor().getCreateElementRequestAdapter();
                        IElementType type = (IElementType) adapter.getAdapter(IElementType.class);
                        if (type == SiriusElementTypes.DNodeListElement_3010) {
                            EditPart compartmentEditPart = getChildBySemanticHint(SiriusVisualIDRegistry.getType(DNodeListViewNodeListCompartmentEditPart.VISUAL_ID));
                            return compartmentEditPart == null ? null : compartmentEditPart.getCommand(request);
                        }
                    }
                    return super.getCommand(request);
                }
                return null;
            }
        });
        super.createDefaultEditPolicies();
    }

    /**
     * @was-generated
     */
    protected boolean addFixedChild(EditPart childEditPart) {
        if (childEditPart instanceof DNodeListName2EditPart) {
            ((DNodeListName2EditPart) childEditPart).setLabel(getPrimaryShape().getLabelFigure());
            return true;
        }
        return super.addFixedChild(childEditPart);
    }

    /**
     * @was-generated
     */
    protected void removeChildVisual(EditPart childEditPart) {
        /* workaround, we don't want the view node container to remove it's name */
        if (!(childEditPart instanceof DNodeListName2EditPart)) {
            if (removeFixedChild(childEditPart)) {
                return;
            }
            super.removeChildVisual(childEditPart);
        }
    }

    /**
     * @was-generated
     */
    public EditPart getPrimaryChildEditPart() {
        return getChildBySemanticHint(SiriusVisualIDRegistry.getType(DNodeListName2EditPart.VISUAL_ID));
    }
}
