package com.bitbakery.plugin.translator;

import javax.swing.*;

/**
 * The configuration form for the Translator plugin. This is accessible from the IntelliJ settings dialog.
 */
public class TranslatorConfigForm {
    private JCheckBox isTargetLanguageSticky;
    private JCheckBox isSelectionSticky;
    private JComboBox targetLanguages;
    private JCheckBox isSourceDefaultLanguage;
    private JComboBox sourceLanguages;
    private JPanel rootComponent;

    public JPanel getRootComponent() {
        return rootComponent;
    }

    public void setData(TranslatorConfig data) {
        isTargetLanguageSticky.setSelected(data.isTargetLanguageSticky());
        isSelectionSticky.setSelected(data.isSelectionSticky());
        isSourceDefaultLanguage.setSelected(data.isSourceDefaultLanguage());
        targetLanguages.setSelectedItem(data.getTargetLanguage());
        sourceLanguages.setSelectedItem(data.getSourceLanguage());
    }

    public void getData(TranslatorConfig data) {
        data.setTargetLanguageSticky(isTargetLanguageSticky.isSelected());
        data.setSelectionSticky(isSelectionSticky.isSelected());
        data.setSourceDefaultLanguage(isSourceDefaultLanguage.isSelected());
    }

    public boolean isModified(TranslatorConfig data) {
        return isModified(isTargetLanguageSticky, data.isTargetLanguageSticky())
                || isModified(isSelectionSticky, data.isSelectionSticky())
                || isModified(isSourceDefaultLanguage, data.isSourceDefaultLanguage())
                || isModified(sourceLanguages, data.getSourceLanguage())
                || isModified(targetLanguages, data.getTargetLanguage());
    }

    private boolean isModified(JCheckBox checkBox, boolean data) {
        return checkBox.isSelected() != data;
    }

    private boolean isModified(JComboBox comboBox, String data) {
        return comboBox.getSelectedItem() != null ? !comboBox.getSelectedItem().equals(data) : data != null;
    }
}
