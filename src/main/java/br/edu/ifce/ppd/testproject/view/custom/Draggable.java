package br.edu.ifce.ppd.testproject.view.custom;

import javax.swing.*;

/**
 * Created by andrecoelho on 2/14/16.
 */
public interface Draggable {

    void dropFailure();

    void dragging();

    void dropped();

    TransferHandler getTransferHandler();

    DragDropState currentState();
}
