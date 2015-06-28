package MainServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class ConnectionListener implements Runnable {

    private Subject subject;
    private ServerSocket serverSocket = null;

    public ConnectionListener() {
    }

    public ConnectionListener(Subject sub) {
        this.subject = sub;
    }

    public void run() {
        try {
         PrintWriter pr = new PrintWriter ("someFile.txt");
         pr.println("This works!!!");
         pr.close();
        }
        catch (IOException e) {
        }
        
        try {
            serverSocket = new ServerSocket(65530);
        } catch (IOException e) {
            System.out.println("Server Socket masla");
            e.printStackTrace();
        }

        while (true) {

            try {
                Socket skt = serverSocket.accept();

                BufferedReader bf = new BufferedReader(new InputStreamReader(skt.getInputStream()));
                String emergencyDeptType = "";
                String authToken = "";
                String[] arguments = null;
                DatabaseSingleton dbSingleton = DatabaseSingleton.getInstance();

                if ((authToken = bf.readLine()) != null) {

                    arguments = authToken.split(";");
                    System.out.println(arguments[0] + arguments[1] + arguments[2]);
                    System.out.println("Arg 1 = " + arguments[0] + " Result: " + dbSingleton.Department_Authentication(arguments[1], arguments[2]));

                    if (arguments[0].equals("police") && dbSingleton.Department_Authentication(arguments[1], arguments[2])) {
                        EmergencyDept pDept = new PDept(this.subject, skt, arguments[1]);
                        System.out.println("A new Police Department Connected");
                    } else if (arguments[0].equals("fire") && dbSingleton.Department_Authentication(arguments[1], arguments[2])) {
                        EmergencyDept fbDept = new FBDept(this.subject, skt, arguments[1]);
                        System.out.println("A new Fire Brigade Department Connected");
                    } else if (arguments[0].equals("rescue") && dbSingleton.Department_Authentication(arguments[1], arguments[2])) {
                        EmergencyDept rDept = new RDept(this.subject, skt, arguments[1]);
                        System.out.println("A new Rescue Department Connected");
                    } else {
                        skt.close();
                        System.out.println("Unrecognized Emergency Department tried to connect. Access denied");
                    }
                }
            } catch (SocketException se) {
                System.out.println("Socket exception");

                se.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}