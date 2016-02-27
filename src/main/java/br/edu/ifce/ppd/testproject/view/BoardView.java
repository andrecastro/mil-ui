package br.edu.ifce.ppd.testproject.view;

import br.edu.ifce.ppd.testproject.controller.GameController;
import br.edu.ifce.ppd.testproject.view.custom.SpotView;
import br.edu.ifce.ppd.testproject.view.helper.Assets;
import br.edu.ifce.ppd.tria.core.model.Player;

import javax.swing.*;
import java.awt.*;

/**
 * Created by andrecoelho on 2/14/16.
 */
public class BoardView extends JPanel {

    private ImageIcon imageIcon;

    private SpotView[] spots;

    private GameController gameController;

    public BoardView(GameController gameController) {
        this.gameController = gameController;
        init();
    }

    private void init() {
        spots = new SpotView[24];
        imageIcon = new ImageIcon(getClass().getClassLoader().getResource("board.png"));

        buildFirstColumn();
        buildSecondColumn();
        buildThirdColumn();
        buildFourthColumn();
        buildFifthColumn();
        buildSixthColumn();
        buildSeventhColumn();

        setLayout(null);

        for (int index = 0; index < 24; index++) {
           placeSpot(index);
        }

        setPreferredSize(new Dimension(460, 447));
    }

    private void placeSpot(int index) {
        spots[index].addActionListener(e -> {
            SpotView spotView = (SpotView) e.getSource();
            spotView.setPiece(Assets.playerPiece(gameController.currentPlayer()));
        });

        add(spots[index]);
    }

    private void buildFirstColumn() {
        spots[0] = new SpotView(1, new Point(10, 10));
        spots[1] = new SpotView(2, new Point(10, 215));
        spots[2] = new SpotView(3, new Point(10, 415));
    }

    private void buildSecondColumn() {
        spots[3] = new SpotView(4, new Point(90, 90));
        spots[4] = new SpotView(5, new Point(90, 215));
        spots[5] = new SpotView(6, new Point(90, 340));
    }

    private void buildThirdColumn() {
        spots[6] = new SpotView(7, new Point(165, 165));
        spots[7] = new SpotView(8, new Point(165, 215));
        spots[8] = new SpotView(9, new Point(165, 260));
    }

    private void buildFourthColumn() {
        spots[9] = new SpotView(10, new Point(215, 10));
        spots[10] = new SpotView(11, new Point(215, 90));
        spots[11] = new SpotView(12, new Point(215, 165));
        spots[12] = new SpotView(13, new Point(215, 260));
        spots[13] = new SpotView(14, new Point(215, 340));
        spots[14] = new SpotView(15, new Point(215, 415));
    }

    private void buildFifthColumn() {
        spots[15] = new SpotView(16, new Point(265, 165));
        spots[16] = new SpotView(17, new Point(265, 215));
        spots[17] = new SpotView(18, new Point(265, 260));
    }

    private void buildSixthColumn() {
        spots[18] = new SpotView(19, new Point(340, 90));
        spots[19] = new SpotView(20, new Point(340, 215));
        spots[20] = new SpotView(21, new Point(340, 340));
    }

    private void buildSeventhColumn() {
        spots[21] = new SpotView(22, new Point(420, 10));
        spots[22] = new SpotView(23, new Point(420, 215));
        spots[23] = new SpotView(24, new Point(420, 415));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imageIcon.getImage(), 0, 0, null);
    }
}
