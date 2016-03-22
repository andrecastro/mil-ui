package br.edu.ifce.ppd.testproject.view;


import br.edu.ifce.ppd.testproject.App;
import br.edu.ifce.ppd.testproject.controller.GameController;
import br.edu.ifce.ppd.testproject.helper.Logger;
import br.edu.ifce.ppd.testproject.view.helper.Assets;

import javax.swing.*;
import java.awt.*;

/**
 * Created by andrecoelho on 2/15/16.
 */
public class MainView extends JFrame {

    private JPanel currentView;
    private MainButtonsView mainButtonsView;
    private LogView logView;

    public MainView(GameController gameController) {
        init(gameController);
    }

    private void init(GameController gameController) {
        logView = new LogView();

        mainButtonsView = new MainButtonsView(gameController);
        currentView = mainButtonsView;

        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        add(currentView, BorderLayout.CENTER);
        add(createScrollLogView(), BorderLayout.SOUTH);

        new Thread(() -> { handleLog(); }).start();

        setIconImage(Assets.icon());
        setSize(new Dimension(830, 630));
        setTitle("Mil Game");
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public void updateCurrentView(JPanel view) {
        this.remove(currentView);
        this.currentView = view;
        this.add(currentView);
        repaint();
        revalidate();
    }

    public void backToInitialView() {
        updateCurrentView(mainButtonsView);
    }

    private void handleLog() {
        while (true) {
            while (Logger.hasLog()) logView.append(Logger.dequeueLog() + "\n");
        }
    }

    private JScrollPane createScrollLogView() {
        JScrollPane logViewPane = new JScrollPane(logView);
        logViewPane.setPreferredSize(new Dimension(702, 100));
        logViewPane.setBorder(BorderFactory.createTitledBorder("Log View"));
        return logViewPane;
    }
}
