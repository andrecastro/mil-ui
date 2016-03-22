package br.edu.ifce.ppd.testproject.rmi.service.proxy;

import br.edu.ifce.ppd.testproject.helper.Logger;
import br.edu.ifce.ppd.tria.core.model.Client;
import br.edu.ifce.ppd.tria.core.service.ChatServiceNotifier;
import br.edu.ifce.ppd.tria.core.service.GameServiceNotifier;
import br.edu.ifce.ppd.tria.core.service.RegisterService;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;

/**
 * Created by andrecoelho on 3/16/16.
 */
public class RmiRegisterServiceProxy implements RegisterService {

    private RegisterService remoteRegisterService;

    private GameServiceNotifier gameServiceNotifier;
    private ChatServiceNotifier chatServiceNotifier;

    public RmiRegisterServiceProxy(RegisterService remoteRegisterService,
              GameServiceNotifier gameServiceNotifier, ChatServiceNotifier chatServiceNotifier) {
        this.remoteRegisterService = remoteRegisterService;
        this.gameServiceNotifier = gameServiceNotifier;
        this.chatServiceNotifier = chatServiceNotifier;
    }

    @Override
    public Client createClient() throws RemoteException {
        return remoteRegisterService.createClient();
    }

    @Override
    public Client register(Client client) throws RemoteException {
        client.setHost("localhost");
        client.setPort(createRegistryOnARandomPort());
        registerNotifiers(client);
        return remoteRegisterService.register(client);
    }

    @Override
    public void deregister(Client client) throws RemoteException {
        deregisterNotifiers(client);
        remoteRegisterService.deregister(client);
    }

    private void deregisterNotifiers(Client client) {
        try {
            Registry clientRegistry = LocateRegistry.getRegistry(client.getPort());
            clientRegistry.unbind("GameServiceNotifier" + client.getId());
            clientRegistry.unbind("ChatServiceNotifier" + client.getId());
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }

    private void registerNotifiers(Client client) {
        Logger.log("Registering notifiers");

        try {
            Registry clientRegistry = LocateRegistry.getRegistry(client.getHost(), client.getPort());
            clientRegistry.rebind("GameServiceNotifier" + client.getId(), gameServiceNotifier);
            clientRegistry.rebind("ChatServiceNotifier" + client.getId(), chatServiceNotifier);

            Logger.log("Notifiers registered successfully");
        } catch (RemoteException e) {
            e.printStackTrace();
            Logger.log("Error registering notifiers");
        }
    }

    private static Integer createRegistryOnARandomPort() {
        Registry clientRegistry = null;
        Integer port = null;


        while (clientRegistry == null) {
            try {
                port = 2000 + new Random().nextInt(1000);
                clientRegistry = LocateRegistry.createRegistry(port);
            } catch (RemoteException ignored) {
            }
        }

        return port;
    }
}
