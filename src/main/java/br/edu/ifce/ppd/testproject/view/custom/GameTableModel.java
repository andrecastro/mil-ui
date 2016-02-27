package br.edu.ifce.ppd.testproject.view.custom;

import br.edu.ifce.ppd.tria.core.model.Game;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * Created by andrecoelho on 2/21/16.
 */
public class GameTableModel extends AbstractTableModel {

    private List<Game> games;
    private String columns[] = {"Identifier", "Alias", "Owner"};

    public GameTableModel(List<Game> games) {
        this.games = games;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public int getRowCount() {
        return games.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Game game = games.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return game.getId();
            case 1:
                return game.getAlias();
            case 2:
                return game.getFirstPlayer().getName();
        }
        return null;
    }
}
