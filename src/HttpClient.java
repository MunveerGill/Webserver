import java.net.*;
import java.io.*;
import java.util.Scanner;


public class HttpClient {
    public static void main ( String[] args ) throws IOException
    {
        System.out.println("Enter a website: ");
        Scanner scanner = new Scanner(System.in);
        String url = scanner.nextLine();
        String htmlFile = "";
        if(url.contains("localhost")){
            System.out.println("What html file? (include the .html)");
            htmlFile = scanner.nextLine();
        }

        HttpClient hp = new HttpClient();
        hp.getDoc(url, htmlFile);


    }

    public void getDoc(String inputtedURL, String htmlFile) throws IOException {

        String host = inputtedURL;
        String file ;
        int port = 80;
        if(host.contains("localhost")){
            file = "/" + htmlFile;
            port=8088;
        }
        else {
            file = "/";

        }

        Socket s = new Socket(host, port);

        try
        {


            OutputStream out = s.getOutputStream();
            System.out.println("After output stream");
            PrintWriter outw = new PrintWriter(out, false);
            System.out.println("after print writer");
            // if localhost then file needs /blah.html else if host = bbc then file equals /


            outw.println("GET " + file +  " HTTP/1.1");
            outw.println("Host: " + host);
            outw.println("Accept: */*");
            outw.println("User-Agent: Java"); // Be honest.
            outw.println(""); // Important, else the server will expect that there's more into the request.
            outw.flush();

            InputStream in = s.getInputStream();
            InputStreamReader inr = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(inr);
            String line;
            while ((line = br.readLine()) != null)
            {
                System.out.println(line);
            }
            // br.close();          // Q. Do I need this?
        }
        catch (UnknownHostException e) {}
        catch (IOException e) {}

        if (s != null)
        {
            try
            {
                s.close();
            }
            catch ( IOException ioEx ) {}
        }

    }
}