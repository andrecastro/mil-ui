package br.edu.ifce.ppd.testproject.view;

import br.edu.ifce.ppd.testproject.view.adapter.DragAdapter;
import br.edu.ifce.ppd.testproject.view.adapter.DropAdapter;
import br.edu.ifce.ppd.testproject.view.custom.SpotView;
import javafx.animation.Animation;

import javax.swing.*;
import java.awt.*;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragSource;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;

import static java.util.Arrays.asList;

/**
 * Created by andrecoelho on 2/13/16.
 */
public class ViewTestDragDrop extends JFrame {

    private SpotView drop;
    private SpotView drag;
    private SpotView drag1;

    public ViewTestDragDrop() {
        init();
    }

    private void init() {
        setLayout(new BorderLayout());

        ImageIcon imageIcon = new ImageIcon(getClass().getClassLoader().getResource("drag.png"));

//        TransferHandler th = new TransferHandler("name");
//        th.setDragImage(imageIcon.getImage());

//        drop = new SpotView();
//        drop.setName("drop");
//        drop.setBackground(Color.black);
//        drop.setPreferredSize(new Dimension(700, 200));
//        drop.setDropTarget();
//
//        drag = new SpotView();
//        drag.setName("drag");
//
//        drag1 = new SpotView();
//        drag1.setName("drag1");
//
//        DragSource dragSource = new DragSource();
//        DragAdapter adapter = new DragAdapter();
//        dragSource.addDragSourceListener(adapter);
//
//        dragSource.createDefaultDragGestureRecognizer(drag, DnDConstants.ACTION_COPY, adapter);
//        dragSource.createDefaultDragGestureRecognizer(drag1, DnDConstants.ACTION_COPY, adapter);
//
//
//        JButton startDrag = new JButton("StartDrag");
//
//
//        add(drag, BorderLayout.NORTH);
//        add(drag1, BorderLayout.CENTER);
//        add(drop, BorderLayout.SOUTH);
//        add(startDrag, BorderLayout.EAST);
//
//        pack();
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        setLocationRelativeTo(null);
//        setVisible(true);

    }

    public JComponent findByName(String name) {
       return (JComponent) asList(getContentPane().getComponents())
               .stream()
               .filter(c -> c.getName().equals(name))
               .findFirst()
               .orElseGet(null);
    }
}
