package com.sap.cloud.sample.persistence;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

public class JobDAO {
	private DataSource dataSource;

    /**
     * Create new data access object with data source.
     */
    public JobDAO(DataSource newDataSource) throws SQLException {
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
     * Check if the person table already exists and create it if not.
     */
    private void checkTable() throws SQLException {
        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            if (!existsTable(connection)) {
                createTable(connection);
            } else {
            	dropTable(connection);
            	createTable(connection);
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
        ResultSet rs = meta.getTables(null, null, "T_JOBS", null);
        while (rs.next()) {
            String name = rs.getString("TABLE_NAME");
            if (name.equals("T_JOBS")) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Drop the job table
     */
    public void dropTable(Connection connection) throws SQLException {
    	PreparedStatement pstmt = connection.prepareStatement("DROP TABLE T_JOBS");
    	pstmt.executeUpdate();
    }

    /**
     * Create the person table.
     */
    private void createTable(Connection connection) throws SQLException {
        PreparedStatement pstmt = connection
                .prepareStatement("CREATE TABLE T_JOBS("
                        + "WORKER_NAME VARCHAR(255), "
                        + "PROJECT_NAME VARCHAR(255), "
                        + "HOURS FLOAT, "
                        + "DATE_JOB VARCHAR(255)"
                        + ")");
        pstmt.executeUpdate();
    }
    
    public List<Job> selectAllJobs() throws SQLException {
    	
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement pstmt = connection
                    .prepareStatement("SELECT WORKER_NAME, PROJECT_NAME, HOURS, DATE_JOB FROM T_JOBS");
            ResultSet rs = pstmt.executeQuery();
            ArrayList<Job> list = new ArrayList<Job>();
            while (rs.next()) {
                Job j = new Job();
                j.setHours(rs.getFloat(3));
                j.setDate(rs.getString(4));
                list.add(j);
            }
            return list;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
    
    /**
     * Add a job to the table.
     */
    public void addJob(Job job) throws SQLException {
        Connection connection = dataSource.getConnection();

        try {
            PreparedStatement pstmt = connection
                    .prepareStatement("INSERT INTO T_JOBS (WORKER_NAME, PROJECT_NAME, HOURS, DATE_JOB) VALUES (?, ?, ?, ?)");
            // TODO : here goes the id of the user in this session
            pstmt.setString(1, job.getWorker().getFirstName());
            pstmt.setString(2, job.getProject().getName());
            pstmt.setFloat(3, job.getHours());
            pstmt.setString(4, job.getDate());
            pstmt.executeUpdate();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

	
}