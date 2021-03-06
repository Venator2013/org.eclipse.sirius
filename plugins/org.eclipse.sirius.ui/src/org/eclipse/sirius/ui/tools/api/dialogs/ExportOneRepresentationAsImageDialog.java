/*******************************************************************************
 * Copyright (c) 2011 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.ui.tools.api.dialogs;

import java.io.File;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.sirius.common.tools.api.resource.ImageFileFormat;
import org.eclipse.sirius.common.ui.tools.api.util.SWTUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * Dialog used by the export one diagram to image files action to prompt the
 * user for a destination and image format.
 * 
 * @author jdupont
 * 
 */
public class ExportOneRepresentationAsImageDialog extends AbstractExportRepresentationsAsImagesDialog {

    /**
     * The id for the persistent folder setting for this dialog.
     */
    private static final String DIALOG_SETTINGS_FILE = "ExportRepresentationsAsImagesDialog.file"; //$NON-NLS-1$

    /**
     * Invalid path error message.
     */
    private static final String INVALID_PATH_MESSAGE = "Invalid path";

    /**
     * Invalid path error message.
     */
    private static final String INVALID_FILE_PATH_MESSAGE = "Invalid file path";

    /**
     * File path name field.
     */
    private static final String TO_FILE = "To file";

    /**
     * Popup dialog name export file.
     */
    private static final String EXPORT_TO_IMAGE = "Export to Image";

    /**
     * the dialog window title.
     */
    private static final String DIALOG_TITLE = "Export representation as image file";

    /**
     * file name to export.
     */
    private String fileName;

    /**
     * Creates an instance of the copy to image dialog.
     * 
     * @param shell
     *            the parent shell
     * @param path
     *            the default path to store the image or null
     * @param fileName
     *            the representation name to export
     */
    public ExportOneRepresentationAsImageDialog(Shell shell, IPath path, String fileName) {
        super(shell, path);
        this.fileName = fileName;
        initDialogSettings(path);
    }

    /**
     * 
     * {@inheritDoc}
     * 
     * @see org.eclipse.sirius.ui.tools.api.dialogs.AbstractExportRepresentationsAsImagesDialog#handleBrowseButtonPressed()
     */
    @Override
    protected void handleBrowseButtonPressed() {
        final FileDialog dialog = new FileDialog(Display.getCurrent().getActiveShell(), SWT.SAVE);
        dialog.setFilterExtensions(new String[] { "*.png;*.jpg;*.svg;*.pdf", "*.*" }); //$NON-NLS-1$ //$NON-NLS-2$       
        dialog.setText(EXPORT_TO_IMAGE);
        String currentSourceString = folderText.getText();
        File file = new File(folderText.getText());
        if (file.isFile()) {
            int lastSeparatorIndex = currentSourceString.lastIndexOf(File.separator);
            if (lastSeparatorIndex != -1) {
                dialog.setFileName(currentSourceString.substring(lastSeparatorIndex + 1, currentSourceString.length()));
            }
        } else {
            dialog.setFileName(currentSourceString);
        }
        String selectedFileName = dialog.open();

        // Change combo image format to correspond to folder text
        if (selectedFileName != null) {
            folderText.setText(selectedFileName);
            changeComboImageFormat();
        }
    }

    /**
     * 
     * {@inheritDoc}
     * 
     * @see org.eclipse.sirius.ui.tools.api.dialogs.AbstractExportRepresentationsAsImagesDialog#createImageFormatGroup(org.eclipse.swt.widgets.Composite)
     */
    @Override
    protected void createImageFormatGroup(final Composite parent) {
        final Composite composite = SWTUtil.createCompositeHorizontalFill(parent, 3, false);
        SWTUtil.createLabel(composite, IMAGE_FORMAT_LABEL);

        String[] imageSafeFormatItems = getImageSafeFormatItems();

        Control imageFormatControl;
        if (imageSafeFormatItems != null && imageSafeFormatItems.length > 0) {

            if (imageSafeFormatItems.length == 1) {
                imageFormatControl = SWTUtil.createLabel(composite, imageSafeFormatItems[0]);

            } else {
                imageFormatCombo = new Combo(composite, SWT.DROP_DOWN | SWT.READ_ONLY);
                imageFormatCombo.setItems(imageSafeFormatItems);
                imageFormatCombo.setText(imageFormat.getName());
                imageFormatControl = imageFormatCombo;
            }
        } else {
            imageFormatControl = SWTUtil.createLabel(composite, DEFAULT_VALUE.getName());
        }
        final GridData gridData = new GridData(SWT.FILL, SWT.CENTER, true, true);
        imageFormatControl.setLayoutData(gridData);

    }

