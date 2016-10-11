package servlets;

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
    
	public PALExample(DataSource ds) throws SQLException {
    	setDataSource(ds);
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
    
	public void runPAL() throws SQLException {
		
		Connection connection = dataSource.getConnection();
		
		String [] queries = new String[100];
		
		// MUY IMPORTANTE: PASAR EL FORMATO DE LAS CONSULTAS BIEN!!! ( CON LOS ' ' DONDE DEBE SER )
		// MUY IMPORTANTE: NO PONER ';' ADENTRO DE LAS QUERIES!
		
		queries[0] = "SET SCHEMA NEO_5QQN7TQREM10T642K0T903BJH";
		queries[1] = "DROP TYPE PAL_GR_DATA_T";
		queries[2] = "CREATE TYPE PAL_GR_DATA_T AS TABLE(  ID  INT, Y  DOUBLE, X1  DOUBLE)";
		queries[3] = "DROP TYPE PAL_GR_RESULT_T";
		queries[4] = "CREATE TYPE PAL_GR_RESULT_T AS TABLE( ID  INT, Ai  DOUBLE)";
		queries[5] = "DROP TYPE PAL_GR_FITTED_T";
		queries[6] = "CREATE TYPE PAL_GR_FITTED_T AS TABLE( ID  INT, Fitted  DOUBLE)";
		queries[7] = "DROP TYPE PAL_GR_SIGNIFICANCE_T";
		queries[8] = "CREATE TYPE PAL_GR_SIGNIFICANCE_T AS TABLE( NAME  varchar(50), VALUE  DOUBLE)";
		queries[9] = "DROP TYPE PAL_CONTROL_T";
		queries[10] = "CREATE TYPE PAL_CONTROL_T AS TABLE( NAME  VARCHAR(100),  INTARGS  INT, DOUBLEARGS  DOUBLE, STRINGARGS  VARCHAR(100))";
		queries[11] = "DROP TYPE PAL_GR_PMMLMODEL_T";
		queries[12] = "CREATE TYPE PAL_GR_PMMLMODEL_T AS TABLE( ID  INT, Model  varchar(5000))";
		
		queries[13] = "DROP TABLE PAL_GR_PDATA_TBL";
		queries[14] = "CREATE column table PAL_GR_PDATA_TBL( POSITION  INT,  SCHEMA_NAME NVARCHAR(256),  TYPE_NAME  NVARCHAR(256),  PARAMETER_TYPE  VARCHAR(7))";
		queries[15] = "INSERT INTO PAL_GR_PDATA_TBL values (1, 'NEO_5QQN7TQREM10T642K0T903BJH' , 'PAL_GR_DATA_T' , 'IN' )";
		queries[16] = "INSERT INTO PAL_GR_PDATA_TBL values (2, 'NEO_5QQN7TQREM10T642K0T903BJH' , 'PAL_CONTROL_T' , 'IN' )";
		queries[17] = "INSERT INTO PAL_GR_PDATA_TBL values (3, 'NEO_5QQN7TQREM10T642K0T903BJH' , 'PAL_GR_RESULT_T' , 'OUT' )";
		queries[18] = "INSERT INTO PAL_GR_PDATA_TBL values (4, 'NEO_5QQN7TQREM10T642K0T903BJH' , 'PAL_GR_FITTED_T' , 'OUT' )";
		queries[19] = "INSERT INTO PAL_GR_PDATA_TBL values (5, 'NEO_5QQN7TQREM10T642K0T903BJH' , 'PAL_GR_SIGNIFICANCE_T' , 'OUT' )";
		queries[20] = "INSERT INTO PAL_GR_PDATA_TBL values (6, 'NEO_5QQN7TQREM10T642K0T903BJH' , 'PAL_GR_PMMLMODEL_T' , 'OUT' )";
		queries[19] = "INSERT INTO PAL_GR_PDATA_TBL values (5, 'NEO_5QQN7TQREM10T642K0T903BJH' , 'PAL_GR_SIGNIFICANCE_T' , 'OUT' )";
		queries[20] = "INSERT INTO PAL_GR_PDATA_TBL values (6, 'NEO_5QQN7TQREM10T642K0T903BJH' , 'PAL_GR_PMMLMODEL_T' , 'OUT' )";
		
		queries[21] = "CALL SYS.AFLLANG_WRAPPER_PROCEDURE_DROP( 'NEO_5QQN7TQREM10T642K0T903BJH' , 'PAL_GEOR_PROC' )";
		queries[22] = "CALL SYS.AFLLANG_WRAPPER_PROCEDURE_CREATE( 'AFLPAL' , 'GEOREGRESSION' , 'NEO_5QQN7TQREM10T642K0T903BJH' , "
				       + " 'PAL_GEOR_PROC' , PAL_GR_PDATA_TBL)";
		
		queries[23] = "DROP TABLE #PAL_CONTROL_TBL";
		queries[24] = "CREATE LOCAL TEMPORARY COLUMN TABLE #PAL_CONTROL_TBL ( NAME  VARCHAR(100), INTARGS  INT,  DOUBLEARGS  DOUBLE, STRINGARGS  VARCHAR(100))";
		queries[25] = "INSERT INTO #PAL_CONTROL_TBL VALUES ( 'THREAD_NUMBER' ,8,null,null)";
		queries[26] = "INSERT INTO #PAL_CONTROL_TBL VALUES ( 'PMML_EXPORT' ,2,null,null)";
		
		queries[27] = "DROP TABLE PAL_GR_DATA_TBL";
		queries[28] = "CREATE COLUMN TABLE PAL_GR_DATA_TBL (  ID  INT, Y  DOUBLE, X1  DOUBLE)";
		
		queries[29] = "INSERT INTO PAL_GR_DATA_TBL VALUES (0,1.1,1)";
		queries[30] = "INSERT INTO PAL_GR_DATA_TBL VALUES (1,4.2,2)";
		queries[31] = "INSERT INTO PAL_GR_DATA_TBL VALUES (2,8.9,3)";
		queries[32] = "INSERT INTO PAL_GR_DATA_TBL VALUES (3,16.3,4)";
		queries[33] = "INSERT INTO PAL_GR_DATA_TBL VALUES (4,24,5)";
		queries[34] = "INSERT INTO PAL_GR_DATA_TBL VALUES (5,36,6)";
		queries[35] = "INSERT INTO PAL_GR_DATA_TBL VALUES (6,48,7)";
		queries[36] = "INSERT INTO PAL_GR_DATA_TBL VALUES (7,64,8)";
		queries[37] = "INSERT INTO PAL_GR_DATA_TBL VALUES (8,80,9)";
		queries[38] = "INSERT INTO PAL_GR_DATA_TBL VALUES (9,101,10)";
		
		queries[39] = "DROP TABLE PAL_GR_RESULTS_TBL";
		queries[40] = "CREATE COLUMN TABLE PAL_GR_RESULTS_TBL ( ID  INT, Ai  DOUBLE)";
		queries[41] = "DROP TABLE PAL_GR_FITTED_TBL";
		queries[42] = "CREATE COLUMN TABLE PAL_GR_FITTED_TBL ( ID  INT, Fitted  DOUBLE)";
		queries[43] = "DROP TABLE PAL_GR_SIGNIFICANCE_TBL";
		queries[44] = "CREATE COLUMN TABLE PAL_GR_SIGNIFICANCE_TBL ( NAME  varchar(50), VALUE  DOUBLE)";
		queries[45] = "DROP TABLE PAL_GR_PMMLMODEL_TBL";
		queries[46] = "CREATE COLUMN TABLE PAL_GR_PMMLMODEL_TBL ( ID  INT,  PMMLMODEL  VARCHAR(5000))";
		
		//queries[47] = "CALL PAL_GEOR_PROC('PAL_GR_DATA_TBL',  '#PAL_CONTROL_TBL' ,'PAL_GR_RESULTS_TBL', 'PAL_GR_FITTED_TBL', 'PAL_GR_SIGNIFICANCE_TBL','PAL_GR_PMMLMODEL_TBL') with overview";
		
		// Hago queries
		
		oneSQLUpdate(connection, queries[0]);
		oneSQLUpdate(connection, queries[1]);
		oneSQLUpdate(connection, queries[2]);
		oneSQLUpdate(connection, queries[3]);
		oneSQLUpdate(connection, queries[4]);
		oneSQLUpdate(connection, queries[5]);
		oneSQLUpdate(connection, queries[6]);
		oneSQLUpdate(connection, queries[7]);
		oneSQLUpdate(connection, queries[8]);
		oneSQLUpdate(connection, queries[9]);
		oneSQLUpdate(connection, queries[10]);
		oneSQLUpdate(connection, queries[11]);
		oneSQLUpdate(connection, queries[12]);
		
		oneSQLUpdate(connection, queries[13]);
		oneSQLUpdate(connection, queries[14]);
		
		oneSQLUpdate(connection, queries[15]);
		oneSQLUpdate(connection, queries[16]);
		oneSQLUpdate(connection, queries[17]);
		oneSQLUpdate(connection, queries[18]);
		
		oneSQLUpdate(connection, queries[19]);
		oneSQLUpdate(connection, queries[20]);
		
		oneSQLCall(connection, queries[21]);
		oneSQLCall(connection, queries[22]);
		
		oneSQLUpdate(connection, queries[23]);
		oneSQLUpdate(connection, queries[24]);
		oneSQLUpdate(connection, queries[25]);
		oneSQLUpdate(connection, queries[26]);
		
		oneSQLUpdate(connection, queries[27]);
		oneSQLUpdate(connection, queries[28]);
		
		oneSQLUpdate(connection, queries[29]);
	    oneSQLUpdate(connection, queries[30]);
	    oneSQLUpdate(connection, queries[31]);
	    oneSQLUpdate(connection, queries[32]);
	    oneSQLUpdate(connection, queries[33]);
	    oneSQLUpdate(connection, queries[34]);
	    oneSQLUpdate(connection, queries[35]);
	    oneSQLUpdate(connection, queries[36]);
	    oneSQLUpdate(connection, queries[37]);
	    oneSQLUpdate(connection, queries[38]);
	    
	    oneSQLUpdate(connection, queries[39]);
	    oneSQLUpdate(connection, queries[40]);
	    oneSQLUpdate(connection, queries[41]);
	    oneSQLUpdate(connection, queries[42]);
	    oneSQLUpdate(connection, queries[43]);
	    oneSQLUpdate(connection, queries[44]);
	    oneSQLUpdate(connection, queries[45]);
	    oneSQLUpdate(connection, queries[46]);
	    
	    // ALGORITMO PAL:
		queries[47] = "CALL NEO_5QQN7TQREM10T642K0T903BJH.PAL_GEOR_PROC(PAL_GR_DATA_TBL,  '#PAL_CONTROL_TBL' , "
				+ " PAL_GR_RESULTS_TBL,"
				+ " PAL_GR_FITTED_TBL, PAL_GR_SIGNIFICANCE_TBL,PAL_GR_PMMLMODEL_TBL) with overview";

	    oneSQLCall(connection, queries[47]); // PAL-ALGORITMO
	    
	    oneSQLUpdate(connection, "drop table TEST_T");
	    oneSQLUpdate(connection, "create table TEST_T (ID INTEGER PRIMARY KEY)");
	    
	    //oneSQLUpdate(connection, queries[48]); // LEO DE LA RESULTS-TBL
		
	    if(connection != null) {connection.close();}
	        
	   return;
	}
	
	public void oneSQLUpdate(Connection connection, String query) throws SQLException {
	       Statement stmt = null;
	 	   try {
	 	       stmt = connection.createStatement();
	 	       stmt.executeUpdate(query);
	 	    } catch (SQLException e ) {
	 	    	
	 	    } finally {
	 	        if (stmt != null) { stmt.close(); }
	 	    }
	 }
	
	 public void oneSQLCall(Connection connection, String query) throws SQLException {
	       Statement stmt = null;
	 	   try {
	 	       stmt = connection.prepareCall(query);
	 	       stmt.execute(query);
	 	    } catch (SQLException e ) {
	 	    	
	 	    } finally {
	 	        if (stmt != null) { stmt.close(); }
	 	    }
	 }

}