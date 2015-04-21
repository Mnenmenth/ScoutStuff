import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by mnenmenth on 3/24/15.
 */
public class Server{
    public final static int PORT = 25565;
    public static String data = null;
    public static FileOutputStream fos = null;
    public static BufferedOutputStream bos = null;
    public static ServerSocket sSocket;
    public static Socket socket = null;
    public static void main(String[] args) throws IOException {
        try {
            sSocket = new ServerSocket(PORT);
            while (true) {
                try {
                    //String test = "Alliance: Red Alliance|Team #: 245|Match #: 45|Autonomous Container Score: 3|Autonomous Tote Score: 23|Autonomous Tote Stack: 34|Teleop Container Noodle: true|Teleop Stack Height: 2|Teleop Tote Score: 234|Shoot Door?: true|Upside Down Tote: true|Has Auton: true|Landfill: true";
                    System.out.println("Waiting for client connection...");
                    socket = sSocket.accept();

                    System.out.println("Client Connected");
                    InputStreamReader input = new InputStreamReader(Server.socket.getInputStream());
                    BufferedReader reader = new BufferedReader(input);

                    Server.data = reader.readLine();
                    System.out.println("\n" + Server.data);
                    DoStuff doStuff = new DoStuff();
                    doStuff.start();
                } finally {
                    if (fos != null) fos.close();
                    if (bos != null) bos.close();
                    if (socket != null) socket.close();
                }
            }
        } catch (IOException e) {
            System.out.println("Server not started");
        }
    }
}

class DoStuff extends Thread implements Runnable{
    public void run() {
        try {
            //String test = "Alliance: Red Alliance|Team #: 245|Match #: 45|Autonomous Container Score: 3|Autonomous Tote Score: 23|Autonomous Tote Stack: 34|Teleop Container Noodle: true|Teleop Stack Height: 2|Teleop Tote Score: 234|Shoot Door?: true|Upside Down Tote: true|Has Auton: true|Landfill: true";

            Generation g = new Generation(Server.data);
            g.excelGen();
            join();
        } catch (InterruptedException e){
            System.err.println("You suck java.");
        }
    }
}