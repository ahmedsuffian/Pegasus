package MainServer;

import java.net.Socket;


public class RDept extends EmergencyDept{

    private EmergencyNotification r_notification;
    DatabaseSingleton db =  DatabaseSingleton.getInstance();

    public RDept (Subject sub, Socket skt,String dep_id) {
            super (sub, skt,dep_id);
    }

    @Override
    public void update(Subject observable, String Type) {

        if(Type.equals ("rescue")){
            EmergencyNotification object = observable.getter();

            this.forwardNotification(object);
        }
    }
}