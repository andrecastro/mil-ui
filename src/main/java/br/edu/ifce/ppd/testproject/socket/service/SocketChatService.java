package br.edu.ifce.ppd.testproject.socket.service;

import br.edu.ifce.ppd.testproject.socket.SocketClient;
import br.edu.ifce.ppd.tria.core.model.Client;
import br.edu.ifce.ppd.tria.core.protocol.Action;
import br.edu.ifce.ppd.tria.core.service.ChatService;

import static br.edu.ifce.ppd.tria.core.protocol.helper.ActionBuilder.anAction;

/**
 * Created by andrecoelho on 2/20/16.
 */
public class SocketChatService implements ChatService {

    private SocketClient socketClient;

    public SocketChatService(SocketClient socketClient) {
        this.socketClient = socketClient;
    }

    @Override
    public Action sendMessage(Client client, String message) {
        Action sendMessage = anAction()
                .to("chat-service/send-message")
                .withParamValue("message", message)
                .build();

        socketClient.sendAction(sendMessage);
        return sendMessage;
    }
}
