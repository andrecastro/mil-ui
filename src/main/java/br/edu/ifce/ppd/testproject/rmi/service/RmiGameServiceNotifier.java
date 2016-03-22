package br.edu.ifce.ppd.testproject.rmi.service;

import br.edu.ifce.ppd.testproject.controller.GameController;
import br.edu.ifce.ppd.tria.core.model.Game;
import br.edu.ifce.ppd.tria.core.service.GameServiceNotifier;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by andrecoelho on 3/16/16.
 */
public class RmiGameServiceNotifier extends UnicastRemoteObject implements GameServiceNotifier {

    private transient GameController gameController;

    public RmiGameServiceNotifier(GameController gameController) throws RemoteException {
        this.gameController = gameController;
    }

    @Override
    public void notifyCloseGame() throws RemoteException {
        gameController.notifyClose();
    }

    @Override
    public void notifyEnterGame(Game game) throws RemoteException {
        gameController.notifyEnterGame(game);
    }

    @Override
    public void notifyPutPiece(Game game) throws RemoteException {
        gameController.notifyPutPiece(game);
    }

    @Override
    public void notifyMovePiece(Game game) throws RemoteException {
        gameController.notifyMovePiece(game);
    }

    @Override
    public void notifyRemovePiece(Game game) {
        gameController.notifyRemovePiece(game);
    }

    @Override
    public void notifyAskToRestart() throws RemoteException {
        gameController.notifyAskToRestart();
    }

    @Override
    public void notifyRestartGame(Game game) throws RemoteException {
        gameController.notifyRestartGame(game);
    }

    @Override
    public void notifyGiveUp() throws RemoteException{
        gameController.notifyGiveUp();
    }
}
