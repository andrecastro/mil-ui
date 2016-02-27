package br.edu.ifce.ppd.testproject.socket.config;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by andrecoelho on 2/26/16.
 */
public interface RouteExecutor {

    void execute(HashMap<String, Serializable> body);

}
