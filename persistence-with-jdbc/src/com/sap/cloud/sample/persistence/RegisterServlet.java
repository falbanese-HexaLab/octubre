package com.sap.cloud.sample.persistence;

import java.io.*;  
import java.sql.*;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;  
import javax.servlet.http.*;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;  


public class RegisterServlet extends HttpServlet {  
	
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceWithJDBCServlet.class);
	private PersonDAO personDAO;
    //private PALExample palExample;
    
    /** {@inheritDoc} */
    @Override
    public void init() throws ServletException {
        try {
            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/DefaultDB");
            personDAO = new PersonDAO(ds);
            //palExample = new PALExample(ds);
        } catch (SQLException e) {
            throw new ServletException(e);
        } catch (NamingException e) {
            throw new ServletException(e);
        }
    }
    
    /** {@inheritDoc} */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().println("<p>Usuario registrado - GET!</p>");
        
    }

    /** {@inheritDoc} */
    @Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)  
	            throws ServletException, IOException { 
    	response.getWriter().println("<p>Usuario registrado - POST !</p>");
    	
		try {
			String firstName = request.getParameter("RegisterFirstName");
	        String lastName = request.getParameter("RegisterLastName");
	        String password = request.getParameter("RegisterPassword");
	        String username = request.getParameter("RegisterUsername");
	        int age = Integer.parseInt(request.getParameter("RegisterAge"));

	        // Add person if name is not null/empty
	        if (firstName != null && lastName != null && !firstName.trim().isEmpty() && !lastName.trim().isEmpty()) {
	            Person person = new Person();
	            person.setFirstName(firstName.trim());
	            person.setLastName(lastName.trim());
	            person.setPassword(password.trim());
	            person.setAge(age);
	            person.setUserName(username);
	            personDAO.addPerson(person);
	        }
            
        } catch (Exception e) {
            response.getWriter().println("Persistence operation failed with reason: " + e.getMessage());
            LOGGER.error("Persistence operation failed", e);
        }  
	}  
  
}  