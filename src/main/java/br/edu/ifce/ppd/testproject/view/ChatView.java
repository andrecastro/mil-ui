package br.edu.ifce.ppd.testproject.view;

import br.edu.ifce.ppd.testproject.controller.GameController;
import br.edu.ifce.ppd.testproject.view.helper.Assets;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

import static javax.swing.BorderFactory.createEmptyBorder;

/**
 * Created by andrecoelho on 2/14/16.
 */
public class ChatView extends JPanel {

    private GameController gameController;

    private JTextPane textView;
    private JTextArea textWrite;
    private JButton sendButton;

    private String messageTemplate = "<p style=\"padding: 5px; border-bottom: 1px solid black; width: 180px\"><b>%s: </b> %s</p>";

    public ChatView(GameController gameController) {
        this.gameController = gameController;
        this.init();
    }

    private void init() {
        textView = new JTextPane();
        textWrite = new JTextArea();
        sendButton = new JButton("Send", Assets.chat());

        textView.setEditable(false);
        textView.setContentType("text/html");
        textView.setText("");

        JScrollPane textViewPanel = new JScrollPane(textView);
        textViewPanel.setPreferredSize(new Dimension(245, 340));
        textViewPanel.setBorder(BorderFactory.createTitledBorder("Chat View"));

        textWrite.setWrapStyleWord(true);
        textWrite.setLineWrap(true);
        textWrite.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.consume();
                    sendText();
                }
            }
        });

        JScrollPane textWritePanel = new JScrollPane(textWrite);
        textWritePanel.setPreferredSize(new Dimension(245, 23));

        sendButton.setPreferredSize(new Dimension(250, 37));
        sendButton.addActionListener(e -> sendText());

        setBorder(createEmptyBorder(2, 2, 2, 2));
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(250, 447));

        add(textViewPanel, BorderLayout.NORTH);
        add(textWritePanel, BorderLayout.CENTER);
        add(sendButton, BorderLayout.SOUTH);
    }

    private void sendText() {
        String message = textWrite.getText();

        if (message != null && !message.trim().isEmpty()) {
            updateViewText(gameController.currentPlayer().getName(), message);
            gameController.sendMessage(message);
            textWrite.setText(null); // clean text area
        }
    }

    private String formatMessage(String playerName, String message) {
        return String.format(messageTemplate, playerName, message);
    }

    public void updateViewText(String playerName, String message) {
        try {
            message = message.replace("\n", "<br/>");
            HTMLDocument doc = (HTMLDocument) textView.getDocument();
            doc.insertBeforeEnd(doc.getDefaultRootElement().getElement(0), formatMessage(playerName, message));

            System.out.println(textView.getText());
        } catch (BadLocationException | IOException e) {
            e.printStackTrace();
        }
    }
}
