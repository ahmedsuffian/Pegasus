package MainServer;

import java.net.Socket;

public class PDept extends EmergencyDept {

    public PDept(Subject sub, Socket skt, String dep_id) {
        super(sub, skt, dep_id);
    }

    @Override
    public void update(Subject observable, String Type) {

        if (Type.equals("police")) {
            EmergencyNotification object = observable.getter();

            this.forwardNotification(object);
        }
    }
}
