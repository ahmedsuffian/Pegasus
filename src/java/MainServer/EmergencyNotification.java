	//package foursCartel.EmergencyNotification;
	package MainServer;

	import java.io.Serializable;

	public class EmergencyNotification implements Serializable  {
		private String clientID;
		private String location;
		private String emergencyType;


		public EmergencyNotification () { }


		public EmergencyNotification (String data) {

			String [] arr = data.split ("\n");

			this.clientID =  arr [0]; //;
                        System.out.println ("Client ID fetched: " + this.clientID + " " + arr [0]);
                        
			this.location = arr [1];
			this.emergencyType = arr [2];
		}


		public EmergencyNotification (String cI, String c, String add) {
			this.clientID = cI;
			this.location = c;
			this.emergencyType = add;
		}

		public String getclientID () {
			return clientID;
		}
		public String getlocation () {
			return location;
		}
		public String getemergencyType () {
			return emergencyType;
		}
	

		public String toString () {
			return ""+clientID+"\n"+location +  "\n"+ emergencyType + "\n";
		}
		public void setclientID (String id) {
			clientID = id;
		}
		public void setlocation (String c) {
			location = c;
		}
		public void setemergencyType (String add) {
			emergencyType = add;
		}

	}