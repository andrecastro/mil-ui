package br.edu.ifce.ppd.testproject.view;

import br.edu.ifce.ppd.testproject.adapter.BoardDropTarget;
import br.edu.ifce.ppd.testproject.adapter.DropAdapter;
import br.edu.ifce.ppd.testproject.adapter.MouseDragAdapter;
import br.edu.ifce.ppd.testproject.view.custom.CustomLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.dnd.*;

import static java.util.Arrays.asList;

/**
 * Created by andrecoelho on 2/13/16.
 */
public class ViewTestDragDrop extends JFrame {

    private CustomLabel drop;
    private CustomLabel drag;

    public ViewTestDragDrop() {
        init();
    }

    private void init() {
        setSize(700, 500);
        setLayout(new BorderLayout());

        ImageIcon imageIcon = new ImageIcon(getClass().getClassLoader().getResource("drag.png"));

        TransferHandler th = new TransferHandler("name");
        th.setDragImage(imageIcon.getImage());


        drop = new CustomLabel("Drop");
        drop.setName("drop");
        drop.setBackground(Color.black);
        drop.setPreferredSize(new Dimension(700, 200));
        drop.setDropTarget(new DropAdapter(this));

        drag = new CustomLabel("Drag");
        drag.setName("drag");
        drag.setAutoscrolls(false);
        drag.addMouseListener(new MouseDragAdapter());
        drag.setTransferHandler(th);

        add(drag, BorderLayout.NORTH);
        add(drop, BorderLayout.SOUTH);

        setDropTarget(new BoardDropTarget(this));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    public JComponent findByName(String name) {
       return (JComponent) asList(getContentPane().getComponents())
               .stream()
               .filter(c -> c.getName().equals(name))
               .findFirst()
               .orElseGet(null);
    }
}
