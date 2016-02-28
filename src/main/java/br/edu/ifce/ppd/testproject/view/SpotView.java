package br.edu.ifce.ppd.testproject.view;

import br.edu.ifce.ppd.testproject.view.custom.DragDropState;
import br.edu.ifce.ppd.testproject.view.custom.Draggable;
import br.edu.ifce.ppd.testproject.view.helper.Assets;
import br.edu.ifce.ppd.tria.core.model.Player;
import br.edu.ifce.ppd.tria.core.model.Spot;

import javax.swing.*;
import java.awt.*;

import static br.edu.ifce.ppd.testproject.view.custom.DragDropState.DRAGGING;
import static br.edu.ifce.ppd.testproject.view.custom.DragDropState.INITIAL;
import static br.edu.ifce.ppd.tria.core.model.PlayerSelection.FIRST_PLAYER;

/**
 * Created by andrecoelho on 2/14/16.
 */
public class SpotView extends JButton implements Draggable {

    private DragDropState state;
    private Boolean hasPiece;
    private Spot spot;

    private ImageIcon currentPiece;
    private Icon defaultDisabledPiece;

    private static final int SPOT_SIZE = 25;

    public SpotView(Spot spot, Point position) {
        init(spot, position);
    }

    public void setPiece(ImageIcon piece) {
        setIcon(piece);
        setDisabledIcon(piece);
        setEnabled(false);
        setRolloverEnabled(false);
        currentPiece = piece;
        hasPiece = true;
    }

    public void updateWith(Spot newSpot) {
        spot = newSpot;

        if (spot.isOccupiedByPlayerOne()) {
            setPiece(Assets.blackPiece());
        } else if (spot.isOccupiedByPlayerTwo()) {
            setPiece(Assets.lightPiece());
        } else {
            cleanSpotView();
        }
    }


    public void updateWith(SpotView draggable) {
        updateWith(draggable.spot);
    }

    public Integer getId() {
        return spot.getId();
    }

    public boolean hasPiece() {
        return hasPiece;
    }

    @Override
    public void dropFailure() {
        this.state = INITIAL;
        this.hasPiece = true;
        this.setIcon(currentPiece);
        this.setDisabledIcon(currentPiece);
        this.setEnabled(true);
    }

    @Override
    public void dragging() {
        this.state = DRAGGING;
        this.setIcon(Assets.cleanSpot());
        this.setDisabledIcon(Assets.cleanSpot());
        this.setEnabled(false);
    }

    @Override
    public void dropped() {
        this.cleanSpotView();
    }

    public boolean isOccupiedBy(Player player) {
        if (player.getSelection().equals(FIRST_PLAYER)) {
            return spot.isOccupiedByPlayerOne();
        }

        return spot.isOccupiedByPlayerTwo();
    }

    @Override
    public DragDropState currentState() {
        return state;
    }

    public void cleanSpotView() {
        this.hasPiece = false;
        this.currentPiece = Assets.cleanSpot();
        this.state = INITIAL;
        this.setIcon(Assets.cleanSpot());
        this.setDisabledIcon(defaultDisabledPiece);
        this.setRolloverEnabled(true);
        this.setEnabled(true);
    }

    private void init(Spot spot, Point position) {
        this.spot = spot;
        this.hasPiece = false;
        this.state = INITIAL;
        this.currentPiece = Assets.cleanSpot();

        this.setName(String.valueOf(spot.getId()));

        // set the size
        this.setPreferredSize(new Dimension(SPOT_SIZE, SPOT_SIZE));
        this.setBounds(position.x, position.y, SPOT_SIZE, SPOT_SIZE);

        // set the visual configuration
        this.setRolloverIcon(Assets.rolloverSpot());
        this.setRolloverSelectedIcon(Assets.rolloverSpot());
        this.setRolloverEnabled(true);
        this.setPressedIcon(Assets.pressedSpot());
        this.setIcon(Assets.cleanSpot());
        this.defaultDisabledPiece = getDisabledIcon();
    }

    public boolean isPossiblePath(SpotView spotView) {
        return this.spot.isPossiblePath(spotView.spot);
    }

    public ImageIcon currentImage() {
        return this.currentPiece;
    }
}
