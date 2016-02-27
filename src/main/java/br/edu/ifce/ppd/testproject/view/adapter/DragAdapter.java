package br.edu.ifce.ppd.testproject.view.adapter;


import br.edu.ifce.ppd.testproject.view.custom.DragDropState;
import br.edu.ifce.ppd.testproject.view.custom.Draggable;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.dnd.*;

/**
 * Created by andrecoelho on 2/14/16.
 */
public class DragAdapter implements DragGestureListener, DragSourceListener {

    @Override
    public void dragGestureRecognized(DragGestureEvent event) {
        Cursor cursor = null;
        JComponent panel = (JComponent) event.getComponent();

        if (event.getDragAction() == DnDConstants.ACTION_COPY) {
            cursor = DragSource.DefaultCopyDrop;
        }

        ((Draggable) panel).dragging();
        event.startDrag(cursor, new StringSelection(panel.getName()));
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

        if (draggable.currentState() == DragDropState.DRAGGING) {
            draggable.dropFailure();
        }
    }
}
