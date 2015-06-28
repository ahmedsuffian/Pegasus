package MainServer;

import java.net.Socket;
import java.io.ObjectOutputStream;
import java.net.*;
import java.io.*;

public abstract class EmergencyDept {

    protected Subject subject;
    protected int port;
    protected InetAddress ipAddress;
    protected String type;
    protected Socket socket;
    protected String dep_username;

    public abstract void update(Subject observable, String Type);

    public EmergencyDept(Subject sub, Socket skt, String dep_user) {
        this.subject = sub;
        this.socket = skt;
        this.subject.attach(this);
        this.dep_username = dep_user;
    }
    
    protected void forwardNotification (EmergencyNotification notification) {
        
        DatabaseSingleton db =  DatabaseSingleton.getInstance(); 

        
        try{
            ObjectOutputStream outputStream = new ObjectOutputStream(this.socket.getOutputStream());
            outputStream.writeObject(notification.toString());
            outputStream.flush();
            String nType = notification.getemergencyType();
            db.server_entry(notification.getclientID(), this.dep_username, nType);
            MainServlet servletInstance = MainServlet.getInstance ();
            System.out.println (servletInstance.getTestString());
            servletInstance.notifyNewRow(new NotificationEntry (notification.getclientID(), this.dep_username, notification.getemergencyType()));
        }
        catch(SocketException se){
            System.out.println("Uaaannnn :'(");
        }
        catch(UnknownHostException se){
            System.out.println("Uaaannnn :'(");
        }
        catch(IOException se){
                System.out.println("Uaaannnn :'(");
        }

    }
}
