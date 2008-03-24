package com.bitbakery.plugin.translator;

import com.google.api.translate.Translate;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.editor.VisualPosition;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;

import javax.swing.*;
import java.awt.*;

/**
 * Event handler for the "Translate" action which appears in the popup menu
 */
public class TranslationAction extends AnAction {

    public void actionPerformed(final AnActionEvent e) {
        final Editor ed = e.getData(DataKeys.EDITOR);
        if (ed == null) {
            return;
        }
        final Project project = e.getData(DataKeys.PROJECT);
        final TranslatorConfig config = ApplicationManager.getApplication().getComponent(TranslatorConfig.class);
        final Language source = Language.get(config.getSourceLanguageCode());
        final Language target = getTargetLanguage(ed, config, source);

        if (target != null) {
            (new WriteCommandAction.Simple(project) {
                protected void run() throws Throwable {
                    SelectionModel sel = ed.getSelectionModel();
                    String text = sel.getSelectedText();
                    if (StringUtil.isEmptyOrSpaces(text)) {
                        return;
                    }
                    ed.getDocument().replaceString(
                            sel.getSelectionStart(),
                            sel.getSelectionEnd(),
                            Translate.translate(text, source.code, target.code));

                    if (!config.isSelectionSticky()) {
                        sel.setSelection(ed.getCaretModel().getOffset(), ed.getCaretModel().getOffset());
                    }
                }
            }).execute();
        }
    }

    private Language getTargetLanguage(Editor ed, TranslatorConfig config, Language source) {
        if (config.isTargetLanguageSticky()) {
            return Language.get(config.getTargetLanguageCode());

        }
        TargetLanguageDialog dlg = new TargetLanguageDialog(source);
        return dlg.showDialog(getCaretPosition(ed));
    }

    private Point getCaretPosition(Editor ed) {
        VisualPosition caret = ed.getCaretModel().getVisualPosition();
        Point pt = ed.visualPositionToXY(caret);
        SwingUtilities.convertPointToScreen(pt, ed.getContentComponent());
        return pt;
    }
}
