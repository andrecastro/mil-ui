package br.edu.ifce.ppd.testproject.view;

import javax.swing.*;
import java.awt.*;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;

/**
 * Created by andrecoelho on 2/13/16.
 */
public class DefaultDragSource extends DragSource implements DragGestureListener{

    private JComponent draggable;

    public DefaultDragSource(JComponent draggable) {
        this.draggable = draggable;
        this.createDefaultDragGestureRecognizer(draggable, DnDConstants.ACTION_COPY, this);
    }

    public void dragGestureRecognized(DragGestureEvent event) {
        Cursor cursor = null;
        JComponent panel = (JComponent) event.getComponent();

        if (event.getDragAction() == DnDConstants.ACTION_COPY) {
            cursor = DragSource.DefaultCopyDrop;
        }

        event.startDrag(cursor, new MyTransferable(panel));
    }
}
