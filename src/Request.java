import java.io.*;
import java.net.Socket;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * Created by munveergill on 06/11/2016.
 */
public class Request implements Runnable{

    Socket socket;
    String CRLF = "\n";//returning carriage return (CR) and a line feed (LF)


    public Request(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try{

            process();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void process() {

        try {
            OutputStream output = socket.getOutputStream();
            InputStream is = socket.getInputStream();
            DataOutputStream dos = new DataOutputStream(output);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));

            String requestLine = bufferedReader.readLine();

            System.out.println();
            System.out.println(requestLine);

            StringTokenizer stringTokenizer = new StringTokenizer(requestLine);
            stringTokenizer.nextToken();
            String file = stringTokenizer.nextToken();

            file = "." + file;

            // open the file

            FileInputStream fileInputStream = null;
            boolean doesTheFileExist = true;
            try{
                fileInputStream = new FileInputStream(file);
                System.out.println("INSIDE try");
            }catch (FileNotFoundException f){
                doesTheFileExist = false;
                System.out.println("FILE: " + file);
                System.out.println("FIS: " + fileInputStream);
                System.out.println("FILE DOESNT EXIST");
            }

            // Construct the http headers

            String statusLine;
            String contentType;
            String body = null;
            String day;
            Date date = Calendar.getInstance().getTime();


            if (doesTheFileExist) {
                statusLine = "HTTP/1.1 200 OK" + CRLF ; //common success message
                day = "Date: " + date.toString() + CRLF;
                contentType = "Content-type: " + contentType( file ) + CRLF;
            }//content info


            else {
                statusLine = "HTTP/1.1 404 Not Found" + CRLF;//common error message
                day = "Date: " + date.toString() + CRLF;
                contentType = "Content-type: " + "text/html" + CRLF ;//content info
                body = "<HTML>" +
                        "<HEAD><TITLE>Not Found</TITLE></HEAD>" +
                        "<BODY>Not Found</BODY></HTML>" + CRLF;
            }

            System.out.println("STATUS LINE " + statusLine);

            dos.writeBytes(statusLine);
            dos.writeBytes(day);
            dos.writeBytes(contentType);
            dos.writeBytes(CRLF);
            
            if (doesTheFileExist){
                sendBytes(fileInputStream, dos);
//                dos.writeBytes(statusLine);
//                dos.writeBytes(contentType);
                fileInputStream.close();
            }
            else {
//                dos.writeBytes(statusLine);
                dos.writeBytes(body);
//                dos.writeBytes(contentType);
            }

            System.out.println("****************************");
            System.out.println(file);
            System.out.println("****************************");

//            String headerLine = null;
//            while ((headerLine = bufferedReader.readLine()).length() !=0){
//                System.out.println(headerLine);
//            }

            dos.close();
            bufferedReader.close();
            socket.close();


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void sendBytes(FileInputStream fileInputStream, DataOutputStream dos) {

        try {
            byte[] buffer = new byte[1024];
            int bytes = 0;
            while ((bytes = fileInputStream.read(buffer)) != -1){
                dos.write(buffer, 0, bytes);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String contentType(String file) {
//        System.out.println("CONTENT TYPE: " + file);
        if(file.endsWith(".htm") || file.endsWith(".html")) {return "text/html; charset=utf-8";}
        if(file.endsWith(".jpg") || file.endsWith(".jpeg")) {return "image/jpeg";}
        if(file.endsWith(".gif")) {return "image/gif";}
        return "application/octet-stream";
    }
}
