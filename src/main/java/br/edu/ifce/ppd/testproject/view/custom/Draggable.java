package br.edu.ifce.ppd.testproject.view.custom;

/**
 * Created by andrecoelho on 2/14/16.
 */
public interface Draggable {

    void dropFailure();

    void dragging();

    void dropped();

    DragDropState currentState();
}
