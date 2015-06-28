//package foursCartel.DatabaseSingleton;
package MainServer;

public class NotificationEntry {
    private int req_id;
    private String c_name;
    private String e_type;
    private String status;
    private String time;
    private int dept_id;
    private String dept_name;

    public void setDept_id(int dept_id) {
        this.dept_id = dept_id;
    }

    public int getDept_id() {
        return dept_id;
    }
    
    public NotificationEntry () {}
    
    public NotificationEntry(int r_id, String name,int dID ,  String type, String sts, String tym){
            this.req_id = r_id;
            this.c_name = name;
            this.dept_id = dID;
            this.e_type = type;
            this.status = sts;
            this.time = tym;
            
            DatabaseSingleton db = DatabaseSingleton.getInstance();
            
            this.dept_name = db.getDepartmentName(dID);
    }

        
    public NotificationEntry(String name,String dName ,  String type){
        //    this.req_id = r_id;
            this.c_name = name;
            this.dept_name = dName;
            this.e_type = type;
           // this.status = sts;
            //this.time = tym;
    }
    
    public void printRecord () {
        System.out.println (this.req_id + 
            this.c_name + 
            this.e_type + 
            this.status + 
            this.time );
    }
    
    public String toHTMLrow () {
        
        String row = "<tr>";
        
        row += "<td>" + this.c_name+ "</td>";
        row += "<td>" + this.e_type+ "</td>";
        row += "<td>" + this.dept_name + "</td>"; 
        
        row += "</tr>";
        
        return row;
    }
    

    public String getReportHTMLrow () {
        
        String row = "<tr>";
        
        row += "<td>" + this.req_id+ "</td>";        
        row += "<td>" + this.c_name+ "</td>";
        row += "<td>" + this.e_type+ "</td>";
        row += "<td>" + this.status + "</td>"; 
        row += "<td>" + this.dept_name + "</td>"; 
        row += "<td>" + this.time + "</td>"; 
        
        row += "</tr>";
        
        return row;
    }

    
    public void setReq_id(int req_id) {
        this.req_id = req_id;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public void setE_type(String e_type) {
        this.e_type = e_type;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getReq_id() {
        return req_id;
    }

    public String getC_name() {
        return c_name;
    }

    public String getE_type() {
        return e_type;
    }

    public String getStatus() {
        return status;
    }

    public String getTime() {
        return time;
    }
    
    
}

