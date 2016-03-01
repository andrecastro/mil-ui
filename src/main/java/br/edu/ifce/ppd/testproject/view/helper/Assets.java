package br.edu.ifce.ppd.testproject.view.helper;

import br.edu.ifce.ppd.tria.core.model.Player;

import javax.swing.*;
import java.awt.*;

/**
 * Created by andrecoelho on 2/14/16.
 */
public class Assets {

    private static ImageIcon blackPiece;
    private static ImageIcon lightPiece;
    private static ImageIcon cleanSpot;
    private static ImageIcon pressedSpot;
    private static ImageIcon rolloverSpot;
    private static ImageIcon info;
    private static ImageIcon question;
    private static ImageIcon win;
    private static ImageIcon lose;
    private static ImageIcon background;

    private static ImageIcon newGame;
    private static ImageIcon back;
    private static ImageIcon list;
    private static ImageIcon refresh;
    private static ImageIcon giveUp;
    private static ImageIcon enter;
    private static ImageIcon chat;

    private static ImageIcon icon;

    static {
        blackPiece = new ImageIcon(Assets.class.getClassLoader().getResource("black-piece.png"));
        lightPiece = new ImageIcon(Assets.class.getClassLoader().getResource("light-piece.png"));
        cleanSpot = new ImageIcon(Assets.class.getClassLoader().getResource("clean-spot.png"));
        pressedSpot = new ImageIcon(Assets.class.getClassLoader().getResource("pressed-spot.png"));
        rolloverSpot = new ImageIcon(Assets.class.getClassLoader().getResource("rollover-spot.png"));
        info = new ImageIcon(Assets.class.getClassLoader().getResource("info.png"));
        question = new ImageIcon(Assets.class.getClassLoader().getResource("question.png"));
        win = new ImageIcon(Assets.class.getClassLoader().getResource("win.png"));
        lose = new ImageIcon(Assets.class.getClassLoader().getResource("lose.png"));
        background = new ImageIcon(Assets.class.getClassLoader().getResource("board-game.jpg"));
        newGame = new ImageIcon(Assets.class.getClassLoader().getResource("new.png"));
        back = new ImageIcon(Assets.class.getClassLoader().getResource("back.png"));
        list = new ImageIcon(Assets.class.getClassLoader().getResource("list.png"));
        refresh = new ImageIcon(Assets.class.getClassLoader().getResource("refresh.png"));
        giveUp = new ImageIcon(Assets.class.getClassLoader().getResource("giveup.png"));
        enter = new ImageIcon(Assets.class.getClassLoader().getResource("enter.png"));
        chat = new ImageIcon(Assets.class.getClassLoader().getResource("chat.png"));
        icon = new ImageIcon(Assets.class.getClassLoader().getResource("board-ico.png"));
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

    public static Image icon() {
        return icon.getImage();
    }

    public static ImageIcon info() {
        return info;
    }

    public static ImageIcon question() {
        return question;
    }

    public static ImageIcon win() {
        return win;
    }

    public static ImageIcon lose() {
        return lose;
    }

    public static ImageIcon background() {
        return background;
    }

    public static ImageIcon newGame() {
        return newGame;
    }

    public static ImageIcon back() {
        return back;
    }

    public static ImageIcon list() {
        return list;
    }

    public static ImageIcon refresh() {
        return refresh;
    }

    public static ImageIcon giveUp() {
        return giveUp;
    }

    public static ImageIcon enter() {
        return enter;
    }

    public static ImageIcon chat() {
        return chat;
    }
}
