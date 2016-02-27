package br.edu.ifce.ppd.testproject.view;

import javax.swing.*;
import javax.swing.text.DefaultCaret;

/**
 * Created by andrecoelho on 2/20/16.
 */
public class LogView extends JTextArea {

    public LogView() {
        init();
    }

    private void init() {
        setEditable(false);
        setWrapStyleWord(true);
        setLineWrap(true);

        DefaultCaret caret = (DefaultCaret) getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
    }
}
