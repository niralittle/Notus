package nc.notus.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nc.notus.dao.TaskDAO;
import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.ResultIterator;
import nc.notus.dbmanager.Statement;
import nc.notus.entity.Task;

/**
 *
 * @author Igor Litvinenko & Vladimir Ermolenko
 */
public class TaskDAOImpl extends GenericDAOImpl<Task> implements TaskDAO {

    public TaskDAOImpl(DBManager dbManager) {
        super(dbManager);
    }
    
    
    /**
     * Method creates new instance of entity in DB
     * @param t entity to add to DB
     * @return Primary Key of created instance
     */
    @Override
    public Object add(Task task) {
        StringBuilder query = new StringBuilder();
        
        if(task.getEmployeeID() != null) {
	        query.append("INSERT INTO task(SERVICEORDERID, EMPLOYEEID, ROLEID, TASKSTATUSID) ");
	        query.append("VALUES(?, ?, ?, ?)");
	        Statement statement = dbManager.prepareStatement(query.toString());
	        statement.setInt(1, task.getServiceOrderID());
	        statement.setInt(2, task.getEmployeeID());
	        statement.setInt(3, task.getRoleID());
	        statement.setInt(4, task.getTaskStatusID());
	        statement.executeUpdate();
	        Object primaryKey = statement.getGeneratedPrimaryKey();
	        return primaryKey;
        } else {
        	query.append("INSERT INTO task(SERVICEORDERID, ROLEID, TASKSTATUSID) ");
	        query.append("VALUES(?, ?, ?)");
	        Statement statement = dbManager.prepareStatement(query.toString());
	        statement.setInt(1, task.getServiceOrderID());
	        statement.setInt(2, task.getRoleID());
	        statement.setInt(3, task.getTaskStatusID());
	        statement.executeUpdate();
	        Object primaryKey = statement.getGeneratedPrimaryKey();
	        return primaryKey;       
        }
        
    }
    
   /**
    * Method substitutes instance of entity in DB with one given.
    * It invocates <code>getId()</code> method of instance to get primary key
    * @param t instance of entity to update in DB
    */
    @Override
    public void update(Task task) { 
    	 StringBuilder query = new StringBuilder();
    	 query.append("UPDATE task ");
    	 query.append("SET SERVICEORDERID = ?, EMPLOYEEID = ?, ROLEID = ?, TASKSTATUSID = ?");
    	 query.append("WHERE id = ?");
    	 Statement statement = dbManager.prepareStatement(query.toString());
	        statement.setInt(1, task.getServiceOrderID());
	        statement.setInt(2, task.getEmployeeID());
	        statement.setInt(3, task.getRoleID());
	        statement.setInt(4, task.getTaskStatusID());
	        statement.setInt(5, task.getId());
	 statement.executeUpdate();
    }
    
    
    /**
     * Method returns list of numberOfRecords choosen Engineer type tasks with paging;
     * hash - virtual column to to prevent Lost Updates for implementing
     * Optimistic Locking using hashes in tasks updates
     * @param offset - offset from start position in paging
     * @param numberOfRecords - quantity of records to fetch
     * @param roleID - id of assigned role for tasks
     * @return list of tasks
     */
    @Override
    public List<Task> getEngTasks(int offset, int numberOfRecords, int roleID) {
        String query  = "SELECT * FROM ( SELECT a.*, ROWNUM rnum FROM (" +
                "SELECT t.id, t.serviceorderid, t.employeeid, t.roleid, t.taskstatusid, " +
                "ORA_HASH(t.employeeid || '/' || t.taskstatusid) hash " +
                "FROM task t " +
                "JOIN taskstatus ts ON t.taskstatusid = ts.id " +
                "WHERE t.roleid = ? " +
                "AND ts.status = 'Active' " +
                "AND t.employeeid IS NULL " +
                "ORDER BY t.id " +
                ") a where ROWNUM <= ? )" +
                "WHERE rnum  >= ?";
        Statement statement = dbManager.prepareStatement(query);
        statement.setInt(1, roleID);
        statement.setInt(2, numberOfRecords);
        statement.setInt(3, offset);
        ResultIterator ri = statement.executeQuery();
        List<Task> tasksEng = new ArrayList<Task>();
        while (ri.next()){
            Task task = new Task();
            task.setId(ri.getInt("id"));
            task.setServiceOrderID(ri.getInt("serviceorderid"));
            task.setEmployeeID(ri.getInt("employeeid"));
            task.setRoleID(ri.getInt("roleid"));
            task.setTaskStatusID(ri.getInt("taskstatusid"));
            task.setHash(ri.getLong("hash"));
            tasksEng.add(task);
        }
        return tasksEng;
    }
    
    /**
     * Method returns list of numberOfRecords choosen userID tasks with paging;
     * hash - virtual column to to prevent Lost Updates for implementing
     * Optimistic Locking using hashes in tasks updates
     * @param offset - offset from start position in paging
     * @param numberOfRecords - quantity of records to fetch
     * @param userID - user ID
     * @return list of tasks
     */
    public List<Task> getTasksByID(int offset, int numberOfRecords, int userID) {
        String query  = "SELECT * FROM ( SELECT a.*, ROWNUM rnum FROM (" +
                "SELECT t.id, t.serviceorderid, t.employeeid, t.roleid, t.taskstatusid, " +
                "ORA_HASH(t.employeeid || '/' || t.taskstatusid) hash " +
                "FROM task t " +
                "JOIN taskstatus ts ON t.taskstatusid = ts.id " +
                "WHERE ts.status = 'Active' " +
                "AND t.employeeid = ? " +
                "ORDER BY t.id " +
                ") a where ROWNUM <= ? )" +
                "WHERE rnum  >= ?";
        Statement statement = dbManager.prepareStatement(query);
        statement.setInt(1, userID);
        statement.setInt(2, numberOfRecords);
        statement.setInt(3, offset);
        ResultIterator ri = statement.executeQuery();
        List<Task> tasks = new ArrayList<Task>();
        while (ri.next()){
            Task task = new Task();
            task.setId(ri.getInt("id"));
            task.setServiceOrderID(ri.getInt("serviceorderid"));
            task.setEmployeeID(ri.getInt("employeeid"));
            task.setRoleID(ri.getInt("roleid"));
            task.setTaskStatusID(ri.getInt("taskstatusid"));
            task.setHash(ri.getLong("hash"));
            tasks.add(task);
        }
        return tasks;
    }


    /**
     * Method assign task to specific user without Lost Updates problem
     * @param task - task for update
     * @return quantity of updated records
     */

    @Override
    public int assignTask(Task task) {
        String query  = "UPDATE task " +
                        "SET employeeid = ? " +
                        "WHERE id = ? " +
                        "AND ORA_HASH(t.employeeid || '/' || t.taskstatusid) = ? ";
        Statement statement = dbManager.prepareStatement(query);
        statement.setInt(1, task.getEmployeeID());
        statement.setInt(2, task.getId());
        statement.setLong(3, task.getHash());
        int rowsUpdated = statement.executeUpdate();
        statement.close();
        return rowsUpdated;
    }


}