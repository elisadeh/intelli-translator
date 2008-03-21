package com.bitbakery.plugin.translator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Displays the target languages to which we can translate the selcted string.
 */
public class TargetLanguageDialog extends JDialog {
    private JList list;
    private String selectedLanguageCode;

    public TargetLanguageDialog(String sourceLanguage) throws HeadlessException {
        setUndecorated(true);
        setModal(true);
        add(buildLanguageList(sourceLanguage));
        pack();
    }

    private JList buildLanguageList(final String sourceLanguage) {
        list = new JList(Languages.getLanguageNames(sourceLanguage));
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        list.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.VK_ENTER) {
                    selectAndClose(event, sourceLanguage);
                } else if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    close(event);
                }
            }

            public void keyPressed(KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.VK_UP) {
                    if (list.getSelectedIndex() <= 0) {
                        list.setSelectedIndex(getLastIndex());
                        event.consume();
                    }
                } else if (event.getKeyCode() == KeyEvent.VK_DOWN) {
                    if (list.getSelectedIndex() >= getLastIndex()) {
                        list.setSelectedIndex(0);
                        event.consume();
                    }
                }
            }
        });
        list.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                selectAndClose(event, sourceLanguage);
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

    private void selectAndClose(InputEvent event, String sourceLanguage) {
        selectedLanguageCode = Languages.getLanguageCode(sourceLanguage, (String) list.getSelectedValue());
        close(event);
    }

    private void close(InputEvent event) {
        setVisible(false);
        event.consume();
        dispose();
    }

    private int getLastIndex() {
        return list.getModel().getSize() - 1;
    }

    public String showDialog(Point location) {
        setLocation(location);
        setVisible(true);
        return selectedLanguageCode;
    }
}
