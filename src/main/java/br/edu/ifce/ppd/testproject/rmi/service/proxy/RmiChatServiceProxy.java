package br.edu.ifce.ppd.testproject.rmi.service.proxy;

import br.edu.ifce.ppd.tria.core.model.Client;
import br.edu.ifce.ppd.tria.core.service.ChatService;

import java.rmi.RemoteException;

/**
 * Created by andrecoelho on 3/16/16.
 */
public class RmiChatServiceProxy implements ChatService {

    private ChatService remoteChatService;

    public RmiChatServiceProxy(ChatService remoteChatService) throws RemoteException {
        this.remoteChatService = remoteChatService;
    }

    @Override
    public String sendMessage(Client client, String message) throws RemoteException {
        return remoteChatService.sendMessage(client, message);
    }
}
