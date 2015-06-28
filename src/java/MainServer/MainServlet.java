/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainServer;

import static MainServer.MainServer.subject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author AhmedSuffian
 */
public class MainServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    boolean hasUpdate  = true;
    boolean firstTime = true;
    static MainServlet servletInstance;
    static String testString = "";
    
    ArrayList <NotificationEntry> updates = null;

    public static String getTestString() {
        return testString;
    }
    
    public MainServlet () {
        initializeStuff ();
    }
    
    public void notifyNewRow (NotificationEntry ne) {
        updates.add(ne);
        hasUpdate = true;
    }
    
    private void initializeStuff () {
    
        System.out.println("Server is ready!");
        servletInstance = this;
        updates= new ArrayList <NotificationEntry> () {};
        //String name,int dID ,  String type
        notifyNewRow (new NotificationEntry ("seecs", "some department", "police"));
        Thread notificationThread = new Thread (new NotificationListener (subject));
        Thread connectionThread = new Thread (new ConnectionListener (subject));

        notificationThread.setDaemon (true);
        connectionThread.setDaemon (true);

        notificationThread.start ();
        connectionThread.start();

      
    }
    
    public static MainServlet getInstance () {
        return servletInstance;
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet MainServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MainServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // processRequest(request, response);
        
        
        
        String action = request.getParameter("action");
        if (action != null) {
            if (action.equals ("checkUpdates")) {

                response.setContentType("text/html;charset=UTF-8");
                try (PrintWriter out = response.getWriter()) {
                    out.print(hasUpdate);
                }
            }
            else if (action.equals("fetchUpdates")){

                try (PrintWriter out = response.getWriter()) {
                    if (firstTime) {
                        out.print("<table id=\"recordsTable\" border=\"1\">");
                        out.print ( "<tr> <th> Client Login ID </th> <th> Emergency Type </th> <th> Department ID </th>  </tr>");
                        testString = "is it? ";
                    }

                    while (updates.size() != 0 ) {
                        out.println( updates.get(0).toHTMLrow());
                        updates.remove(updates.get(0));
                    }

                    if (firstTime) 
                        out.println ("</table>");

                updates.removeAll(new ArrayList <NotificationEntry>());

                hasUpdate = false;
                firstTime= false;
                out.flush();

                }
            }
            else if (action.equals ("getCompleteReport")) {

                DatabaseSingleton db = DatabaseSingleton.getInstance();
                ArrayList <NotificationEntry> report = db.complete_report();

                makeTable (report, response);
                firstTime= true;
            }
            else if (action.equals("getClientReport")) {

                DatabaseSingleton db = DatabaseSingleton.getInstance();
                ArrayList <NotificationEntry> report = db.Client_Report(request.getParameter("filter"));

                makeTable (report, response);
                firstTime= true;
            }
            else if (action.equals("getDepartmentReport")) {

                DatabaseSingleton db = DatabaseSingleton.getInstance();
                ArrayList <NotificationEntry> report = db.Department_Report(request.getParameter("filter"));

                makeTable (report, response);
                firstTime= true;
            }
        }
    }

    private void makeTable (ArrayList <NotificationEntry> report, HttpServletResponse response) throws IOException {

        try (PrintWriter out = response.getWriter()) {
        
            out.print("<table id=\"recordsTable\" border=\"1\">");
            out.print ( "<tr> <th> Request ID</th> <th> Client Login ID</th> <th> Emergency Type</th>" +
                        "<th> Status</th> <th> Department Name</th> <th> Time Stamp</th>"+"</tr>");

            for (int i=  0; i< report.size(); i ++) {
            
                out.println(report.get(i).getReportHTMLrow());
            }
            out.println ("</table>");
            
        }
    }
    
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        
        //request.getAttribute("action");
        if (request.getParameter("loginID").equals("ahmed") && request.getParameter("loginPwd").equals("12345")){
            response.setContentType("text/html");  
            PrintWriter pw=response.getWriter();  
  
            //response.setHeader("Location", request.getLocalAddr() + ":"+   request.getLocalPort()+ "/MainServer_Servlet/services.html");
//            response.setHeader("Location",request.getRemoteAddr()+ ":"+ request.getLocalPort()+ request.getContextPath() +  "/services.html");
            
            Cookie loginCookie = new Cookie ("user",request.getParameter("loginID") );
            loginCookie.setMaxAge(1*30);
            response.addCookie(loginCookie);
            response.sendRedirect(request.getContextPath() +"/services.jsp");
            pw.close();  
        
        }else{
            //response.sendRedirect ("services.html");
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
            PrintWriter out= response.getWriter();
            out.println("<font color=red> Invalid credentionals</font>");
            rd.include(request, response);
// response.setHeader("Location", request.getLocalAddr() + ":"+   request.getLocalPort()+ "/MainServer_Servlet/index.html");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
