package br.edu.ifce.ppd.testproject.adapter;

import br.edu.ifce.ppd.testproject.view.custom.Draggable;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by andrecoelho on 2/13/16.
 */
public class MouseDragAdapter extends MouseAdapter {

    @Override
    public void mouseReleased(MouseEvent e) {
       System.out.println("mouseReleased");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Draggable c = (Draggable) e.getSource();
        TransferHandler handler = c.getTransferHandler();
        handler.exportAsDrag((JComponent) c, e, TransferHandler.COPY);
        c.dragging();
    }

}
