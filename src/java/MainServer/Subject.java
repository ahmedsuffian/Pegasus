package MainServer; 

import java.util.ArrayList;
import java.util.List;

public class Subject {
	
	private List<EmergencyDept> observers = new ArrayList<EmergencyDept>();
	private EmergencyNotification ENotif;

	//private int emeregencyDept;

	public EmergencyNotification getState() {
		return ENotif;
	}

	public void setState(EmergencyNotification ENotif) {

		this.ENotif = ENotif;
		notifyAllObservers();
	//	return this.emeregencyDept;
	}

	public void attach(EmergencyDept observer){
		observers.add(observer);
	}

	public void notifyAllObservers(){
		for (EmergencyDept observer : observers) {
			observer.update(this, ENotif.getemergencyType());
		}
	} 	
	public EmergencyNotification getter(){
	//	this.emeregencyDept = dept.getID();
		return ENotif;
	}
}
