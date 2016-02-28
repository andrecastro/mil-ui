package br.edu.ifce.ppd.testproject.controller;

import br.edu.ifce.ppd.tria.core.model.Game;
import br.edu.ifce.ppd.tria.core.model.Player;

/**
 * Created by andrecoelho on 2/21/16.
 */
public interface GameController {

    void createGame(String alias, String playerName);

    void enterGame(Game game, String playerName);

    void lisGames();

    void putPiece(Integer selectedSpotId);

    void removePiece(Integer selectedSpotId);

    void sendMessage(String message);

    void giveUp();

    void backToInitialView();

    void notifySendMessage(String playerName, String message);

    void notifyEnterGame(Game game);

    void notifyClose();

    void notifyPutPiece(Game game, Boolean yourTurn);

    void notifyRemovePiece(Game game);

    void notifyMovePiece(Game game, Boolean yourTurn);

    void notifyGiveUp();

    void notifyAskToRestart();

    void notifyRestartGame(Game game);

    Player currentPlayer();

    Game currentGame();

    void movePiece(Integer fromSpotId, Integer toSpotId);

    void askToRestartGame();

    void notifyWonGame();

    void notifyLostGame();
}
