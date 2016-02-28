package br.edu.ifce.ppd.testproject.view;

import br.edu.ifce.ppd.testproject.App;
import br.edu.ifce.ppd.testproject.controller.GameController;

import javax.swing.*;
import java.awt.*;

/**
 * Created by andrecoelho on 2/28/16.
 */
public class GameButtonsView extends JPanel {

    private JButton restartGame;
    private JButton giveUp;

    private GameController gameController;

    public GameButtonsView(GameController gameController) {
        this.gameController = gameController;
        init();
    }

    private void init() {
        restartGame = new JButton("Restart Game");
        giveUp = new JButton("Give up");

        restartGame.addActionListener(e -> restartGame());
        giveUp.addActionListener(e -> giveUp());

        setPreferredSize(new Dimension(550, 40));
        setLayout(new FlowLayout());
        add(restartGame);
        add(giveUp);
    }

    private void restartGame() {

        if (gameController.currentGame().getSecondPlayer() == null) {
            JOptionPane.showMessageDialog(App.mainView, "You do not have an opponent yet to restart");
            return;
        }

        int option = JOptionPane.showConfirmDialog(App.mainView, "Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            gameController.askToRestartGame();
        }
    }

    private void giveUp() {
        int option = JOptionPane.showConfirmDialog(App.mainView, "Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            gameController.giveUp();
        }
    }
}
