package br.edu.ifce.ppd.testproject.view;

import br.edu.ifce.ppd.testproject.controller.GameController;
import br.edu.ifce.ppd.testproject.view.adapter.DragAdapter;
import br.edu.ifce.ppd.testproject.view.adapter.DropAdapter;
import br.edu.ifce.ppd.testproject.view.helper.Assets;
import br.edu.ifce.ppd.tria.core.model.Game;
import br.edu.ifce.ppd.tria.core.model.Spot;

import javax.swing.*;
import java.awt.*;
import java.awt.dnd.DragSource;
import java.util.HashMap;

import static br.edu.ifce.ppd.testproject.helper.Logger.*;
import static java.awt.dnd.DnDConstants.ACTION_COPY;
import static java.util.Arrays.asList;

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

    public void update() {
        HashMap<Integer, Spot> board = gameController.currentGame().getBoard();
        board.keySet().stream().forEach(id -> {
            spots[id - 1].updateWith(board.get(id));
            spots[id - 1].repaint();
            spots[id - 1].revalidate();
        });
    }

    public SpotView findById(String id) {
        return (SpotView) asList(getComponents())
                .stream()
                .filter(c -> c.getName().equals(id))
                .findFirst()
                .orElseGet(null);
    }

    private void init() {
        spots = new SpotView[24];
        imageIcon = new ImageIcon(getClass().getClassLoader().getResource("board.png"));

        createSpotView();
        placeSpotView();

        setPreferredSize(new Dimension(450, 400));
    }

    private void createSpotView() {
        Game game = gameController.currentGame();

        buildFirstColumn(game.getBoard());
        buildSecondColumn(game.getBoard());
        buildThirdColumn(game.getBoard());
        buildFourthColumn(game.getBoard());
        buildFifthColumn(game.getBoard());
        buildSixthColumn(game.getBoard());
        buildSeventhColumn(game.getBoard());
    }

    private void placeSpotView() {
        setLayout(null);

        // Custom drag and drop
        DragSource dragSource = new DragSource();
        DragAdapter dragAdapter = new DragAdapter(gameController);
        dragSource.addDragSourceListener(dragAdapter);

        for (int index = 0; index < 24; index++) {
            spots[index].addActionListener(e -> onClick((SpotView) e.getSource()));
            spots[index].setDropTarget(new DropAdapter(this, gameController));
            dragSource.createDefaultDragGestureRecognizer(spots[index], ACTION_COPY, dragAdapter);
            add(spots[index]);
        }
    }

    private void buildFirstColumn(HashMap<Integer, Spot> board) {
        spots[0] = new SpotView(board.get(1), new Point(10, 10));
        spots[1] = new SpotView(board.get(2), new Point(10, 215));
        spots[2] = new SpotView(board.get(3), new Point(10, 415));
    }

    private void buildSecondColumn(HashMap<Integer, Spot> board) {
        spots[3] = new SpotView(board.get(4), new Point(90, 90));
        spots[4] = new SpotView(board.get(5), new Point(90, 215));
        spots[5] = new SpotView(board.get(6), new Point(90, 340));
    }

    private void buildThirdColumn(HashMap<Integer, Spot> board) {
        spots[6] = new SpotView(board.get(7), new Point(165, 165));
        spots[7] = new SpotView(board.get(8), new Point(165, 215));
        spots[8] = new SpotView(board.get(9), new Point(165, 260));
    }

    private void buildFourthColumn(HashMap<Integer, Spot> board) {
        spots[9] = new SpotView(board.get(10), new Point(215, 10));
        spots[10] = new SpotView(board.get(11), new Point(215, 90));
        spots[11] = new SpotView(board.get(12), new Point(215, 165));
        spots[12] = new SpotView(board.get(13), new Point(215, 260));
        spots[13] = new SpotView(board.get(14), new Point(215, 340));
        spots[14] = new SpotView(board.get(15), new Point(215, 415));
    }

    private void buildFifthColumn(HashMap<Integer, Spot> board) {
        spots[15] = new SpotView(board.get(16), new Point(265, 165));
        spots[16] = new SpotView(board.get(17), new Point(265, 215));
        spots[17] = new SpotView(board.get(18), new Point(265, 260));
    }

    private void buildSixthColumn(HashMap<Integer, Spot> board) {
        spots[18] = new SpotView(board.get(19), new Point(340, 90));
        spots[19] = new SpotView(board.get(20), new Point(340, 215));
        spots[20] = new SpotView(board.get(21), new Point(340, 340));
    }

    private void buildSeventhColumn(HashMap<Integer, Spot> board) {
        spots[21] = new SpotView(board.get(22), new Point(420, 10));
        spots[22] = new SpotView(board.get(23), new Point(420, 215));
        spots[23] = new SpotView(board.get(24), new Point(420, 415));
    }

    private void onClick(SpotView spotView) {
        Game game = gameController.currentGame();

        switch (game.getStatus()) {
            case PLACING_OF_PIECE:
                putPieceIn(spotView);
                break;
            case REMOVING_PIECE:
                removePieceOf(spotView);
                break;
        }
    }

    private void removePieceOf(SpotView spotView) {
        if (!spotView.hasPiece()) {
            log("This spot is empty, select another one");
            return;
        }

        if (spotView.isOccupiedBy(gameController.currentPlayer())) {
            log("This is your piece man, select one of your opponent");
            return;
        }

        spotView.cleanSpotView();
        gameController.removePiece(spotView.getId());
    }

    private void putPieceIn(SpotView spotView) {
        if (spotView.hasPiece()) {
            log("Choose another spot, this is already occupied");
            return;
        }

        spotView.setPiece(Assets.playerPiece(gameController.currentPlayer()));
        gameController.putPiece(spotView.getId());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imageIcon.getImage(), 0, 0, null);
    }
}
