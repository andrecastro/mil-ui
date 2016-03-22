package br.edu.ifce.ppd.testproject;

import br.edu.ifce.ppd.testproject.controller.GameController;
import br.edu.ifce.ppd.testproject.helper.Logger;
import br.edu.ifce.ppd.testproject.rmi.service.RmiChatServiceNotifier;
import br.edu.ifce.ppd.testproject.rmi.service.RmiGameServiceNotifier;
import br.edu.ifce.ppd.testproject.rmi.service.proxy.RmiChatServiceProxy;
import br.edu.ifce.ppd.testproject.rmi.service.proxy.RmiGameServiceProxy;
import br.edu.ifce.ppd.testproject.rmi.service.proxy.RmiRegisterServiceProxy;
import br.edu.ifce.ppd.testproject.socket.Route;
import br.edu.ifce.ppd.testproject.socket.SocketClient;
import br.edu.ifce.ppd.testproject.socket.service.SocketChatServiceProxy;
import br.edu.ifce.ppd.testproject.socket.service.SocketGameServiceProxy;
import br.edu.ifce.ppd.testproject.socket.service.SocketRegisterServiceProxy;
import br.edu.ifce.ppd.testproject.view.MainView;
import br.edu.ifce.ppd.tria.core.model.Client;
import br.edu.ifce.ppd.tria.core.service.ChatService;
import br.edu.ifce.ppd.tria.core.service.GameService;
import br.edu.ifce.ppd.tria.core.service.RegisterService;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class App {

    public static Client client;
    public static MainView mainView;

    public static void main( String[] args ) throws RemoteException, NotBoundException {

//        mainView = createSocketApp();
        mainView = createRmiApp();

        if (client == null) {
            Logger.log("Connection refused...");
        } else {
            Logger.log("Connection established with client: " + client.getId() + "...");
        }
    }

    private static MainView createSocketApp() throws RemoteException {
        SocketClient socketClient = new SocketClient();

        // Chat
        ChatService chatService = new SocketChatServiceProxy(socketClient);

        // Game
        GameService gameService = new SocketGameServiceProxy(socketClient);
        GameController gameController = new GameController(gameService, chatService);

        // Registration
        Route route = new Route(gameController);
        RegisterService registerService = new SocketRegisterServiceProxy(socketClient, route);
        client = registerService.register(null);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                registerService.deregister(client);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }));

        return new MainView(gameController);
    }

    private static MainView createRmiApp() throws RemoteException, NotBoundException {
        String remoteGameServiceName = "GameService";
        String remoteChatServiceName = "ChatService";
        String remoteRegisterServiceName = "RegisterService";

        Registry remoteRegistry = LocateRegistry.getRegistry("localhost", 2020);

        RegisterService remoteRegisterService = (RegisterService) remoteRegistry.lookup(remoteRegisterServiceName);
        ChatService remoteChatService = (ChatService) remoteRegistry.lookup(remoteChatServiceName);
        GameService remoteGameService = (GameService) remoteRegistry.lookup(remoteGameServiceName);

        // Chat
        RmiChatServiceProxy chatServiceProxy = new RmiChatServiceProxy(remoteChatService);

        // Game
        RmiGameServiceProxy gameServiceProxy = new RmiGameServiceProxy(remoteGameService);
        GameController gameController = new GameController(gameServiceProxy, chatServiceProxy);
        gameServiceProxy.setGameController(gameController);

        // Notifiers
        RmiGameServiceNotifier gameServiceNotifier = new RmiGameServiceNotifier(gameController);
        RmiChatServiceNotifier chatServiceNotifier = new RmiChatServiceNotifier(gameController);

        // Registration
        RmiRegisterServiceProxy registerServiceProxy = new RmiRegisterServiceProxy(remoteRegisterService,
                gameServiceNotifier, chatServiceNotifier);

        client = registerServiceProxy.createClient();
        registerServiceProxy.register(client);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                registerServiceProxy.deregister(client);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }));

        return new MainView(gameController);
    }
}
