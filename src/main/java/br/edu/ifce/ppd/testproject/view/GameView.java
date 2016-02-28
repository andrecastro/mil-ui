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

    public GameView(GameController gameController) {
        init(gameController);
    }

    private void init(GameController gameController) {
        boardView = new BoardView(gameController);
        chatView = new ChatView(gameController);

        setBorder(BorderFactory.createTitledBorder(gameController.currentGame().getAlias()));
        setLayout(new BorderLayout());

        add(boardView, BorderLayout.WEST);
        add(chatView, BorderLayout.EAST);

        setVisible(true);
        lockBoardView();
        lockChatView();
    }

    public void updateBoarView() {
        this.boardView.update();
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
