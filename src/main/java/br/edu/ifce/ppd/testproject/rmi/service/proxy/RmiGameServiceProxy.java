package br.edu.ifce.ppd.testproject.rmi.service.proxy;

import br.edu.ifce.ppd.testproject.controller.GameController;
import br.edu.ifce.ppd.tria.core.model.Client;
import br.edu.ifce.ppd.tria.core.model.Game;
import br.edu.ifce.ppd.tria.core.service.GameService;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Created by andrecoelho on 3/16/16.
 */
public class RmiGameServiceProxy implements GameService {

    private GameService remoteGameService;
    private GameController gameController;

    public RmiGameServiceProxy(GameService remoteGameService) {
        this.remoteGameService = remoteGameService;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public ArrayList<Game> retrieveIdleGames() throws RemoteException {
        ArrayList<Game> idleGames = remoteGameService.retrieveIdleGames();
        gameController.answerListGames(idleGames);
        return idleGames;
    }

    @Override
    public Game createGame(Client client, String gameAlias, String playerOne) throws RemoteException {
        Game game = remoteGameService.createGame(client, gameAlias, playerOne);
        gameController.answerCreateGame(game);
        return game;
    }

    @Override
    public Game enterGame(Client client, String gameId, String playerTwo) throws RemoteException {
        Game game = remoteGameService.enterGame(client, gameId, playerTwo);
        gameController.answerEnterGame(game);
        return game;
    }

    @Override
    public Game putPieceInSpot(Client client, String gameId, Integer selectedSpotId) throws RemoteException {
        Game game = remoteGameService.putPieceInSpot(client, gameId, selectedSpotId);
        gameController.answerPutPiece(game);
        return game;
    }

    @Override
    public Game removePiece(Client client, String gameId, Integer selectedSpotId) throws RemoteException {
        Game game = remoteGameService.removePiece(client, gameId, selectedSpotId);
        gameController.answerRemovePiece(game);
        return game;
    }

    @Override
    public Game movePiece(Client client, String gameId, Integer fromSpotId, Integer toSpotId) throws RemoteException {
        Game game = remoteGameService.movePiece(client, gameId, fromSpotId, toSpotId);
        gameController.answerMovePiece(game);
        return game;
    }

    @Override
    public Game askToRestartGame(Client client, String gameId) throws RemoteException {
        return remoteGameService.askToRestartGame(client, gameId);
    }

    @Override
    public Game restartGame(Client client, String gameId) throws RemoteException {
        return remoteGameService.restartGame(client, gameId);
    }

    @Override
    public Game giveUp(Client client) throws RemoteException {
        return remoteGameService.giveUp(client);
    }
}
