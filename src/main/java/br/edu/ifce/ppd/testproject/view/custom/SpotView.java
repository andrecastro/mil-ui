package br.edu.ifce.ppd.testproject.view.custom;

import br.edu.ifce.ppd.testproject.controller.GameController;
import br.edu.ifce.ppd.testproject.view.helper.Assets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static br.edu.ifce.ppd.testproject.view.custom.DragDropState.*;
import static br.edu.ifce.ppd.testproject.view.custom.DragDropState.INITIAL;

/**
 * Created by andrecoelho on 2/14/16.
 */
public class SpotView extends JButton implements Draggable {

    private DragDropState state;
    private ImageIcon currentPiece;
    private Boolean hasPiece;

    private Integer id;

    private static final int SPOT_SIZE = 25;

    public SpotView(Integer id, Point position) {
        init(id, position);
    }

    private void init(Integer id, Point position) {
        this.id = id;
        this.hasPiece = false;
        this.state = INITIAL;

        // set the size
        this.setPreferredSize(new Dimension(SPOT_SIZE, SPOT_SIZE));
        this.setBounds(position.x, position.y, SPOT_SIZE, SPOT_SIZE);

        // set the visual configuration
        this.setRolloverIcon(Assets.rolloverSpot());
        this.setRolloverSelectedIcon(Assets.rolloverSpot());
        this.setRolloverEnabled(true);
        this.setPressedIcon(Assets.pressedSpot());
        this.setIcon(Assets.cleanSpot());
    }

    public void setPiece(ImageIcon piece) {
        if (!hasPiece) {
            setIcon(piece);
            setDisabledIcon(piece);
            setEnabled(false);
            currentPiece = piece;
            hasPiece = true;
        }
    }

    @Override
    public void dropFailure() {
        this.state = INITIAL;
        this.hasPiece = true;
        this.setIcon(currentPiece);
        this.setDisabledIcon(currentPiece);
        this.setEnabled(false);
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
        this.state = DROPPED;
        this.hasPiece = false;
        this.setIcon(Assets.cleanSpot());
        this.setEnabled(true);
    }

    @Override
    public DragDropState currentState() {
        return state;
    }
}
