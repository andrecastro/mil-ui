package br.edu.ifce.ppd.testproject.view;


import br.edu.ifce.ppd.testproject.controller.GameController;

import javax.swing.*;
import java.awt.*;

/**
 * Created by andrecoelho on 2/14/16.
 */
public class GameView extends JPanel {

    private ChatView chatView;
    private BoardView boardView;

    private PiecesView playerOnePiecesView;
    private PiecesView playerTwoPiecesView;

    private GameButtonsView gameButtonsView;

    private GameController gameController;

    public GameView(GameController gameController) {
        this.gameController = gameController;
        init();
    }

    private void init() {
        boardView = new BoardView(gameController);
        chatView = new ChatView(gameController);
        playerOnePiecesView = new PiecesView(gameController.currentGame().getFirstPlayer());
        playerTwoPiecesView = new PiecesView(gameController.currentGame().getSecondPlayer());
        gameButtonsView = new GameButtonsView(gameController);

        setBorder(BorderFactory.createTitledBorder(gameController.currentGame().getAlias()));
        setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel(new BorderLayout());
        boardPanel.add(playerOnePiecesView, BorderLayout.WEST);
        boardPanel.add(boardView, BorderLayout.CENTER);
        boardPanel.add(playerTwoPiecesView, BorderLayout.EAST);
        boardPanel.add(gameButtonsView, BorderLayout.SOUTH);

        add(boardPanel, BorderLayout.WEST);
        add(chatView, BorderLayout.EAST);

        setVisible(true);
        lockBoardView();
        lockChatView();
    }

    public void updateBoarView() {
        this.boardView.update();
        this.playerOnePiecesView.update(gameController.currentGame().getFirstPlayer());
        this.playerTwoPiecesView.update(gameController.currentGame().getSecondPlayer());
        this.repaint();
        this.revalidate();
    }

    public void lockBoardView() {
        enableComponents(boardView, false);
    }

    public void unlockBoardView() {
        enableComponents(boardView, true);
    }

    public void lockChatView() {
        enableComponents(chatView, false);
    }

    public void unlockChatView() {
        enableComponents(chatView, true);
    }

    public void messageToChat(String playerName, String message) {
        chatView.updateViewText(playerName, message);
    }

    // Recursively disable or enable all components
    // http://stackoverflow.com/questions/10985734/java-swing-enabling-disabling-all-components-in-jpanel
    private void enableComponents(Container container, boolean enable) {
        Component[] components = container.getComponents();
        for (Component component : components) {
            component.setEnabled(enable);
            if (component instanceof Container) {
                enableComponents((Container)component, enable);
            }
        }
    }
}
