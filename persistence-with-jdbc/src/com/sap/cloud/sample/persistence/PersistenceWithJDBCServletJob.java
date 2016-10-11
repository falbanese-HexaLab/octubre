package com.sap.cloud.sample.persistence;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.security.core.server.csi.IXSSEncoder;
import com.sap.security.core.server.csi.XSSEncoder;

public class PersistenceWithJDBCServletJob extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceWithJDBCServlet.class);

    private JobDAO jobDAO;
    
    /** {@inheritDoc} */
    @Override
    public void init() throws ServletException {
        try {
            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/DefaultDB");
            jobDAO = new JobDAO(ds);
        } catch (SQLException e) {
            throw new ServletException(e);
        } catch (NamingException e) {
            throw new ServletException(e);
        }
    }
    
    /** {@inheritDoc} */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().println("<p>Persistence with JDBC Sample Job!</p>");
        try {
        	appendJobTable(response);
            appendAddJobForm(response);
        } catch (Exception e) {
            response.getWriter().println("Persistence operation failed with reason: " + e.getMessage());
            LOGGER.error("Persistence operation failed", e);
        }
    }

    /** {@inheritDoc} */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        try {
            doAddJob(request);
            doGet(request, response);
        } catch (Exception e) {
            response.getWriter().println("Persistence operation failed with reason: " + e.getMessage());
            LOGGER.error("Persistence operation failed", e);
        }
    }
    
    private void appendAddJobForm(HttpServletResponse response) throws IOException {
    	response.getWriter().println(
    			"<p><form action=\"\" method=\"post\">"
    					+ "&nbsp;Project:<select input type = \"text\" name=\"Project\">  "
    					+ "<option>MACRO</option>"
    					+ "<option>CREDICLICK</option>"  
    					+ "<option>CAPACITACION</option>"
    					+ "</select>"
    					
                        + "&nbsp;Hours:<input type=\"text\" name=\"Hours\">"
                        + "&nbsp;Date:<input type=\"text\" name=\"Date\">"
                        + "&nbsp;<input type=\"submit\" value=\"Add Job\">" 
                        + "</form></p>");
    }
    
    private void doAddJob(HttpServletRequest request) throws ServletException, IOException, SQLException {
    	//String projectName = request.getParameter("Project");
    	Float hours = Float.parseFloat(request.getParameter("Hours"));
    	String date = request.getParameter("Date");
    	
    	//if(projectName != null && hours != null && date != null) {
    		Job j = new Job();
    		j.setHours(hours);
    		j.setDate(date);
    		jobDAO.addJob(j);
    	//}
    }
    
    private void appendJobTable(HttpServletResponse response) throws SQLException, IOException {
        // Append table that lists all jobs
        
    	List<Job> resultList = jobDAO.selectAllJobs();
        response.getWriter().println(
                "<p><table border=\"1\"><tr><th colspan=\"4\">" + (resultList.isEmpty() ? "" : resultList.size() + " ")
                        + "Entries in the Database</th></tr>");
        if (resultList.isEmpty()) {
            response.getWriter().println("<tr><td colspan=\"4\">Database is empty</td></tr>");
        } else {
            response.getWriter().println(""
            		+ "<tr><th>Person</th>"
            		+ "<th>Project</th>"
            		+ "<th>Hours</th>"
            		+ "<th>Date</th></tr>");
        }
        IXSSEncoder xssEncoder = XSSEncoder.getInstance();
        for (Job j : resultList) {
            response.getWriter().println(
            		"<tr><td>"
            				+ xssEncoder.encodeHTML(j.getWorker().getFirstName()) 
            				+ "</td><td>" + xssEncoder.encodeHTML(j.getProject().getName()) 
            				+ "</td><td>" + xssEncoder.encodeHTML(Float.toString(j.getHours())) 
                    		+ "</td><td>" + xssEncoder.encodeHTML(j.getDate())
                    + "</td></tr>"
            );
        }
        response.getWriter().println("</table></p>");
        for (Job j : resultList) {
        	response.getWriter().println(Float.toString(j.getHours()));
        }
    }
}