/*******************************************************************************
 * Copyright (c) 2007-2013 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.viewpoint;

import org.eclipse.emf.common.util.EList;
import org.eclipse.sirius.viewpoint.description.NodeMapping;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>View Node List Element</b></em>'. <!-- end-user-doc -->
 * 
 * <!-- begin-model-doc --> An element of a list. <!-- end-model-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.sirius.viewpoint.DNodeListElement#getOwnedStyle <em>
 * Owned Style</em>}</li>
 * <li>{@link org.eclipse.sirius.viewpoint.DNodeListElement#getOriginalStyle
 * <em>Original Style</em>}</li>
 * <li>{@link org.eclipse.sirius.viewpoint.DNodeListElement#getActualMapping
 * <em>Actual Mapping</em>}</li>
 * <li>
 * {@link org.eclipse.sirius.viewpoint.DNodeListElement#getCandidatesMapping
 * <em>Candidates Mapping</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.sirius.viewpoint.ViewpointPackage#getDNodeListElement()
 * @model
 * @generated
 */
public interface DNodeListElement extends AbstractDNode {
    /**
     * Returns the value of the '<em><b>Origin Mapping</b></em>' reference. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Origin Mapping</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc --> <!-- begin-model-doc --> The mapping that has
     * created this element. <!-- end-model-doc -->
     * 
     * @return the value of the '<em>Origin Mapping</em>' reference.
     * @see #setActualMapping(NodeMapping)
     * @see org.eclipse.sirius.viewpoint.SiriusPackage#getViewNodeListElement_OriginMapping()
     * @model required="true"
     * @generated
     */
    NodeMapping getActualMapping();

    /**
     * Sets the value of the '
     * {@link org.eclipse.sirius.viewpoint.DNodeListElement#getActualMapping
     * <em>Actual Mapping</em>}' reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Actual Mapping</em>' reference.
     * @see #getActualMapping()
     * @generated
     */
    void setActualMapping(NodeMapping value);

    /**
     * Returns the value of the '<em><b>Candidates Mapping</b></em>' reference
     * list. The list contents are of type
     * {@link org.eclipse.sirius.viewpoint.description.NodeMapping}. <!--
     * begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> The
     * candidates mapping of this node. <!-- end-model-doc -->
     * 
     * @return the value of the '<em>Candidates Mapping</em>' reference list.
     * @see org.eclipse.sirius.viewpoint.SiriusPackage#getDNodeListElement_CandidatesMapping()
     * @model type="org.eclipse.sirius.description.NodeMapping" required="true"
     * @generated
     */
    EList<NodeMapping> getCandidatesMapping();

    /**
     * Returns the value of the '<em><b>Owned Style</b></em>' containment
     * reference. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Owned Style</em>' containment reference isn't
     * clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc --> <!-- begin-model-doc --> The style of this element.
     * <!-- end-model-doc -->
     * 
     * @return the value of the '<em>Owned Style</em>' containment reference.
     * @see #setOwnedStyle(NodeStyle)
     * @see org.eclipse.sirius.viewpoint.SiriusPackage#getViewNodeListElement_OwnedStyle()
     * @model containment="true" resolveProxies="true"
     * @generated
     */
    NodeStyle getOwnedStyle();

    /**
     * Sets the value of the '
     * {@link org.eclipse.sirius.viewpoint.DNodeListElement#getOwnedStyle
     * <em>Owned Style</em>}' containment reference. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Owned Style</em>' containment
     *            reference.
     * @see #getOwnedStyle()
     * @generated
     */
    void setOwnedStyle(NodeStyle value);

    /**
     * Returns the value of the '<em><b>Original Style</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> The
     * instance of style that is contained by the mapping. The ownedStyle
     * reference should be a copy of this style. <!-- end-model-doc -->
     * 
     * @return the value of the '<em>Original Style</em>' reference.
     * @see #setOriginalStyle(Style)
     * @see org.eclipse.sirius.viewpoint.SiriusPackage#getViewNodeListElement_OriginalStyle()
     * @model
     * @generated
     */
    Style getOriginalStyle();

    /**
     * Sets the value of the '
     * {@link org.eclipse.sirius.viewpoint.DNodeListElement#getOriginalStyle
     * <em>Original Style</em>}' reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Original Style</em>' reference.
     * @see #getOriginalStyle()
     * @generated
     */
    void setOriginalStyle(Style value);

} // DNodeListElement