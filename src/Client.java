import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by munveergill on 05/11/2016.
 */
public class Client {

    public void go(String input){

        try{
            Socket socket = new Socket(input,80);

            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("GET part");
            bufferedWriter.write("GET / HTTP/1.1\r\n");
            System.out.println("after get");
            bufferedWriter.flush();

            String line;
            StringBuffer stringBuffer = new StringBuffer();

            if(bufferedReader.readLine() == null){
                System.out.println("ITS EMPty");
            }

//            FileReader fr=new FileReader(bufferedWriter);
//            BufferedReader br= new BufferedReader(fr);
//            StringBuilder content=new StringBuilder(1024);
//            while((s=br.readLine())!=null)
//            {
//                content.append(s);
//            }


            while ((line = bufferedReader.readLine()) != null){
                System.out.println("LINE: " + line);
                stringBuffer.append(line);
                System.out.println(stringBuffer.toString());
            }

            bufferedWriter.close();
            bufferedReader.close();
            System.out.println("String buffer: " + stringBuffer.toString());

            System.out.println("STOPPED");

//
//            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
//            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//
//            String read = bufferedReader.readLine();
//            System.out.println(read);
//
//            bufferedReader.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        System.out.println("Enter a website: ");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        Client c = new Client();
        c.go(input);
    }

}
