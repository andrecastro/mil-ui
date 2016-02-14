package br.edu.ifce.ppd.testproject.adapter;

import br.edu.ifce.ppd.testproject.view.ViewTestDragDrop;
import br.edu.ifce.ppd.testproject.view.custom.Draggable;

import javax.swing.*;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.IOException;

/**
 * Created by andrecoelho on 2/14/16.
 */
public class BoardDropTarget extends DropTarget {

    private ViewTestDragDrop viewTestDragDrop;

    public BoardDropTarget(ViewTestDragDrop viewTestDragDrop) {
        this.viewTestDragDrop = viewTestDragDrop;
    }

    @Override
    public synchronized void drop(DropTargetDropEvent event) {
        Transferable transferable = event.getTransferable();

        try {
            String name = (String) transferable.getTransferData(transferable.getTransferDataFlavors()[0]);
            JLabel draggable = (JLabel) viewTestDragDrop.findByName(name);
            ((Draggable) draggable).dropFailure();
        } catch (UnsupportedFlavorException | IOException e) {
            e.printStackTrace();
        }
    }
}
