package com.bitbakery.plugin.translator;

import com.google.api.translate.Translate;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.VisualPosition;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;

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

        // TODO - We should be able to configure whether or not we want to select every time, or whether we want to always translate to the same target language
        TargetLanguageDialog dlg = new TargetLanguageDialog();
        final String target = dlg.showDialog(getCaretPosition(ed));

        if (target != null) {
            // TODO - This should be configurable, defaulting to the defaultLanguage
            final String source = Locale.getDefault().getLanguage();

            (new WriteCommandAction.Simple(project) {
                protected void run() throws Throwable {
                    try {
                        SelectionModel sel = ed.getSelectionModel();
                        String text = sel.getSelectedText();
                        if (StringUtil.isEmptyOrSpaces(text)) {
                            return;
                        }
                        ed.getDocument().replaceString(
                                sel.getSelectionStart(),
                                sel.getSelectionEnd(),
                                Translate.translate(text, source, target));
                        
                    } catch (Exception ex) {
                        // TODO - Real error handling might be nice...
                        ex.printStackTrace();
                    }
                }
            }).execute();
        }
    }

    private Point getCaretPosition(Editor ed) {
        VisualPosition caret = ed.getCaretModel().getVisualPosition();
        Point pt = ed.visualPositionToXY(caret);
        SwingUtilities.convertPointToScreen(pt, ed.getContentComponent());
        return pt;
    }
}
