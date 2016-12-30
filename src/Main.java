import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by munveergill on 06/11/2016.
 */
public class Main {

    public static void main(String[] args) throws IOException {

        Webserver webserver = new Webserver();
        webserver.go();

    }
}
