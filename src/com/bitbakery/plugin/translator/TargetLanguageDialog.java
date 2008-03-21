package com.bitbakery.plugin.translator;

import static com.google.api.translate.Language.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;

/**
 * Displays the target languages to which we can translate the selcted string.
 */
public class TargetLanguageDialog extends JDialog {
    private static HashMap<String, String> LANGUAGES;

    static {
        LANGUAGES = new HashMap<String, String>();
        LANGUAGES.put(display(ARABIC), ARABIC);
        LANGUAGES.put(display(CHINESE), CHINESE);
        LANGUAGES.put(display(CHINESE_SIMPLIFIED), CHINESE_SIMPLIFIED);
        LANGUAGES.put(display(DUTCH), DUTCH);
        LANGUAGES.put(display(ENGLISH), ENGLISH);
        LANGUAGES.put(display(FRENCH), FRENCH);
        LANGUAGES.put(display(GERMAN), GERMAN);
        LANGUAGES.put(display(GREEK), GREEK);
        LANGUAGES.put(display(ITALIAN), ITALIAN);
        LANGUAGES.put(display(JAPANESE), JAPANESE);
        LANGUAGES.put(display(KOREAN), KOREAN);
        LANGUAGES.put(display(PORTUGESE), PORTUGESE);
        LANGUAGES.put(display(RUSSIAN), RUSSIAN);
        LANGUAGES.put(display(SPANISH), SPANISH);
    }

    private static String display(final String lang) {
        return new Locale(lang).getDisplayLanguage();
    }

    private JList list;
    private String selectedLanguage;

    public TargetLanguageDialog() throws HeadlessException {
        setUndecorated(true);
        setModal(true);
        add(buildLanguageList());
        pack();
    }

    private JList buildLanguageList() {
        list = new JList(new HashSet(LANGUAGES.keySet()).toArray());
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.VK_ENTER) {
                    selectedLanguage = LANGUAGES.get(list.getSelectedValue());
                    close(event);
                } else if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    close(event);
                }
            }
        });
        list.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                selectedLanguage = LANGUAGES.get(list.getSelectedValue());
                close(event);
            }
        });
        list.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent event) {
                setVisible(false);
                dispose();
            }
        });
        return list;
    }

    public String showDialog(Point location) {
        setLocation(location);
        setVisible(true);
        return selectedLanguage;
    }

    private void close(InputEvent event) {
        setVisible(false);
        event.consume();
        dispose();
    }
}
