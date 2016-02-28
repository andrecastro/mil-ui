package br.edu.ifce.ppd.testproject.view;

import br.edu.ifce.ppd.testproject.view.helper.Assets;
import br.edu.ifce.ppd.tria.core.model.Player;

import javax.swing.*;
import java.awt.*;

/**
 * Created by andrecoelho on 2/28/16.
 */
public class PiecesView extends JPanel {

    private Player player;
    private JLabel[] pieces;

    public PiecesView(Player player) {
        this.player = player;
        this.init();
    }

    private void init() {
        setPreferredSize(new Dimension(50, 400));
        setLayout(new GridLayout(9, 1));
        setBackground(Color.WHITE);

        pieces = new JLabel[9];

        for (int i = 0; i < 9; i++) {
            JLabel piece = new JLabel(getPiece(player));
            piece.setPreferredSize(new Dimension(25, 25));
            piece.setEnabled(true);
            pieces[i] = piece;
        }

        if (player != null) {
            for (int i = 0; i < player.getNumberOfPiecesPlaced(); i++) {
                pieces[i].setEnabled(false);
            }
        }

        for (int i = 0; i < 9; i++) {
            add(pieces[i]);
        }
    }

    public void update(Player player) {
        this.player = player;

        for (int i = 0; i < 9; i++) {
            pieces[i].setIcon(getPiece(player));
            pieces[i].setEnabled(true);
        }

        for (int i = 0; i <  player.getNumberOfPiecesPlaced(); i++) {
            pieces[i].setEnabled(false);
        }

        for (int i = 0; i < 9; i++) {
            pieces[i].repaint();
        }
    }

    private ImageIcon getPiece(Player player) {
        if (player == null)
            return Assets.cleanSpot();

        return Assets.playerPiece(player);
    }
}
