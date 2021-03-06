package br.edu.ifce.ppd.testproject.socket;


import br.edu.ifce.ppd.testproject.controller.GameController;
import br.edu.ifce.ppd.testproject.socket.config.RouteExecutor;
import br.edu.ifce.ppd.tria.core.model.Game;
import br.edu.ifce.ppd.tria.core.protocol.Action;

import java.util.HashMap;
import java.util.List;

/**
 * Created by andrecoelho on 2/20/16.
 */
public class Route {

    private GameController gameController;
    private HashMap<String, RouteExecutor> configuredRoutes;

    public Route(GameController gameController) {
        this.gameController = gameController;
        this.configuredRoutes = new HashMap<>();
        this.configure();
    }

    private void configure() {
        addPath("register-service/notify-close-game", (body) -> gameController.notifyClose());
        addPath("game-service/create-game", (body) -> gameController.answerCreateGame((Game) body.get("game")));
        addPath("game-service/idle-games", (body) -> gameController.answerListGames((List<Game>) body.get("idle-games")));
        addPath("game-service/enter-game", (body) -> gameController.answerEnterGame((Game) body.get("game")));
        addPath("game-service/notify-enter-game", (body) -> gameController.notifyEnterGame((Game) body.get("game")));
        addPath("game-service/remove-piece", (body) -> gameController.answerRemovePiece((Game) body.get("game")));
        addPath("game-service/put-piece", (body) -> gameController.answerPutPiece((Game) body.get("game")));
        addPath("game-service/move-piece", (body) -> gameController.answerMovePiece((Game) body.get("game")));

        addPath("game-service/notify-put-piece", (body) -> gameController.notifyPutPiece((Game) body.get("game")));
        addPath("game-service/notify-remove-piece", (body) -> gameController.notifyRemovePiece((Game) body.get("game")));
        addPath("game-service/notify-move-piece", (body) -> gameController.notifyMovePiece((Game) body.get("game")));
        addPath("game-service/notify-give-up", (body) -> gameController.notifyGiveUp());

        addPath("game-service/notify-ask-to-restart", (body) -> gameController.notifyAskToRestart());

        addPath("game-service/notify-restart-game", (body) -> gameController.notifyRestartGame((Game) body.get("game")));

        addPath("chat-service/notify-send-message", (body) -> {
            gameController.notifySendMessage((String) body.get("player-name"), (String) body.get("message"));
        });
    }

    private void addPath(String path, RouteExecutor routeExecutor) {
        this.configuredRoutes.put(path, routeExecutor);
    }

    public void to(Action action) {
        RouteExecutor executor = configuredRoutes.get(action.getPath());

        if (executor != null)
            executor.execute(action.getBody());
    }
}
