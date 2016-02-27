package br.edu.ifce.ppd.testproject.socket.service;

import br.edu.ifce.ppd.testproject.socket.Route;
import br.edu.ifce.ppd.testproject.socket.SocketClient;
import br.edu.ifce.ppd.tria.core.model.Client;
import br.edu.ifce.ppd.tria.core.service.RegisterService;

/**
 * Created by andrecoelho on 2/21/16.
 */
public class SocketRegisterService implements RegisterService {

    private SocketClient socketClient;
    private Route route;

    public SocketRegisterService(SocketClient socketClient, Route route) {
        this.socketClient = socketClient;
        this.route = route;
    }

    @Override
    public Client register() {
        String clientId = socketClient.start(route);

        if (clientId == null)
            return null;

        return new Client(clientId);
    }

    @Override
    public void deregister(Client client) {
        socketClient.close();
    }
}
