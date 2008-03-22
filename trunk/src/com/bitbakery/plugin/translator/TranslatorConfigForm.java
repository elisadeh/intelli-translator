package com.bitbakery.plugin.translator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

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

    public TranslatorConfigForm() {
        for (String source : Languages.getSourceLanguages()) {
            sourceLanguages.addItem(source);
        }
        sourceLanguages.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent event) {
                targetLanguages.removeAllItems();
                String source = (String) sourceLanguages.getSelectedItem();
                for (String target : Languages.getTargetLanguages(source)) {
                    targetLanguages.addItem(target);
                }
            }
        });

        isSourceDefaultLanguage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                sourceLanguages.setEnabled(!isSourceDefaultLanguage.isSelected());
            }
        });

        isTargetLanguageSticky.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                targetLanguages.setEnabled(isTargetLanguageSticky.isSelected());
            }
        });
    }

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
