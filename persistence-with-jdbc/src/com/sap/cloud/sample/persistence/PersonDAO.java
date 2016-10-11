package com.sap.cloud.sample.persistence;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

/**
 * Data access object encapsulating all JDBC operations for a person.
 */
public class PersonDAO {
    private DataSource dataSource;

    /**
     * Create new data access object with data source.
     */
    public PersonDAO(DataSource newDataSource) throws SQLException {
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
        checkTable();
    }

    /**
     * Add a person to the table.
     */
    public void addPerson(Person person) throws SQLException {
        Connection connection = dataSource.getConnection();

        try {
            PreparedStatement pstmt = connection
                    .prepareStatement("INSERT INTO T_PERSONS (PASSWORD, FIRSTNAME, LASTNAME, "
                    		+ "AGE, USERNAME, EARNINGS) VALUES (?, ?, ?, ?, ?, ?)");
            pstmt.setString(1, person.getPassword());
            pstmt.setString(2, person.getFirstName());
            pstmt.setString(3, person.getLastName());
            pstmt.setInt(4,  person.getAge());
            pstmt.setString(5, person.getUserName());
            pstmt.setInt(6, person.getEarnings());
            pstmt.executeUpdate();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     * Get all persons from the table.
     */
    public List<Person> selectAllPersons() throws SQLException {
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement pstmt = connection
                    .prepareStatement("SELECT PASSWORD, FIRSTNAME, LASTNAME, "
                    		+ "AGE, USERNAME, EARNINGS FROM T_PERSONS");
            ResultSet rs = pstmt.executeQuery();
            ArrayList<Person> list = new ArrayList<Person>();
            while (rs.next()) {
                Person p = new Person();
                p.setPassword(rs.getString(1));
                p.setFirstName(rs.getString(2));
                p.setLastName(rs.getString(3));
                p.setAge(Integer.parseInt(rs.getString(4)));
                p.setUserName(rs.getString(5));
                p.setEarnings(Integer.parseInt(rs.getString(6)));
                list.add(p);
            }
            return list;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     * Check if the person table already exists and create it if not.
     */
    private void checkTable() throws SQLException {
        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            
            if (!existsTable(connection)) {
                createTable(connection);
                //createTableTest();
            } else {
            	dropTable(connection);
            	//createTable(connection);
            }
            
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     * Check if the person table already exists.
     */
    private boolean existsTable(Connection conn) throws SQLException {
        DatabaseMetaData meta = conn.getMetaData();
        ResultSet rs = meta.getTables(null, null, "T_PERSONS", null);
        while (rs.next()) {
            String name = rs.getString("TABLE_NAME");
            if (name.equals("T_PERSONS")) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Drop the person table.
     */
    private void dropTable(Connection connection) throws SQLException {
    	PreparedStatement pstmt = connection.prepareStatement("DROP TABLE T_PERSONS");
    	pstmt.executeUpdate();
    }

    /**
     * Create the person table.
     */
    private void createTable(Connection connection) throws SQLException {
        PreparedStatement pstmt = connection
                .prepareStatement("CREATE TABLE T_PERSONS "
                        + "(PASSWORD VARCHAR(255), "
                        + "FIRSTNAME VARCHAR (255), "
                        + "LASTNAME VARCHAR (255), "
                        + "AGE INTEGER, "
                        + "USERNAME VARCHAR(255) PRIMARY KEY, "
                        + "EARNINGS INTEGER"
                        + ")");
        pstmt.executeUpdate();
    }
    
    public void createTableTest() throws SQLException {
    	Connection connection = dataSource.getConnection();
    	Statement pstmt = connection.createStatement();
    	pstmt.execute("CREATE TABLE T_TEST (ID INTEGER PRIMARY KEY);");
    	//if(connection != null) {connection.close();}
    }
    

}








