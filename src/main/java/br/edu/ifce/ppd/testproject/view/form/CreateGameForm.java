package br.edu.ifce.ppd.testproject.view.form;

import javax.swing.*;
import java.awt.*;

/**
 * Created by andrecoelho on 2/21/16.
 */
public class CreateGameForm extends JPanel {


    private JTextField aliasText;
    private JTextField playerNameText;

    public CreateGameForm() {
        init();
    }

    private void init() {
        setLayout(new GridLayout(2, 2));

        JLabel aliasLabel = new JLabel("Game alias");
        aliasText = new JTextField();

        JLabel playerName = new JLabel("Player Name");
        playerNameText = new JTextField();

        add(aliasLabel);
        add(aliasText);
        add(playerName);
        add(playerNameText);

        setPreferredSize(new Dimension(300, 50));
    }

    public String getAliasValue() {
        return aliasText.getText();
    }

    public String getPalyerNameValue() {
        return playerNameText.getText();
    }

    public boolean hasAlias() {
        return aliasText.getText() != null && !aliasText.getText().trim().isEmpty();
    }

    public boolean hasPlayerName() {
        return playerNameText.getText() != null && !playerNameText.getText().trim().isEmpty();
    }

}