    /**
     * 
     * {@inheritDoc}
     * 
     * @see org.eclipse.sirius.ui.tools.api.dialogs.AbstractExportRepresentationsAsImagesDialog#createFolderGroup(org.eclipse.swt.widgets.Composite)
     */
    @Override
    protected void createFolderGroup(final Composite parent) {
        final Composite composite = SWTUtil.createCompositeHorizontalFill(parent, 4, false);
        SWTUtil.createLabel(composite, TO_FILE);
        folderText = new Combo(composite, SWT.SINGLE | SWT.BORDER);
        restoreWidgetValues();
        IPath path = new Path(folder);
        if (isExistingFolder(path) || path.getFileExtension() == null) {
            path = path.addTrailingSeparator();
            path = path.append(fileName.replaceAll("[/\\\\]", FILE_SEPARATOR_ALTERNATIVE)); //$NON-NLS-1$
            String extension = imageFormat.getName().toLowerCase();
            path = path.addFileExtension(extension);
            String newExtensionFilePath = path.toFile().getPath();
            folder = newExtensionFilePath;
        }
        folderText.setText(folder);
        final GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.widthHint = 350;
        folderText.setLayoutData(gridData);
        final Button button = new Button(composite, SWT.PUSH);
        button.setText(BROWSE_LABEL);
        button.setLayoutData(AbstractExportRepresentationsAsImagesDialog.makeButtonData(button));
        button.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(final SelectionEvent event) {
                handleBrowseButtonPressed();
            }
        });
    }

    /**
     * {@inheritDoc} Configures the shell in preparation for opening this window
     * in it.
     * 
     * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
     */
    @Override
    protected void configureShell(final Shell shell) {
        super.configureShell(shell);
        shell.setText(DIALOG_TITLE);
    }

    /**
     * 
     * {@inheritDoc}
     * 
     * @see org.eclipse.sirius.ui.tools.api.dialogs.AbstractExportRepresentationsAsImagesDialog#restoreWidgetValues()
     */
    @Override
    protected void restoreWidgetValues() {
        IDialogSettings settings = getDialogSettings();
        if (settings != null) {
            String[] directoryNames = settings.getArray(DIALOG_SETTINGS_FILE);
            if (directoryNames == null || directoryNames.length == 0) {
                return; // ie.- no settings stored
            }
            folder = directoryNames[0];
            // destination
            for (String directoryName : directoryNames) {
                folderText.add(directoryName);
            }
        }
    }

    /**
     * 
     * {@inheritDoc}
     * 
     * @see org.eclipse.sirius.ui.tools.api.dialogs.AbstractExportRepresentationsAsImagesDialog#initDialogSettings(org.eclipse.core.runtime.IPath)
     */
    @Override
    protected void initDialogSettings(final IPath path) {
        final IDialogSettings dialogSettings = getDialogSettings();

        // By default, the folder will be the root of the filesystem, where ever
        // that may be on a system.
        folder = "/"; //$NON-NLS-1$

        if (path == null) {
            final String persistentFolder = dialogSettings.get(DIALOG_SETTINGS_FILE);
            if (persistentFolder != null) {
                folder = persistentFolder;
            }
        } else {
            folder = path.toOSString();
        }

        final String persistentImageFormat = dialogSettings.get(DIALOG_SETTINGS_IMAGE_FORMAT);
        if (persistentImageFormat == null) {
            imageFormat = AbstractExportRepresentationsAsImagesDialog.getDefaultImageFormat();
        } else {
            imageFormat = AbstractExportRepresentationsAsImagesDialog.resolveImageFormat(persistentImageFormat);
        }

    }

    /**
     * 
     * {@inheritDoc}
     * 
     * @see org.eclipse.sirius.ui.tools.api.dialogs.AbstractExportRepresentationsAsImagesDialog#saveDialogSettings()
     */
    @Override
    protected void saveDialogSettings() {
        final IDialogSettings dialogSettings = getDialogSettings();
        if (dialogSettings != null) {
            String[] directoryNames = dialogSettings.getArray(DIALOG_SETTINGS_FILE);
            if (directoryNames == null || directoryNames.length == 0) {
                directoryNames = new String[1];
                directoryNames[0] = folder;
            }
            // Get the folder path of the full file path.
            String folderPathToKeepInHistory = folder;
            IPath path = new Path(folder);
            if (!isExistingFolder(path) && path.getFileExtension() != null) {
                folderPathToKeepInHistory = path.removeLastSegments(1).toOSString();
            }

            directoryNames = addToHistory(directoryNames, folderPathToKeepInHistory);
            dialogSettings.put(DIALOG_SETTINGS_FILE, directoryNames);
            dialogSettings.put(DIALOG_SETTINGS_IMAGE_FORMAT, imageFormat.getName());
        }
    }

    /**
     * 
     * {@inheritDoc}
     * 
     * @see org.eclipse.sirius.ui.tools.api.dialogs.AbstractExportRepresentationsAsImagesDialog#validateFolderText()
     */
    @Override
    protected void validateFolderText() {
        super.validateFolderText();
        final File file = new File(folderText.getText());
        final File directory = file.getParentFile();
        final IPath path = new Path(folderText.getText());

        if (file.isDirectory()) {
            setDialogErrorState(INVALID_FILE_PATH_MESSAGE);
        } else {
            // If the name of file is not informed in export one
            // representation, the extension is add to path

            if (directory == null) {
                setDialogErrorState(INVALID_PATH_MESSAGE);
            } else if (!directory.exists()) {
                setDialogErrorState(FOLDER_NOT_EXIST_MESSAGE);
            } else if (path.toString().endsWith("/") || path.toString().endsWith(CHARACTER_EXTENSION_FILE)) { //$NON-NLS-1$
                setDialogErrorState(INVALID_PATH_MESSAGE);
            }

        }

    }

    /**
     * Add the default file name and the current selected extension to the file
     * name.
     */
    protected void modifyPathWithDefaultFileName() {
        IPath path = new Path(folderText.getText());
        // Add the default file name if needed
        if (isExistingFolder(path)) {
            path = path.append(fileName);
        }
        // Add the file extension if needed
        if (!path.toString().endsWith(CHARACTER_EXTENSION_FILE) && path.getFileExtension() == null) {
            path = path.addFileExtension(imageFormatCombo.getText().toLowerCase());
            folderText.setText(path.toOSString());
        }
        changeComboImageFormat();
    }

    @SuppressWarnings("static-access")
    private void changeComboImageFormat() {
        IPath path = new Path(folderText.getText());
        if (!isExistingFolder(path) && !path.toString().endsWith(CHARACTER_EXTENSION_FILE)) {
            String fileExtension = path.getFileExtension();
            if (fileExtension != null) {
                boolean isPresent = false;
                for (ImageFileFormat extension : SAFE_VALUES) {
                    if (extension.getName().toLowerCase().equals(fileExtension.toLowerCase())) {
                        imageFormatCombo.setText(extension.getName());
                        isPresent = true;
                        break;
                    } else if ("jpeg".equals(fileExtension.toLowerCase())) { //$NON-NLS-1$
                        imageFormatCombo.setText(imageFormat.JPG.getName());
                        isPresent = true;
                        break;
                    }
                }
                if (!isPresent) {
                    StringBuffer fileWithExtensionForbidden = new StringBuffer(folderText.getText()).append(CHARACTER_EXTENSION_FILE).append(imageFormatCombo.getText().toLowerCase());
                    folderText.setText(fileWithExtensionForbidden.toString());
                }
            }
        }

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.sirius.ui.tools.api.dialogs.AbstractExportRepresentationsAsImagesDialog#initListeners()
     */
    @Override
    protected void initListeners() {
        changeComboImageFormat();
        folderText.addModifyListener(new ModifyListener() {
            public void modifyText(final ModifyEvent e) {
                validateFolderText();
            }
        });
        folderText.addSelectionListener(new SelectionListener() {

            public void widgetSelected(SelectionEvent e) {
                final File file = new File(folderText.getText());
                if (file.isDirectory()) {
                    modifyPathWithDefaultFileName();
                }
            }

            public void widgetDefaultSelected(SelectionEvent e) {
                // Do nothing.
            }
        });
        folderText.addFocusListener(new FocusListener() {

            public void focusLost(FocusEvent e) {
                IPath path = new Path(folderText.getText());
                // Add the file extension if needed
                if (!path.toString().endsWith(CHARACTER_EXTENSION_FILE) && path.getFileExtension() == null) {
                    path = path.addFileExtension(imageFormatCombo.getText().toLowerCase());
                    folderText.setText(path.toOSString());
                }
                changeComboImageFormat();
            }

            public void focusGained(FocusEvent e) {
            }
        });
        if (imageFormatCombo != null) {
            imageFormatCombo.addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetSelected(final SelectionEvent e) {
                    imageFormat = AbstractExportRepresentationsAsImagesDialog.resolveImageFormat(imageFormatCombo.getSelectionIndex());
                    // Change extension file to correspond to combo image
                    // format selection
                    IPath path = new Path(folder);
                    if (!isExistingFolder(path) && path.getFileExtension() != null) {
                        String extension = imageFormat.getName().toLowerCase();
                        path = path.removeFileExtension();
                        path = path.addFileExtension(extension);
                        String newExtensionFilePath = path.toFile().getPath();
                        folderText.setText(newExtensionFilePath);
                    }
                }
            });

        }
    }

    private boolean isExistingFolder(IPath path) {
        boolean isExistingFolder = false;
        File file = new File(path.toOSString());
        if (file.exists() && file.isDirectory()) {
            isExistingFolder = true;
        }
        return isExistingFolder;
    }
}
