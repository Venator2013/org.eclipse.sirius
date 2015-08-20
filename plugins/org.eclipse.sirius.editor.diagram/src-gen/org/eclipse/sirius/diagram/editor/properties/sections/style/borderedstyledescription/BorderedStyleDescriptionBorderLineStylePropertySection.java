/*******************************************************************************
 * Copyright (c) 2007, 2013 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.diagram.editor.properties.sections.style.borderedstyledescription;

// Start of user code imports

<<<<<<< HEAD
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.sirius.diagram.description.style.StylePackage;
import org.eclipse.sirius.editor.editorPlugin.SiriusEditor;
import org.eclipse.sirius.editor.properties.sections.common.AbstractComboPropertySection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

// End of user code imports

/**
 * A section for the borderColor property of a BorderedStyleDescription object.
=======
import java.util.List;

import org.eclipse.sirius.diagram.LineStyle;
import org.eclipse.sirius.diagram.description.style.StylePackage;
import org.eclipse.sirius.editor.properties.sections.common.AbstractComboPropertySection;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import org.eclipse.sirius.editor.editorPlugin.SiriusEditor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;

// End of user code imports

/**
 * A section for the borderLineStyle property of a BorderedStyleDescription
 * object.
>>>>>>> pcdavid/master
 */
public class BorderedStyleDescriptionBorderLineStylePropertySection extends AbstractComboPropertySection {
    /**
     * @see org.eclipse.sirius.diagram.editor.properties.sections.AbstractComboPropertySection#getDefaultLabelText()
     */
    protected String getDefaultLabelText() {
<<<<<<< HEAD
        return "BorderColor"; //$NON-NLS-1$
=======
        return "BorderLineStyle"; //$NON-NLS-1$
>>>>>>> pcdavid/master
    }

    /**
     * @see org.eclipse.sirius.diagram.editor.properties.sections.AbstractComboPropertySection#getLabelText()
     */
    protected String getLabelText() {
        String labelText;
<<<<<<< HEAD
        labelText = super.getLabelText() + "*:"; //$NON-NLS-1$
=======
        labelText = super.getLabelText() + ":"; //$NON-NLS-1$
>>>>>>> pcdavid/master
        // Start of user code get label text

        // End of user code get label text
        return labelText;
    }

    /**
     * @see org.eclipse.sirius.diagram.editor.properties.sections.AbstractComboPropertySection#getFeature()
     */
<<<<<<< HEAD
    protected EReference getFeature() {
        return StylePackage.eINSTANCE.getBorderedStyleDescription_BorderColor();
=======
    protected EAttribute getFeature() {
        return StylePackage.eINSTANCE.getBorderedStyleDescription_BorderLineStyle();
>>>>>>> pcdavid/master
    }

    /**
     * @see org.eclipse.sirius.diagram.editor.properties.sections.AbstractComboPropertySection#getFeatureValue(int)
     */
    protected Object getFeatureValue(int index) {
<<<<<<< HEAD
        return getFeatureValueAt(index);
=======
        return getChoiceOfValues().get(index);
>>>>>>> pcdavid/master
    }

    /**
     * @see org.eclipse.sirius.diagram.editor.properties.sections.AbstractComboPropertySection#isEqual(int)
     */
    protected boolean isEqual(int index) {
<<<<<<< HEAD
        boolean isEqual = false;
        if (getFeatureValueAt(index) == null)
            isEqual = eObject.eGet(getFeature()) == null;
        else
            isEqual = getFeatureValueAt(index).equals(eObject.eGet(getFeature()));
        return isEqual;
    }

    /**
     * Returns the value at the specified index in the choice of values for the
     * feature.
     * 
     * @param index
     *            Index of the value.
     * @return the value at the specified index in the choice of values.
     */
    protected Object getFeatureValueAt(int index) {
        List<?> values = getChoiceOfValues();
        if (values.size() < index || values.size() == 0 || index == -1) {
            return null;
        }
        return values.get(index);
    }

    /**
     * Fetches the list of available values for the feature.
     * 
     * @return The list of available values for the feature.
     */
    protected List<?> getChoiceOfValues() {
        List<?> values = Collections.emptyList();
        List<IItemPropertyDescriptor> propertyDescriptors = getDescriptors();
        for (Iterator<IItemPropertyDescriptor> iterator = propertyDescriptors.iterator(); iterator.hasNext();) {
            IItemPropertyDescriptor propertyDescriptor = iterator.next();
            if (((EStructuralFeature) propertyDescriptor.getFeature(eObject)) == getFeature())
                values = new ArrayList<Object>(propertyDescriptor.getChoiceOfValues(eObject));
        }

        // Required singleValued color reference : do not propose null.
        values.remove(null);

        // Start of user code choice of values
        // End of user code choice of values
        return values;
=======
        return getChoiceOfValues().get(index).equals(eObject.eGet(getFeature()));
    }

    /**
     * @see org.eclipse.sirius.diagram.editor.properties.sections.AbstractComboPropertySection#getEnumerationFeatureValues()
     */
    protected List<?> getChoiceOfValues() {
        return LineStyle.VALUES;
>>>>>>> pcdavid/master
    }

    /**
     * {@inheritDoc}
     */
    public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
        super.createControls(parent, tabbedPropertySheetPage);
<<<<<<< HEAD
        nameLabel.setFont(SiriusEditor.getFontRegistry().get("required"));
        // Start of user code create controls

        // End of user code create controls
    }
    // Start of user code user operations

    // End of user code user operations
=======

        nameLabel.setToolTipText("The style of the border line.");

        CLabel help = getWidgetFactory().createCLabel(composite, "");
        FormData data = new FormData();
        data.top = new FormAttachment(nameLabel, 0, SWT.TOP);
        data.left = new FormAttachment(nameLabel);
        help.setLayoutData(data);
        help.setImage(getHelpIcon());
        help.setToolTipText("The style of the border line.");

    }
>>>>>>> pcdavid/master
}
