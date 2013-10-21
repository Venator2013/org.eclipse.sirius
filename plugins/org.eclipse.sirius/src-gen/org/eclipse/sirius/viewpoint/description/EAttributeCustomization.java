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
package org.eclipse.sirius.viewpoint.description;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>EAttribute Customization</b></em>'. <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>
 * {@link org.eclipse.sirius.viewpoint.description.EAttributeCustomization#getAttributeName
 * <em>Attribute Name</em>}</li>
 * <li>
 * {@link org.eclipse.sirius.viewpoint.description.EAttributeCustomization#getValue
 * <em>Value</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.sirius.viewpoint.description.DescriptionPackage#getEAttributeCustomization()
 * @model
 * @generated
 */
public interface EAttributeCustomization extends EStructuralFeatureCustomization {
    /**
     * Returns the value of the '<em><b>Attribute Name</b></em>' attribute. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Attribute Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Attribute Name</em>' attribute.
     * @see #setAttributeName(String)
     * @see org.eclipse.sirius.viewpoint.description.DescriptionPackage#getEAttributeCustomization_AttributeName()
     * @model
     * @generated
     */
    String getAttributeName();

    /**
     * Sets the value of the '
     * {@link org.eclipse.sirius.viewpoint.description.EAttributeCustomization#getAttributeName
     * <em>Attribute Name</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Attribute Name</em>' attribute.
     * @see #getAttributeName()
     * @generated
     */
    void setAttributeName(String value);

    /**
     * Returns the value of the '<em><b>Value</b></em>' attribute. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Value</em>' attribute isn't clear, there
     * really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Value</em>' attribute.
     * @see #setValue(String)
     * @see org.eclipse.sirius.viewpoint.description.DescriptionPackage#getEAttributeCustomization_Value()
     * @model dataType="org.eclipse.sirius.description.InterpretedExpression"
     * @generated
     */
    String getValue();

    /**
     * Sets the value of the '
     * {@link org.eclipse.sirius.viewpoint.description.EAttributeCustomization#getValue
     * <em>Value</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Value</em>' attribute.
     * @see #getValue()
     * @generated
     */
    void setValue(String value);

} // EAttributeCustomization