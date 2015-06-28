package MainServer;

import java.net.Socket;

public class FBDept extends EmergencyDept {

    private EmergencyNotification f_notification;
    DatabaseSingleton db = DatabaseSingleton.getInstance();

    public FBDept(Subject sub, Socket skt, String dep_id) {
        super(sub, skt, dep_id);
    }

    @Override
    public void update(Subject observable, String Type) {

        if (Type.equals("fire")) {
            EmergencyNotification object = observable.getter();

            this.forwardNotification(object);
        }
    }
}
