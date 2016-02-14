package br.edu.ifce.ppd.testproject.adapter;

import br.edu.ifce.ppd.testproject.view.ViewTestDragDrop;
import br.edu.ifce.ppd.testproject.view.custom.Draggable;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.IOException;

/**
 * Created by andrecoelho on 2/13/16.
 */
public class DropAdapter extends DropTarget {

    private ViewTestDragDrop viewTestDragDrop;

    public DropAdapter(ViewTestDragDrop viewTestDragDrop) {
        this.viewTestDragDrop = viewTestDragDrop;
    }

    @Override
    public void drop(DropTargetDropEvent event) {
        Transferable transferable = event.getTransferable();

        try {
            String name = (String) transferable.getTransferData(transferable.getTransferDataFlavors()[0]);
            JLabel droppable = (JLabel) this.getComponent();
            JLabel draggable = (JLabel) viewTestDragDrop.findByName(name);

            droppable.setText(draggable.getText());
            ((Draggable) draggable).dropped();

        } catch (UnsupportedFlavorException | IOException e) {
            e.printStackTrace();
        }
    }

}
