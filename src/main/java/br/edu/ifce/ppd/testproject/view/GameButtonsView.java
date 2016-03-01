package br.edu.ifce.ppd.testproject.view;

import br.edu.ifce.ppd.testproject.App;
import br.edu.ifce.ppd.testproject.controller.GameController;
import br.edu.ifce.ppd.testproject.view.helper.Assets;

import javax.swing.*;
import java.awt.*;

import static javax.swing.JOptionPane.*;

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
        restartGame = new JButton("Restart Game", Assets.refresh());
        giveUp = new JButton("Give up", Assets.giveUp());

        restartGame.addActionListener(e -> restartGame());
        giveUp.addActionListener(e -> giveUp());

        setPreferredSize(new Dimension(550, 40));
        setLayout(new FlowLayout());
        add(restartGame);
        add(giveUp);
    }

    private void restartGame() {

        if (gameController.currentGame().getSecondPlayer() == null) {
            JOptionPane.showMessageDialog(App.mainView, "You do not have an opponent yet to restart", "Message",
                    INFORMATION_MESSAGE, Assets.info());
            return;
        }

        int option = JOptionPane.showConfirmDialog(App.mainView,
                "A request will be sent to you opponent. Are you sure of this?",
                "Confirm",
                YES_NO_OPTION,
                QUESTION_MESSAGE,
                Assets.question());

        if (option == YES_OPTION) {
            gameController.askToRestartGame();
        }
    }

    private void giveUp() {
        int option = JOptionPane.showConfirmDialog(App.mainView,
                "Are you sure you want to give up?",
                "Confirm",
                YES_NO_OPTION,
                QUESTION_MESSAGE,
                Assets.question());

        if (option == YES_OPTION) {
            gameController.giveUp();
        }
    }
}
