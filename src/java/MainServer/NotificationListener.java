package MainServer; 

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import MainServer.EmergencyNotification;
import java.net.*;
import java.lang.*;


class NotificationListener implements Runnable {
	
	private ServerSocket serverSocket = null;
	private Socket socket = null;
	private ObjectInputStream inStream = null;

	static Subject subject;

	EmergencyNotification eNotif = null;

	public NotificationListener () {}
	public NotificationListener (Subject sub) {
		this.subject = sub;
	}

	public void run () {


		try {
			serverSocket = new ServerSocket(15002);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (NullPointerException ne)
		{
			ne.printStackTrace();
		}

		while (true) {

			try {
				Socket skt = serverSocket.accept();
				System.out.println("Connected");

				inStream = new ObjectInputStream(skt.getInputStream());
				String temp = (String) inStream.readObject();
				eNotif = new EmergencyNotification (temp);
				subject.setState(eNotif);
                                System.out.println("Object received = " + eNotif.toString ());
				skt.close();
				
			} 
			catch (SocketException se) {
				
				System.out.println ("Socket wala masla");
				se.printStackTrace();
			
			} 
			catch (IOException e) {
				e.printStackTrace();
			} 
			catch (ClassNotFoundException cn) {
				cn.printStackTrace();
			}
		}
	}
}