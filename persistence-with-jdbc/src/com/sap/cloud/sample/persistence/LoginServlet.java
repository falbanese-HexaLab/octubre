package com.sap.cloud.sample.persistence;

import java.io.*;  
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;  
import javax.servlet.http.*;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.util.Pair;  


public class LoginServlet extends HttpServlet {  
	
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceWithJDBCServlet.class);
	
	private PersonDAO personDAO;
	private PALExample aPALExample;
    
    /** {@inheritDoc} */
    @Override
    public void init() throws ServletException {
        try {
            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/DefaultDB");
            
            personDAO = new PersonDAO(ds);
            aPALExample = new PALExample(ds);
        } catch (SQLException e) {
            throw new ServletException(e);
        } catch (NamingException e) {
            throw new ServletException(e);
        }
    }
    
    /** {@inheritDoc} */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().println("<p>Usuario logueado - GET!</p>");
    }

    /** {@inheritDoc} */
    @Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)  
	            throws ServletException, IOException {
    	response.getWriter().println("<p>Intento agregar elems! </p>");
    	try {
    		
    		//response.getWriter().println("Se intenta crear Tabla TEST  1");
    		//personDAO.createTableTest();
    		//response.getWriter().println("Se intenta crear Tabla TEST 2");
    		
    		ArrayList<PairIntDouble> list = aPALExample.runPAL();
			for (PairIntDouble elem : list) {
				response.getWriter().println("hola");
				//response.getWriter().println(Integer.toString(elem.first()) + ' ' + 
				//		Double.toString(elem.second()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
		}
    	
		try {
	        String password = request.getParameter("LoginPassword");
	        String username = request.getParameter("LoginUsername");

	        // Look up the person
	        List<Person> persons = personDAO.selectAllPersons();
	        
	        //response.getWriter().println(username);
	        
	        boolean registered = false;
	        for (Person p : persons)  {
	        	//response.getWriter().println(p.getUserName());
	        	if(p.getUserName().equals(username)) {
	        		registered = true;
	        	}
	        }
	        if(registered) {
	        	response.getWriter().println("<p>OK : usuario logueado !</p>");
	        	// Show User's persisted data, and let upload more data
	        } else {
	        	response.getWriter().println("<p>Error : el usuario no estaba registrado !</p>");
	        }
        } catch (Exception e) {
            response.getWriter().println("Persistence operation failed with reason: " + e.getMessage());
            LOGGER.error("Persistence operation failed", e);
        }
	}  
  
}  