package br.edu.ifce.ppd.testproject.view;

import br.edu.ifce.ppd.testproject.App;
import br.edu.ifce.ppd.testproject.controller.GameController;
import br.edu.ifce.ppd.testproject.view.custom.GameTableModel;
import br.edu.ifce.ppd.testproject.view.form.EnterGameForm;
import br.edu.ifce.ppd.tria.core.model.Game;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static javax.swing.JOptionPane.OK_CANCEL_OPTION;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import static javax.swing.ListSelectionModel.SINGLE_SELECTION;
import static javax.swing.SwingUtilities.getWindowAncestor;

/**
 * Created by andrecoelho on 2/21/16.
 */
public class ListGamesView extends JPanel {

    private JTable table;
    private JToolBar buttonsPanel;

    private List<Game> games;
    private GameController gameController;

    public ListGamesView(List<Game> games, GameController gameController) {
        this.gameController = gameController;
        this.games = games;
        init(games);
    }

    private void init(List<Game> games) {
        buttonsPanel = new JToolBar();

        JButton back = new JButton("Back");
        back.addActionListener(e -> gameController.backToInitialView());

        JButton enterGame = new JButton("Enter Game");
        enterGame.addActionListener(e -> enterGame());

        buttonsPanel.add(back);
        buttonsPanel.add(enterGame);

        GameTableModel tableModel = new GameTableModel(games);
        table = new JTable(tableModel);
        table.setSelectionMode(SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        setPreferredSize(new Dimension(702, 447));
        setLayout(new BorderLayout());
        add(buttonsPanel, BorderLayout.PAGE_START);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void enterGame() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Select a row in the table");
            return;
        }

        Game game = games.get(selectedRow);

        EnterGameForm form = new EnterGameForm();
        int button = JOptionPane.showConfirmDialog(getWindowAncestor(this),
                form,
                "Enter Data",
                OK_CANCEL_OPTION,
                QUESTION_MESSAGE);

        if (button == JOptionPane.OK_OPTION) {
            if (!form.hasPlayerName()) {
                JOptionPane.showMessageDialog(getWindowAncestor(this), "Player name is required");
                return;
            }
        }

        gameController.enterGame(game, form.getPlayerName());
    }
}
