package br.edu.ifce.ppd.testproject.view;

import br.edu.ifce.ppd.testproject.controller.GameController;
import br.edu.ifce.ppd.testproject.view.form.CreateGameForm;

import javax.swing.*;
import java.awt.*;

import static javax.swing.JOptionPane.OK_CANCEL_OPTION;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import static javax.swing.SwingUtilities.*;

/**
 * Created by andrecoelho on 2/21/16.
 */
public class ButtonsView extends JPanel {

    private JButton newGameButton;
    private JButton listGamesButton;

    private GameController gameController;

    public ButtonsView(GameController gameController) {
        this.gameController = gameController;
        init();
    }

    private void init() {
        newGameButton = new JButton("New Game");
        listGamesButton = new JButton("List Games");

        newGameButton.addActionListener(e -> createGame());
        listGamesButton.addActionListener(e -> listGames());

        setPreferredSize(new Dimension(702, 100));
        setLayout(new FlowLayout());
        add(newGameButton);
        add(listGamesButton);
    }

    private void listGames() {
        gameController.lisGames();
    }

    private void createGame() {
        CreateGameForm confirmPanel = new CreateGameForm();
        Icon icon = null;

        int button = JOptionPane.showConfirmDialog(getWindowAncestor(this),
                confirmPanel,
                "Enter Data",
                OK_CANCEL_OPTION,
                QUESTION_MESSAGE, icon);

        if (button == JOptionPane.OK_OPTION) {
            if (!confirmPanel.hasAlias()) {
                JOptionPane.showMessageDialog(getWindowAncestor(this), "Game alias is required");
                return;
            }

            if (!confirmPanel.hasPlayerName()) {
                JOptionPane.showMessageDialog(getWindowAncestor(this), "Player name is required");
                return;
            }

            gameController.createGame(confirmPanel.getAliasValue(), confirmPanel.getPalyerNameValue());
        }
    }
}
