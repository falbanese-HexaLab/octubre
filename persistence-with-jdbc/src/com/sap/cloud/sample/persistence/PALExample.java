package com.sap.cloud.sample.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import javafx.util.Pair;

public class PALExample {
	
	private DataSource dataSource;

    PALExample(DataSource newDataSource) throws SQLException {
    	setDataSource(newDataSource);
    }
    
    /**
     * Get data source which is used for the database operations.
     */
    public DataSource getDataSource() {
        return dataSource;
    }

    /**
     * Set data source to be used for the database operations.
     */
    public void setDataSource(DataSource newDataSource) throws SQLException {
        this.dataSource = newDataSource;
    }
    
    
    public void oneSQLQuery(Connection connection, String query) throws SQLException {
       //Statement stmt = null;
       PreparedStatement stmt = connection.prepareStatement(query);
 	   try {
 	        //stmt = connection.createStatement();
 	        //stmt.execute(query);
 		   stmt.executeUpdate();
 	    } catch (SQLException e ) {
 	    	
 	    } finally {
 	        if (stmt != null) { stmt.close(); }
 	    }
    }
    
    public void oneSQLInsert(Connection connection, String query) throws SQLException {
    	//Statement stmt = null;
    	PreparedStatement stmt = connection.prepareStatement(query);
    	try {
    		//stmt = connection.createStatement();
    		//stmt.execute();
    		stmt.executeUpdate(query);
    	} catch (SQLException e) {
    		
    	} finally {
    		if (stmt != null) {stmt.close(); }
    	}
    }
    
    public ArrayList<PairIntDouble> GetOneSQLQuery(Connection connection, String query) throws SQLException {
      ArrayList<PairIntDouble> list = new ArrayList<PairIntDouble>();
      
      //PreparedStatement pstmt = connection.prepareStatement("SELECT USERNAME FROM T_PERSONS;");
      //pstmt.executeQuery();
      //ResultSet rs = pstmt.getResultSet();
      // Here, we should read rs results and fill in the list array.
      
      Statement pstmt = connection.createStatement();
      
      //pstmt.executeQuery("SELECT USERNAME FROM T_PERSONS"); // --> SI ANDA
      pstmt.executeQuery("SELECT * FROM PAL_GR_PDATA_TBL"); // --> NO ANDA (??). DEBERIA
      
      //ResultSet rs = stmt.getResultSet();
      //if(rs != null) {
    	 // list.add(new PairIntDouble(1,2.0));
      //}
      
      //
      
      list.add(new PairIntDouble(1,2.0));
      if (pstmt != null) { pstmt.close(); }
      
  	  return list;
    }
    
    public void oneSQLCall(Connection connection, String query) throws SQLException {
    	Statement stmt = null;
    	try {
    		stmt = connection.prepareCall(query);
    		stmt.execute(query);
    	} catch (SQLException e) {
    		
    	} finally {
    		if (stmt != null) { stmt.close(); }
    	}
    }
    
	
	public ArrayList<PairIntDouble> runPAL() throws SQLException {
		
		Connection connection = dataSource.getConnection();
		connection.setAutoCommit(true);
		
		String [] queries = new String[100];
		
		queries[0] = "SET SCHEMA DM_PAL;";
		queries[1] = "DROP TYPE PAL_GR_DATA_T;";
		queries[2] = "CREATE TYPE PAL_GR_DATA_T AS TABLE( 'ID' INT,'Y' DOUBLE,'X1' DOUBLE);";
		queries[3] = "DROP TYPE PAL_GR_RESULT_T;";
		queries[4] = "CREATE TYPE PAL_GR_RESULT_T AS TABLE('ID' INT,'Ai' DOUBLE);";
		queries[5] = "DROP TYPE PAL_GR_FITTED_T;";
		queries[6] = "CREATE TYPE PAL_GR_FITTED_T AS TABLE('ID' INT,'Fitted' DOUBLE);";
		queries[7] = "DROP TYPE PAL_GR_SIGNIFICANCE_T;";
		queries[8] = "CREATE TYPE PAL_GR_SIGNIFICANCE_T AS TABLE('NAME' varchar(50),'VALUE' DOUBLE);";
		queries[9] = "DROP TYPE PAL_CONTROL_T;";
		queries[10] = "CREATE TYPE PAL_CONTROL_T AS TABLE('NAME' VARCHAR(100), 'INTARGS' INT,'DOUBLEARGS' DOUBLE,'STRINGARGS' VARCHAR(100));";
		queries[11] = "DROP TYPE PAL_GR_PMMLMODEL_T;";
		queries[12] = "CREATE TYPE PAL_GR_PMMLMODEL_T AS TABLE('ID' INT,'Model' varchar(5000));";
		queries[13] = "DROP table PAL_GR_PDATA_TBL;";
		queries[14] = "CREATE column table PAL_GR_PDATA_TBL('POSITION' INT, 'SCHEMA_NAME'NVARCHAR(256), 'TYPE_NAME' NVARCHAR(256), 'PARAMETER_TYPE' VARCHAR(7));";
		
		queries[15] = "INSERT INTO PAL_GR_PDATA_TBL values (1,'DM_PAL','PAL_GR_DATA_T','IN');";
	    
	    
		ArrayList<PairIntDouble> list = new ArrayList<PairIntDouble>();
		
		oneSQLQuery(connection, queries[13]);
		oneSQLQuery(connection, queries[14]);
		
		list.add(new PairIntDouble(1,2.0));
		
	    if(connection != null) {connection.close();}
	        
	   return list;
	}

}