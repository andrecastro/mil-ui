package br.edu.ifce.ppd.testproject.view.adapter;


import br.edu.ifce.ppd.testproject.controller.GameController;
import br.edu.ifce.ppd.testproject.view.custom.DragDropState;
import br.edu.ifce.ppd.testproject.view.custom.Draggable;
import br.edu.ifce.ppd.testproject.view.custom.SpotView;
import br.edu.ifce.ppd.tria.core.model.GameStatus;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.dnd.*;

import static br.edu.ifce.ppd.testproject.view.custom.DragDropState.DRAGGING;
import static br.edu.ifce.ppd.tria.core.model.GameStatus.PLAYING;

/**
 * Created by andrecoelho on 2/14/16.
 */
public class DragAdapter implements DragGestureListener, DragSourceListener {

    private GameController gameController;

    public DragAdapter(GameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public void dragGestureRecognized(DragGestureEvent event) {
        if (gameController.currentGame().getStatus().equals(PLAYING)) {
            startDragging(event);
        }
    }

    private void startDragging(DragGestureEvent event) {
        SpotView spotView = (SpotView) event.getComponent();

        if (spotView.isOccupiedBy(gameController.currentPlayer())) {
            Cursor cursor = null;

            if (event.getDragAction() == DnDConstants.ACTION_COPY) {
                cursor = DragSource.DefaultCopyDrop;
            }

            ImageIcon dragImage = spotView.currentImage();
            spotView.dragging();
            event.startDrag(cursor, dragImage.getImage(), new Point(-12, -12),
                    new StringSelection(spotView.getName()), this);
        }
    }

    @Override
    public void dragEnter(DragSourceDragEvent dsde) {

    }

    @Override
    public void dragOver(DragSourceDragEvent dsde) {

    }

    @Override
    public void dropActionChanged(DragSourceDragEvent dsde) {

    }

    @Override
    public void dragExit(DragSourceEvent dse) {

    }

    @Override
    public void dragDropEnd(DragSourceDropEvent event) {
        DragSourceContext dragSourceContext = (DragSourceContext) event.getSource();
        Draggable draggable = (Draggable) dragSourceContext.getComponent();

        if (draggable.currentState() == DRAGGING) {
            draggable.dropFailure();
        }
    }
}
