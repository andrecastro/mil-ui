package br.edu.ifce.ppd.testproject;

import br.edu.ifce.ppd.testproject.controller.GameController;
import br.edu.ifce.ppd.testproject.socket.Route;
import br.edu.ifce.ppd.testproject.socket.SocketClient;
import br.edu.ifce.ppd.testproject.socket.controller.SocketGameController;
import br.edu.ifce.ppd.testproject.socket.service.SocketChatService;
import br.edu.ifce.ppd.testproject.socket.service.SocketGameService;
import br.edu.ifce.ppd.testproject.socket.service.SocketRegisterService;
import br.edu.ifce.ppd.testproject.view.MainView;
import br.edu.ifce.ppd.tria.core.model.Client;
import br.edu.ifce.ppd.tria.core.service.ChatService;
import br.edu.ifce.ppd.tria.core.service.GameService;
import br.edu.ifce.ppd.tria.core.service.RegisterService;

import java.util.concurrent.ConcurrentLinkedQueue;

public class App {

    private final static ConcurrentLinkedQueue<String> logQueue = new ConcurrentLinkedQueue<>();

    public static Client client;
    public static MainView mainView;

    public static void main( String[] args ) {
        SocketClient socketClient = new SocketClient();

        // Chat
        ChatService chatService = new SocketChatService(socketClient);

        // Game
        GameService gameService = new SocketGameService(socketClient);
        GameController gameController = new SocketGameController(gameService, chatService);

        // Registration
        Route route = new Route((SocketGameController) gameController);
        RegisterService registerService = new SocketRegisterService(socketClient, route);
        Client client = registerService.register();

        mainView = new MainView(gameController);

        if (client == null) {
            log("Connection refused...");
        } else {
            log("Connection established with client: " + client.getId() + "...");
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> { registerService.deregister(client); }));
    }

    public synchronized static boolean hasLog() {
        return !logQueue.isEmpty();
    }

    public synchronized static String dequeueLog() {
        return logQueue.poll();
    }

    public synchronized static void log(String message) {
        logQueue.offer(message);
    }
}
