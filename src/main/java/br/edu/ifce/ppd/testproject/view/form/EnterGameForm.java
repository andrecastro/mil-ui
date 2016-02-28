package br.edu.ifce.ppd.testproject.view.form;

import javax.swing.*;
import java.awt.*;

/**
 * Created by andrecoelho on 2/21/16.
 */
public class EnterGameForm extends JPanel {


    private JLabel playerLabel;
    private JTextField playerText;

    public EnterGameForm() {
        init();
    }

    private void init() {
        playerLabel = new JLabel("Player Name");
        playerText = new JTextField();
        playerText.setPreferredSize(new Dimension(150, 30));

        setLayout(new FlowLayout());
        add(playerLabel);
        add(playerText);
    }

    public boolean hasPlayerName() {
        return playerText.getText() != null && !playerText.getText().trim().isEmpty();
    }

    public String getPlayerName() {
        return playerText.getText();
    }

}
