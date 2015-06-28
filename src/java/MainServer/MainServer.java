package MainServer; 

//import java.io.*;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.net.SocketException;
//import MainServer.EmergencyNotification;
//import java.net.*;
//import java.lang.*;

public class MainServer{
	static Subject subject = new Subject();

	public static void mmain(String[] args) {
		
		/*
                    TODO list
                    - Make an object of subject and pass it to both the threads.
		*/
                
                //MainServerView.star
               
                System.out.println("Server is ready!");
            
		Thread notificationThread = new Thread (new NotificationListener (subject));
		Thread connectionThread = new Thread (new ConnectionListener (subject));

		notificationThread.setDaemon (true);
		connectionThread.setDaemon (true);

		notificationThread.start ();
		connectionThread.start();
               
		try {
			notificationThread.join ();
			connectionThread.join ();
		}
		catch (Exception e) {
			System.out.println ("Problems with thread join");
		}

	}
}
