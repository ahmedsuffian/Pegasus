//package foursCartel.DatabaseSingleton;
package MainServer;

import java.util.Scanner;
import java.sql.*;
import java.text.*;
import java.util.ArrayList;

public class DatabaseSingleton {

    private static final DatabaseSingleton db_Singleton = new DatabaseSingleton();
    //private Connection con = null;
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/emergency";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "root";

    private DatabaseSingleton() {
    }

    public static DatabaseSingleton getInstance() {
        return db_Singleton;
    }

    public ArrayList<NotificationEntry> complete_report() {
        Connection conn = null;
        Statement stmt = null;
        ArrayList<NotificationEntry> records = new ArrayList<NotificationEntry>();
        records = new ArrayList<>();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT s.req_id, c.client_name, s.Department_id, s.Emergency_type, s.time, s.status  FROM server s JOIN clients c USING (client_id)";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next() == false) {
                System.out.println("No Record Found");
            }
            int r_id = rs.getInt("req_id");
            String c_name = rs.getString("client_name");
            int dep_id = rs.getInt("Department_id");
            String e_type = rs.getString("Emergency_type");
            String status = rs.getString("status");
            String time = rs.getString("time");

            records.add(new NotificationEntry(r_id, c_name, dep_id, e_type, status, time));
            while (rs.next()) {
                //Retrieve by column name
                r_id = rs.getInt("req_id");

                c_name = rs.getString("client_name");
                dep_id = rs.getInt("Department_id");
                e_type = rs.getString("Emergency_type");
                status = rs.getString("status");
                time = rs.getString("time");
                records.add(new NotificationEntry(r_id, c_name, dep_id, e_type, status, time));

            }
            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException se) {
            //Handle errors for JDBC
            //System.out.println("SQL Exception");
            //  			se.printStackTrace();
        } catch (ClassNotFoundException e) {
            //Handle errors for Class.forName
            System.out.println("Class .forName Exception");
            e.printStackTrace();
        } finally {
            //finally block used to close resources

            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try

            return records;
        }//end try
    }

    public String getDepartmentName(int depID) {

        String deptName = "";

        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");

            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            //STEP 4: Execute a query
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT Department_name FROM emergency_departments WHERE Department_id = "+ depID;
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next() == false) {
                return deptName;
            }
            deptName = rs.getString("Department_name");
            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException se) {
            System.out.println("SQL Exception");
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            System.out.println("Class .forName Exception");
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
            
            return deptName;
        }//end try
    }

    public void server_entry(String c_id, String dep_username, String type) {
        Connection conn = null;
        Statement stmt = null;
        String status = "done";
        System.out.println("client_username " + c_id);
        System.out.println("department_username " + dep_username);
        java.util.Date dNow = new java.util.Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss");
        String time = ft.format(dNow);
        //ArrayList <EmergencyDepartments> departments = new ArrayList <EmergencyDepartments> ();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;

            sql = "SELECT Client_id FROM clients WHERE client_username = '" + c_id + "'";
            int client_id;
            try (ResultSet rs = stmt.executeQuery(sql)) {
                if (rs.next() == false) {
                    System.out.println("No Record Found");
                }
                System.out.println("Executing: " + sql);
                //ResultSet rs = stmt.executeQuery(sql);
                client_id = rs.getInt("Client_id");
                //System.out.println("Client_id " + client_id);
            }

            sql = "SELECT Department_id FROM emergency_departments WHERE Department_username = '" + dep_username + "'";

            ResultSet rs1 = stmt.executeQuery(sql);
            if (rs1.next() == false) {
                System.out.println("No Record Found");
            }
            int d_id = rs1.getInt("Department_id");
            rs1.close();
            //System.out.println("dep_id "+d_id);
            sql = "INSERT INTO server (Client_id, Department_id, Emergency_type, status,time) VALUES (" + client_id + ", " + d_id + " ,'" + type + "', '" + status + "', '" + time + "')";
            stmt.executeUpdate(sql);
            //rs2.close();
            stmt.close();

            //rs1.close();
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            System.out.println("SQL Exception");
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            System.out.println("Class .forName Exception");
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
    }

    public boolean Client_Authentication(String username, String password) {
        Connection conn = null;
        Statement stmt = null;

        String pswrd = "";
        //ArrayList <EmergencyDepartments> departments = new ArrayList <EmergencyDepartments> ();
        try {
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT client_password FROM clients WHERE client_username = '" + username + "')";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next() == false) {
                System.out.println("No Record Found");
            }
            //	while(rs.next()){
            //Retrieve by column name

            pswrd = rs.getString("client_password");

            //}
            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException se) {
            //Handle errors for JDBC
            System.out.println("SQL Exception");
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            System.out.println("Class .forName Exception");
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        if (pswrd.equals(password)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean Department_Authentication(String username, String password) {
        Connection conn = null;
        Statement stmt = null;
        String pswrd = "";
        //ArrayList <EmergencyDepartments> departments = new ArrayList <EmergencyDepartments> ();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT Department_password FROM emergency_departments WHERE Department_username = '" + username + "'";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
 				//Retrieve by column name

                //String u_name = rs.getString("department_username");
                pswrd = rs.getString("Department_password");
                System.out.println("pwd: " + pswrd);

            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException se) {
            //Handle errors for JDBC
            System.out.println("SQL Exception");
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            System.out.println("Class .forName Exception");
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try

        if (pswrd.equals(password)) {
            return true;
        } else {
            return false;
        }
    }

    public void Departement_Register() {
        Connection conn = null;
        Statement stmt = null;
        String d_name;
        String d_address;
        String d_mail;
        String d_phone;
        String d_type;
        Scanner scan = new Scanner(System.in);
        System.out.println("Department Name : ");
        d_name = scan.nextLine();
        System.out.println("Department Address : ");
        d_address = scan.nextLine();
        System.out.println("Department Mail : ");
        d_mail = scan.nextLine();
        System.out.println("Department Phone : ");
        d_phone = scan.nextLine();
        System.out.println("Department Type : ");
        d_type = scan.nextLine();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "INSERT INTO emergency_departments (Department_name, Department_Address, Department_phone, Department_mail,Department_type) VALUES ('" + d_name + "', '" + d_address + "' ,'" + d_phone + "', '" + d_mail + "', '" + d_type + "')";
            stmt.executeUpdate(sql);

            //rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            System.out.println("SQL Exception");
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            System.out.println("Class .forName Exception");
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
    }

    public void Client_Register() {
        Connection conn = null;
        Statement stmt = null;
        String c_address;
        String c_mail;
        String c_phone;
        Scanner scan = new Scanner(System.in);
        System.out.println("Clientt Name : ");
        String c_name = scan.nextLine();
        System.out.println("Client Address : ");
        c_address = scan.nextLine();
        System.out.println("Client Mail : ");
        c_mail = scan.nextLine();
        System.out.println("Client Phone : ");
        c_phone = scan.nextLine();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "INSERT INTO clients (client_name, Client_Address, Client_phone, Client_mail) VALUES ('" + c_name + "', '" + c_address + "' ,'" + c_phone + "', '" + c_mail + "')";
            stmt.executeUpdate(sql);

            //				rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException se) {
            //Handle errors for JDBC
            System.out.println("SQL Exception");
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            System.out.println("Class .forName Exception");
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
    }

    public ArrayList Client_Report(String name) {
        Connection conn = null;
        Statement stmt = null;
        //ArrayList <EmergencyDepartments> departments = new ArrayList <EmergencyDepartments> ();
        ArrayList<NotificationEntry> client_records = new ArrayList<NotificationEntry>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT s.req_id, c.client_name, s.Department_id, s.Emergency_type, s.time, s.status  FROM server s JOIN clients c USING (client_id) WHERE c.client_id = (SELECT client_id FROM clients WHERE client_name = '" + name + "')";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next() == false) {
                System.out.println("No Record Found");
            }
            int r_id = rs.getInt("req_id");
            String c_name = rs.getString("client_name");
            int dep_id = rs.getInt("Department_id");
            String e_type = rs.getString("Emergency_type");
            String status = rs.getString("status");
            String time = rs.getString("time");
            client_records.add(new NotificationEntry(r_id, c_name, dep_id, e_type, status, time));

            while (rs.next()) {
                //Retrieve by column name
                r_id = rs.getInt("req_id");
                c_name = rs.getString("client_name");
                dep_id = rs.getInt("Department_id");
                e_type = rs.getString("Emergency_type");
                status = rs.getString("status");
                time = rs.getString("time");
                client_records.add(new NotificationEntry(r_id, c_name, dep_id, e_type, status, time));
                //stmt = con.createStatement();
                //Display values
   				/*System.out.print("Req_ID: " + r_id+"\t");
                 System.out.print(" Client_name: " + c_name+"\t");
                 System.out.print(" Emergency_type: " + e_type+"\t");
                 System.out.print(" Status: " + status+"\t");
                 System.out.println(" Time: " + time);*/
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            System.out.println("SQL Exception");
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            System.out.println("Class .forName Exception");
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        return client_records;
    }

    public ArrayList Department_Report(String dep_name) {
        Connection conn = null;
        Statement stmt = null;
        ArrayList<NotificationEntry> department_records = new ArrayList<NotificationEntry>();
        try {
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT s.req_id, s.client_id, s.Department_id, s.Emergency_type, s.time, s.status  FROM server s JOIN emergency_departments d USING (Department_id) WHERE d.Department_id = (SELECT Department_id FROM emergency_departments WHERE Department_name = '" + dep_name + "')";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next() == false) {
                System.out.println("No Record Found");
            }
            int r_id = rs.getInt("req_id");
            String c_id = rs.getString("client_id");
            int dep_id = rs.getInt("Department_id");
            String e_type = rs.getString("Emergency_type");
            String status = rs.getString("status");
            String time = rs.getString("time");
            department_records.add(new NotificationEntry(r_id, c_id, dep_id, e_type, status, time));

            while (rs.next()) {
                //Retrieve by column name
                r_id = rs.getInt("req_id");
                c_id = rs.getString("client_id");
                dep_id = rs.getInt("Department_id");
                e_type = rs.getString("Emergency_type");
                status = rs.getString("status");
                time = rs.getString("time");
                department_records.add(new NotificationEntry(r_id, c_id, dep_id, e_type, status, time));
                //stmt = con.createStatement();
                //Display values
   				/*System.out.print("Req_ID: " + r_id+"\t");
                 System.out.print(" Client_id: " + c_id+"\t");
                 System.out.print(" Emergency_type: " + e_type+"\t");
                 System.out.print(" Status: " + status+"\t");
                 System.out.println(" Time: " + time);*/
            }
            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException se) {
            //Handle errors for JDBC
            System.out.println("SQL Exception");
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            System.out.println("Class .forName Exception");
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        return department_records;
    }

    public ArrayList Date_Report(String date) {
        Connection conn = null;
        Statement stmt = null;
        ArrayList<NotificationEntry> time_records = new ArrayList<NotificationEntry>();
        try {
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT s.req_id, c.client_name, s.Department_id, s.Emergency_type, s.time, s.status FROM server s JOIN clients c USING (client_id) WHERE time LIKE '%" + date + "%'";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next() == false) {
                System.out.println("No Record Found");
            }
            int r_id = rs.getInt("req_id");
            String c_name = rs.getString("client_name");
            int dep_id = rs.getInt("Department_id");
            String e_type = rs.getString("Emergency_type");
            String status = rs.getString("status");
            String time = rs.getString("time");
            time_records.add(new NotificationEntry(r_id, c_name, dep_id, e_type, status, time));

            while (rs.next()) {
                //Retrieve by column name
                r_id = rs.getInt("req_id");
                c_name = rs.getString("client_name");
                dep_id = rs.getInt("Department_id");
                e_type = rs.getString("Emergency_type");
                status = rs.getString("status");
                time = rs.getString("time");
                time_records.add(new NotificationEntry(r_id, c_name, dep_id, e_type, status, time));

                //stmt = con.createStatement();
                //Display values
   				/*System.out.print("Req_ID: " + r_id+"\t");
                 System.out.print(" Client_name: " + c_name+"\t");
                 System.out.print(" Emergency_type: " + e_type+"\t");
                 System.out.print(" Status: " + status+"\t");
                 }	System.out.println(" Time: " + time);*/
            }
            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException se) {
            //Handle errors for JDBC
            System.out.println("SQL Exception");
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            System.out.println("Class .forName Exception");
            //e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                //	se.printStackTrace();
            }//end finally try
        }//end try
        return time_records;
    }

}
