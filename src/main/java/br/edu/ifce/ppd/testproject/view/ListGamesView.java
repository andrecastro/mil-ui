package br.edu.ifce.ppd.testproject.view;

import br.edu.ifce.ppd.testproject.controller.GameController;
import br.edu.ifce.ppd.testproject.view.custom.GameTableModel;
import br.edu.ifce.ppd.testproject.view.form.EnterGameForm;
import br.edu.ifce.ppd.testproject.view.helper.Assets;
import br.edu.ifce.ppd.tria.core.model.Game;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.OK_CANCEL_OPTION;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import static javax.swing.ListSelectionModel.SINGLE_SELECTION;
import static javax.swing.SwingUtilities.getWindowAncestor;

/**
 * Created by andrecoelho on 2/21/16.
 */
public class ListGamesView extends JPanel {

    private JTable table;
    private JToolBar toolBar;

    private List<Game> games;
    private GameController gameController;

    public ListGamesView(List<Game> games, GameController gameController) {
        this.gameController = gameController;
        this.games = games;
        init(games);
    }

    private void init(List<Game> games) {
        toolBar = new JToolBar();
        toolBar.setFloatable(false);

        JButton back = new JButton("Back", Assets.back());
        back.addActionListener(e -> gameController.backToInitialView());

        JButton enterGame = new JButton("Enter Game", Assets.enter());
        enterGame.addActionListener(e -> enterGame());

        toolBar.add(back);
        toolBar.add(enterGame);

        GameTableModel tableModel = new GameTableModel(games);
        table = new JTable(tableModel);
        table.setSelectionMode(SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        setPreferredSize(new Dimension(702, 447));
        setLayout(new BorderLayout());
        add(toolBar, BorderLayout.PAGE_START);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void enterGame() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Select a row in the table", "Information",
                    INFORMATION_MESSAGE, Assets.info());
            return;
        }

        Game game = games.get(selectedRow);

        EnterGameForm form = new EnterGameForm();
        int button = JOptionPane.showConfirmDialog(getWindowAncestor(this),
                form,
                "Enter Data",
                OK_CANCEL_OPTION,
                QUESTION_MESSAGE,
                Assets.question());

        if (button == JOptionPane.OK_OPTION) {
            if (!form.hasPlayerName()) {
                JOptionPane.showMessageDialog(getWindowAncestor(this), "Player name is required");
                return;
            }
        }

        gameController.enterGame(game, form.getPlayerName());
    }
}
