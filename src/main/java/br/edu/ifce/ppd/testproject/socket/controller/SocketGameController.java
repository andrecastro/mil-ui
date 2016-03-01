package br.edu.ifce.ppd.testproject.socket.controller;


import br.edu.ifce.ppd.testproject.App;
import br.edu.ifce.ppd.testproject.controller.GameController;
import br.edu.ifce.ppd.testproject.view.GameView;
import br.edu.ifce.ppd.testproject.view.ListGamesView;
import br.edu.ifce.ppd.testproject.view.helper.Assets;
import br.edu.ifce.ppd.tria.core.model.Game;
import br.edu.ifce.ppd.tria.core.model.Player;
import br.edu.ifce.ppd.tria.core.model.PlayerSelection;
import br.edu.ifce.ppd.tria.core.service.ChatService;
import br.edu.ifce.ppd.tria.core.service.GameService;

import javax.swing.*;
import java.util.List;

import static br.edu.ifce.ppd.testproject.App.client;
import static br.edu.ifce.ppd.testproject.App.log;
import static br.edu.ifce.ppd.testproject.App.mainView;
import static br.edu.ifce.ppd.tria.core.model.PlayerSelection.FIRST_PLAYER;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import static javax.swing.JOptionPane.YES_NO_OPTION;

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
        gameView.lockBoardView();
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
        gameView.lockBoardView();
    }

    public void answerRemovePiece(Game game) {
        log("Piece removed");

        updateGameInfo(game);
        gameView.lockBoardView();
    }

    @Override
    public void movePiece(Integer fromSpotId, Integer toSpotId) {
        gameService.movePiece(client, currentGame.getId(), fromSpotId, toSpotId);
        gameView.lockBoardView();
    }

    public void answerMovePiece(Game game, Boolean canRemovePiece) {
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
    public void askToRestartGame() {
        log("asking to opponent to restart the game");

        gameService.askToRestartGame(null, currentGame.getId());
    }

    @Override
    public void giveUp() {
        log("Giving up");

        gameService.giveUp(null);
        backToInitialView();
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

        updateGameInfo(game);
        gameView.unlockBoardView();
        gameView.unlockChatView();
    }

    @Override
    public void notifyClose() {
        JOptionPane.showMessageDialog(gameView, "Your opponent closed the game abruptly.",
                "Information", INFORMATION_MESSAGE, Assets.info());
        currentPlayer = null;
        currentGame = null;
        gameView = null;
        backToInitialView();
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
    public void notifyMovePiece(Game game, Boolean yourTurn) {
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
    public void notifyGiveUp() {
        JOptionPane.showMessageDialog(gameView, "Your opponent gave up. This game will be closed",
                "Information", INFORMATION_MESSAGE, Assets.info());
        currentPlayer = null;
        currentGame = null;
        gameView = null;
        backToInitialView();
    }

    @Override
    public void notifyAskToRestart() {
        String message = "Your opponent is asking to restart the game. Do you agree?";
        int option = JOptionPane.showConfirmDialog(gameView, message, "Confirm",
                YES_NO_OPTION, QUESTION_MESSAGE, Assets.question());

        if (option == JOptionPane.YES_OPTION) {
            gameService.restartGame(null, currentGame.getId());
        }
    }

    @Override
    public void notifyRestartGame(Game game) {
        String message = "The game was restarted";
        JOptionPane.showMessageDialog(gameView, message);

        updateGameInfo(game);

        if (currentPlayer.getSelection().equals(FIRST_PLAYER)) {
            log("The game was restarted and now is your turn...");
            gameView.unlockBoardView();
        } else {
            log("The game was restarted...");
            log("wait for your opponent to make the play...");

            gameView.lockBoardView();
        }
    }

    @Override
    public void notifyWonGame() {
        JOptionPane.showMessageDialog(gameView, "Congratulations you won the game!!",
                "Information", INFORMATION_MESSAGE, Assets.win());
        backToInitialView();
    }

    @Override
    public void notifyLostGame() {
        JOptionPane.showMessageDialog(gameView, "You lost the game! Get better and try again!",
                "Information", INFORMATION_MESSAGE, Assets.lose());
        backToInitialView();
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