package br.edu.ifce.ppd.testproject.view.custom;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by andrecoelho on 2/14/16.
 */
public class CustomLabel extends JLabel implements Draggable {

    private DragDropState state;

    public CustomLabel(String label) {
        super(label);
        this.state = DragDropState.INITIAL;
    }

    @Override
    public void dropFailure() {
        this.state = DragDropState.INITIAL;
        this.setText("drag");
    }

    @Override
    public void dragging() {
        this.state = DragDropState.DRAGGING;
        this.setText("dragging");
    }

    @Override
    public void dropped() {
        this.state = DragDropState.DROPPED;
        this.setText("dropped");
    }

    @Override
    public DragDropState currentState() {
        return state;
    }
}
