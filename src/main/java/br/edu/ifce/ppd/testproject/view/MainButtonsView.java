package br.edu.ifce.ppd.testproject.view;

import br.edu.ifce.ppd.testproject.controller.GameController;
import br.edu.ifce.ppd.testproject.view.form.CreateGameForm;
import br.edu.ifce.ppd.testproject.view.helper.Assets;

import javax.swing.*;
import java.awt.*;

import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.OK_CANCEL_OPTION;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import static javax.swing.SwingUtilities.*;

/**
 * Created by andrecoelho on 2/21/16.
 */
public class MainButtonsView extends JPanel {

    private JButton newGameButton;
    private JButton listGamesButton;

    private GameController gameController;

    public MainButtonsView(GameController gameController) {
        this.gameController = gameController;
        init();
    }

    private void init() {
        newGameButton = new JButton("New Game", Assets.newGame());
        listGamesButton = new JButton("List Games", Assets.list());

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

        int button = JOptionPane.showConfirmDialog(getWindowAncestor(this),
                confirmPanel,
                "Enter Data",
                OK_CANCEL_OPTION,
                QUESTION_MESSAGE, Assets.info());

        if (button == JOptionPane.OK_OPTION) {
            if (!confirmPanel.hasAlias()) {
                JOptionPane.showMessageDialog(getWindowAncestor(this), "Game alias is required", "Attribute Required",
                        INFORMATION_MESSAGE, Assets.info());
                return;
            }

            if (!confirmPanel.hasPlayerName()) {
                JOptionPane.showMessageDialog(getWindowAncestor(this), "Player name is required", "Attribute Required",
                        INFORMATION_MESSAGE, Assets.info());
                return;
            }

            gameController.createGame(confirmPanel.getAliasValue(), confirmPanel.getPalyerNameValue());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(Assets.background().getImage(), 0, 0, null);
    }
}
