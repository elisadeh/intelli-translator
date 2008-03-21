package com.bitbakery.plugin.translator;

import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.options.ConfigurationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Persists Translator configuration settings
 */
@State(name = "TranslatorConfig",
        storages = {@Storage(id = "main", file = "$APP_CONFIG$/translator-settings.xml")})
public class TranslatorConfig { // implements ApplicationComponent, Configurable, PersistentStateComponent<TranslatorConfig> {

    public boolean isTargetLanguageSticky;
    public boolean isSelectionSticky;
    public boolean isSourceDefaultLanguage;
    public String sourceLanguage;
    public String targetLanguage;

    private volatile TranslatorConfigForm form;


    public void initComponent() {
        // Do nothing
    }

    public void disposeComponent() {
        // Do nothing
    }

    @NotNull
    public String getComponentName() {
        return "TranslatorConfig";
    }


    @Nls
    public String getDisplayName() {
        return null; //TranslatorStrings.message("plugin.translator.name");
    }

    public Icon getIcon() {
        return null; //return Icons.EJB_CREATE_METHOD_ICON; // TODO - Find a better icon
    }

    @Nullable
    @NonNls
    public String getHelpTopic() {
        // TODO - How does one get a help topic created and loaded? Is this it?
        return null;
    }

    public JComponent createComponent() {
        if (form == null) {
            form = new TranslatorConfigForm();
            form.setData(this);
        }
        return form.getRootComponent();
    }

    public boolean isModified() {
        return form != null && form.isModified(this);
    }

    public void apply() throws ConfigurationException {
        if (form != null) {
            form.getData(this);
        }
    }

    public void reset() {
        if (form != null) {
            form.setData(this);
        }
    }

    public void disposeUIResources() {
        form = null;
    }

    public TranslatorConfig getState() {
        return this;
    }

    public void loadState(TranslatorConfig that) {
        this.isSelectionSticky = that.isSelectionSticky;
        this.isTargetLanguageSticky = that.isTargetLanguageSticky;
        this.isSourceDefaultLanguage = that.isSourceDefaultLanguage;
        this.sourceLanguage = that.sourceLanguage;
        this.targetLanguage = that.targetLanguage;
    }

    public boolean isTargetLanguageSticky() {
        return isTargetLanguageSticky;
    }

    public void setTargetLanguageSticky(boolean targetLanguageSticky) {
        this.isTargetLanguageSticky = targetLanguageSticky;
    }

    public boolean isSelectionSticky() {
        return isSelectionSticky;
    }

    public void setSelectionSticky(boolean selectionSticky) {
        this.isSelectionSticky = selectionSticky;
    }

    public boolean isSourceDefaultLanguage() {
        return isSourceDefaultLanguage;
    }

    public void setSourceDefaultLanguage(boolean sourceDefaultLanguage) {
        this.isSourceDefaultLanguage = sourceDefaultLanguage;
    }

    public String getSourceLanguage() {
        return sourceLanguage;
    }

    public String getTargetLanguage() {
        return targetLanguage;
    }
}
