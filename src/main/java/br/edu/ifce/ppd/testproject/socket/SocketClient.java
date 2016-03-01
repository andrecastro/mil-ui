package br.edu.ifce.ppd.testproject.socket;

import br.edu.ifce.ppd.testproject.App;
import br.edu.ifce.ppd.tria.core.protocol.Action;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

import static br.edu.ifce.ppd.testproject.App.log;

/**
 * Created by andrecoelho on 2/20/16.
 */
public class SocketClient {

    private volatile boolean connected;

    private Socket socket;
    private ConcurrentLinkedQueue<Action> writeQueue;

    private ObjectOutputStream out;
    private ObjectInputStream in;

    private Route route;

    public String start(Route route) {
        this.route = route;
        try {
            String message = connectWithServer();
            startReadThread();
            startWriteThread();

            return message;
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    public void close() {
        if (socket != null) {
            try {
                Action close = new Action("register-service/close-game", null);
                out.writeObject(close);
                connected = false;
                writeQueue.clear();;
                socket.close();
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Action sendAction(Action action) {
        if (writeQueue == null) {
            log("Impossible to communicate with server...");
            return null;
        }
        writeQueue.offer(action);
        return action;
    }

    private String connectWithServer() throws IOException, ClassNotFoundException {
        socket = new Socket("localhost",8088);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        writeQueue = new ConcurrentLinkedQueue<>();
        connected = true;
        return (String) in.readObject();
    }

    private void startWriteThread() {
        new Thread(() -> { while (connected) { handleRead(); } }).start();
    }

    private void startReadThread() {
        new Thread(() -> { while (connected) { handleWrite();} }).start();
    }

    private void handleWrite() {
        try {
            while (!writeQueue.isEmpty()) {
                out.writeUnshared(writeQueue.poll());
                out.reset();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleRead() {
        try {
            Action action = (Action) in.readUnshared();
            route.to(action);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
