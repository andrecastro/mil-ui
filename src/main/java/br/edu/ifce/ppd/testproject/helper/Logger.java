package br.edu.ifce.ppd.testproject.helper;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by andrecoelho on 3/15/16.
 */
public class Logger {

    private final static ConcurrentLinkedQueue<String> logQueue = new ConcurrentLinkedQueue<>();

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
