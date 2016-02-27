package br.edu.ifce.ppd.testproject.view.helper;

import br.edu.ifce.ppd.tria.core.model.Player;

import javax.swing.*;

/**
 * Created by andrecoelho on 2/14/16.
 */
public class Assets {

    private static ImageIcon blackPiece;
    private static ImageIcon lightPiece;
    private static ImageIcon cleanSpot;
    private static ImageIcon pressedSpot;
    private static ImageIcon rolloverSpot;

    static {
        blackPiece = new ImageIcon(Assets.class.getClassLoader().getResource("black-piece.png"));
        lightPiece = new ImageIcon(Assets.class.getClassLoader().getResource("light-piece.png"));
        cleanSpot = new ImageIcon(Assets.class.getClassLoader().getResource("clean-spot.png"));
        pressedSpot = new ImageIcon(Assets.class.getClassLoader().getResource("pressed-spot.png"));
        rolloverSpot = new ImageIcon(Assets.class.getClassLoader().getResource("rollover-spot.png"));
    }

    public static ImageIcon blackPiece() {
        return blackPiece;
    }

    public static ImageIcon cleanSpot() {
        return cleanSpot;
    }

    public static ImageIcon lightPiece() {
        return lightPiece;
    }

    public static ImageIcon pressedSpot() {
        return pressedSpot;
    }

    public static ImageIcon rolloverSpot() {
        return rolloverSpot;
    }

    public static ImageIcon playerPiece(Player player) {
        switch (player.getSelection()) {
            case FIRST_PLAYER:
                return blackPiece();
            case SECOND_PLAYER:
                return lightPiece();
        }

        return cleanSpot();
    }

}
