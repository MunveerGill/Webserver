
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by munveergill on 02/11/2016.
 */
public class Webserver {
    

    public void go() throws IOException {
        int port = 8088;

// Establish the listen socket.
        ServerSocket welcomeSocket = new ServerSocket(port);
        System.out.println(welcomeSocket.getLocalSocketAddress());


// Process HTTP service requests in an infinite loop.
        while (true) {
            Socket connectionSocket = welcomeSocket.accept();
            Request request = new Request( connectionSocket );

            Thread thread = new Thread(request);

            thread.start();
        }

    }


}
