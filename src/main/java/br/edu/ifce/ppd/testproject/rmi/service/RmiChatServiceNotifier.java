package br.edu.ifce.ppd.testproject.rmi.service;

import br.edu.ifce.ppd.testproject.controller.GameController;
import br.edu.ifce.ppd.tria.core.service.ChatServiceNotifier;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by andrecoelho on 3/16/16.
 */
public class RmiChatServiceNotifier extends UnicastRemoteObject implements ChatServiceNotifier {

    private transient GameController gameController;

    public RmiChatServiceNotifier(GameController gameController) throws RemoteException {
        this.gameController = gameController;
    }

    @Override
    public void notifySendMessage(String playerName, String message) throws RemoteException {
        gameController.notifySendMessage(playerName, message);
    }
}
