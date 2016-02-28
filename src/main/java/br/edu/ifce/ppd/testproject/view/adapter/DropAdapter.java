package br.edu.ifce.ppd.testproject.view.adapter;

import br.edu.ifce.ppd.testproject.controller.GameController;
import br.edu.ifce.ppd.testproject.view.BoardView;
import br.edu.ifce.ppd.testproject.view.SpotView;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.io.IOException;

/**
 * Created by andrecoelho on 2/13/16.
 */
public class DropAdapter extends DropTarget {

    private BoardView boardView;
    private GameController gameController;

    public DropAdapter(BoardView boardView, GameController gameController) {
        this.boardView = boardView;
        this.gameController = gameController;
    }

    @Override
    public synchronized void dragOver(DropTargetDragEvent event) {
        Transferable transferable = event.getTransferable();
        String name = null;

        try {
            name = (String) transferable.getTransferData(DataFlavor.stringFlavor);
        } catch (UnsupportedFlavorException | IOException e) {
            e.printStackTrace();
        }

        SpotView droppable = (SpotView) this.getComponent();
        SpotView draggable = boardView.findById(name);

        if (isDropPermitted(droppable, draggable)) {
            super.dragOver(event);
        } else {
            event.rejectDrag();
        }
    }

    @Override
    public void drop(DropTargetDropEvent event) {
        Transferable transferable = event.getTransferable();
        String name = null;

        try {
            name = (String) transferable.getTransferData(DataFlavor.stringFlavor);
        } catch (UnsupportedFlavorException | IOException e) {
            e.printStackTrace();
        }

        SpotView droppable = (SpotView) this.getComponent();
        SpotView draggable = boardView.findById(name);

        if (isDropPermitted(droppable, draggable)) {

            Integer idFrom = draggable.getId();
            Integer idTo = droppable.getId();

            droppable.updateWith(draggable);
            draggable.dropped();
            gameController.movePiece(idFrom, idTo);
        } else {
            event.rejectDrop();
        }
    }

    private boolean isDropPermitted(SpotView droppable, SpotView draggable) {
        return (gameController.currentPlayer().getNumberOfPieces().equals(3) || draggable.isPossiblePath(droppable))
                && !droppable.hasPiece();
    }
}
