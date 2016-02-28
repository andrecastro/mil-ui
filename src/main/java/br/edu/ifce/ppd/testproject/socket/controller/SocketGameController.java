package br.edu.ifce.ppd.testproject.socket.controller;


import br.edu.ifce.ppd.testproject.App;
import br.edu.ifce.ppd.testproject.controller.GameController;
import br.edu.ifce.ppd.testproject.view.GameView;
import br.edu.ifce.ppd.testproject.view.ListGamesView;
import br.edu.ifce.ppd.tria.core.model.Game;
import br.edu.ifce.ppd.tria.core.model.Player;
import br.edu.ifce.ppd.tria.core.service.ChatService;
import br.edu.ifce.ppd.tria.core.service.GameService;

import java.util.List;

import static br.edu.ifce.ppd.testproject.App.log;

/**
 * Created by andrecoelho on 2/14/16.
 */
public class SocketGameController implements GameController {

    private GameService gameService;
    private ChatService chatService;

    private volatile GameView gameView;
    private volatile Game currentGame;
    private volatile Player currentPlayer;

    public SocketGameController(GameService gameService, ChatService chatService) {
        this.gameService = gameService;
        this.chatService = chatService;
    }

    @Override
    public void createGame(String alias, String playerName) {
        log("Creating new game...");

        gameService.createGame(null, alias, playerName);
    }

    public void answerCreateGame(Game game) {
        log("New game created...");
        log("Waiting second player...");

        currentGame = game;
        currentPlayer = game.getFirstPlayer();
        gameView = new GameView(this);
        App.mainView.updateCurrentView(gameView);
    }

    @Override
    public void lisGames() {
        log("Listing games...");

        gameService.retrieveIdleGames();
    }

    public void answerListGames(List<Game> games) {
        log("List of games retrieved...");

        ListGamesView gamesView = new ListGamesView(games, this);
        App.mainView.updateCurrentView(gamesView);
    }

    @Override
    public void enterGame(Game game, String playerName) {
        log("Entering a game: " + game.getId() + "...");

        gameService.enterGame(null, game.getId(), playerName);
    }

    public void answerEnterGame(Game game) {
        log("Game: " + game.getId() + "...");
        log("Your opponent is " + game.getFirstPlayer().getName() + "...");
        log("Waiting for " + game.getFirstPlayer().getName() + " make the play...");

        currentGame = game;
        currentPlayer = game.getSecondPlayer();
        gameView = new GameView(this);
        gameView.unlockChatView();
        App.mainView.updateCurrentView(gameView);
    }

    @Override
    public void putPiece(Integer selectedSpotId) {
        log("Placing the piece...");

        gameService.putPieceInSpot(null, currentGame.getId(), selectedSpotId);
    }

    public void answerPutPiece(Game game, Boolean canRemovePiece) {
        updateGameInfo(game);

        if (canRemovePiece) {
            log("Congratulations you made a mil! Select an opponent piece to remove...");
            gameView.unlockBoardView();
        } else {
            log("Wait for your opponent to make a play...");
            gameView.lockBoardView();
        }
    }

    @Override
    public void removePiece(Integer selectedSpotId) {
        log("Removing piece...");

        gameService.removePiece(null, currentGame.getId(), selectedSpotId);
    }

    public void answerRemovePiece(Game game) {
        log("Piece removed");

        updateGameInfo(game);
        gameView.lockBoardView();
    }

    @Override
    public void sendMessage(String message) {
        chatService.sendMessage(null, message);
    }

    @Override
    public void notifySendMessage(String playerName, String message) {
        gameView.messageToChat(playerName, message);
    }

    @Override
    public void notifyEnterGame(Game game) {
        log("Game: " + game.getId() + "...");
        log("Your opponent is " + game.getSecondPlayer().getName() + "...");

        currentGame = game;
        gameView.unlockBoardView();
        gameView.unlockChatView();
    }

    @Override
    public void notifyClose() {
        log("Your opponent closed the game abruptly");

        currentPlayer = null;
        currentGame = null;
        gameView = null;
        App.mainView.backToInitialView();
    }

    @Override
    public void notifyPutPiece(Game game, Boolean yourTurn) {
        updateGameInfo(game);

        if (yourTurn) {
            log("Is your turn...");
            gameView.unlockBoardView();
        } else {
            log("Wait for your opponent to make another play...");
            gameView.lockBoardView();
        }
    }

    @Override
    public void notifyRemovePiece(Game game) {
        log("You lost a piece...");
        log("Now is your turn...");

        updateGameInfo(game);
        gameView.unlockBoardView();
    }

    @Override
    public void backToInitialView() {
        App.mainView.backToInitialView();
    }

    @Override
    public Player currentPlayer() {
        return currentPlayer;
    }

    @Override
    public Game currentGame() {
        return currentGame;
    }

    private void updateGameInfo(Game game) {
        this.currentGame = game;
        updateCurrentPlayer();
        gameView.updateBoarView();
    }

    private void updateCurrentPlayer() {
        if (currentGame.getFirstPlayer().equals(currentPlayer)) {
            currentPlayer = currentGame.getFirstPlayer();
        } else {
            currentPlayer = currentGame.getSecondPlayer();
        }
    }
}


