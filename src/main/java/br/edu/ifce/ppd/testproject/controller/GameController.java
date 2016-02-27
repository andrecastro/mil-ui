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

    void sendMessage(String message);

    void backToInitialView();

    void notifySendMessage(String playerName, String message);

    void notifyEnterGame(Game game);

    Player currentPlayer();

    Game currentGame();

    void notifyClose();

    void notifyPutPiece(Game game, Boolean yourTurn);
}
