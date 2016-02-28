package br.edu.ifce.ppd.testproject.socket.service;

import br.edu.ifce.ppd.testproject.socket.SocketClient;
import br.edu.ifce.ppd.tria.core.model.Client;
import br.edu.ifce.ppd.tria.core.protocol.Action;
import br.edu.ifce.ppd.tria.core.service.GameService;

import static br.edu.ifce.ppd.tria.core.protocol.helper.ActionBuilder.anAction;

/**
 * Created by andrecoelho on 2/20/16.
 */
public class SocketGameService implements GameService {

    private SocketClient socketClient;

    public SocketGameService(SocketClient socketClient) {
        this.socketClient = socketClient;
    }

    @Override
    public Action retrieveIdleGames() {
        Action retrieveIdleGames = anAction()
                .to("game-service/idle-games")
                .build();

        return  socketClient.sendAction(retrieveIdleGames);
    }

    @Override
    public Action createGame(Client client, String gameAlias, String playerName) {
        Action createGame = anAction()
                .to("game-service/create-game")
                .withParamValue("game-alias", gameAlias)
                .withParamValue("player-name", playerName)
                .build();

        return socketClient.sendAction(createGame);
    }

    @Override
    public Action enterGame(Client client, String gameId, String playerName) {
        Action createGame = anAction()
                .to("game-service/enter-game")
                .withParamValue("game-id", gameId)
                .withParamValue("player-name", playerName)
                .build();

        return socketClient.sendAction(createGame);
    }

    @Override
    public Action putPieceInSpot(Client client, String gameId, Integer selectedSpotId) {
        Action putPieceInSpot = anAction()
                .to("game-service/put-piece-in-spot")
                .withParamValue("game-id", gameId)
                .withParamValue("selected-spot-id", selectedSpotId)
                .build();

        return socketClient.sendAction(putPieceInSpot);
    }

    @Override
    public Action removePiece(Client client, String gameId, Integer selectedSpotId) {
        Action removePiece = anAction()
                .to("game-service/remove-piece")
                .withParamValue("game-id", gameId)
                .withParamValue("selected-spot-id", selectedSpotId)
                .build();

        return socketClient.sendAction(removePiece);
    }
}
