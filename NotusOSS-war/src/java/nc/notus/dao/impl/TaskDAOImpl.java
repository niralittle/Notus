package nc.notus.dao.impl;

import java.util.ArrayList;
import java.util.List;

import nc.notus.dao.TaskDAO;
import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.ResultIterator;
import nc.notus.dbmanager.Statement;
import nc.notus.entity.Task;

/**
 * 
 * @author Igor Litvinenko & Vladimir Ermolenko & Panchenko Dmytro
 */
public class TaskDAOImpl extends GenericDAOImpl<Task> implements TaskDAO {

	public TaskDAOImpl(DBManager dbManager) {
		super(dbManager);
	}

        /**
         * Method returns list of numberOfRecords choosen Engineer type tasks with paging;
         * @param offset - offset from start position in paging
         * @param numberOfRecords - quantity of records to fetch
         * @param roleID - id of assigned role for tasks
         * @return list of tasks
         */
	   @Override
	    public List<Task> getEngTasks(int offset, int numberOfRecords, int roleID) {
	        String query  = "SELECT * FROM ( SELECT a.*, ROWNUM rnum FROM (" +
	                "SELECT t.id, t.serviceorderid, t.employeeid, t.roleid, t.taskstatusid, t.name " +
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
	            task.setName(ri.getString("name"));
	            tasksEng.add(task);
	        }
	        return tasksEng;
	    }
	    
	   /**
	    * Method returns list of numberOfRecords choosen userID tasks with paging;
	    * @param offset - offset from start position in paging
	    * @param numberOfRecords - quantity of records to fetch
	    * @param userID - user ID
	    * @return list of tasks
	    */
	   @Override
           public List<Task> getTasksByID(int offset, int numberOfRecords, int userID) {
	        String query  = "SELECT * FROM ( SELECT a.*, ROWNUM rnum FROM (" +
	                "SELECT t.id, t.serviceorderid, t.employeeid, t.roleid, t.taskstatusid, t.name " +
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
	            task.setName(ri.getString("name"));
	            tasks.add(task);
	        }
	        return tasks;
	    }

           /**
	    * Method returns list of numberOfRecords active assigned tasks with paging;
	    * @param offset - offset from start position in paging
	    * @param numberOfRecords - quantity of records to fetch
	    * @return list of tasks
	    */
           @Override
	    public List<Task> getAssignedTasks(int offset, int numberOfRecords) {
	        String query  = "SELECT * FROM ( SELECT a.*, ROWNUM rnum FROM (" +
	                "SELECT t.id, t.serviceorderid, t.employeeid, t.roleid, t.taskstatusid, t.name " +
	                "FROM task t " +
	                "JOIN taskstatus ts ON t.taskstatusid = ts.id " +
	                "WHERE ts.status = 'Active' " +
	                "AND t.employeeid IS NOT NULL " +
	                "ORDER BY t.id " +
	                ") a where ROWNUM <= ? )" +
	                "WHERE rnum  >= ?";
	        Statement statement = dbManager.prepareStatement(query);
	        statement.setInt(1, numberOfRecords);
	        statement.setInt(2, offset);
	        ResultIterator ri = statement.executeQuery();
	        List<Task> tasks = new ArrayList<Task>();
	        while (ri.next()){
	            Task task = new Task();
	            task.setId(ri.getInt("id"));
	            task.setServiceOrderID(ri.getInt("serviceorderid"));
	            task.setEmployeeID(ri.getInt("employeeid"));
	            task.setRoleID(ri.getInt("roleid"));
	            task.setTaskStatusID(ri.getInt("taskstatusid"));
	            task.setName(ri.getString("name"));
	            tasks.add(task);
	        }
	        return tasks;
	    }

    /**
     * Method returns count of active assigned tasks
     * @param offset - offset from start position in paging
     * @param numberOfRecords - quantity of records to fetch
     * @return count of tasks
     */
    @Override
    public long countAllAssigned() {
        long count = 0;
	String query  = "SELECT COUNT(*) total " +
	                "FROM task t " +
	                "JOIN taskstatus ts ON t.taskstatusid = ts.id " +
	                "WHERE ts.status = 'Active' " +
	                "AND t.employeeid IS NOT NULL ";
        Statement statement = dbManager.prepareStatement(query);
	ResultIterator ri = statement.executeQuery();
	if (ri.next()){
            count = ri.getLong("total");
	}
        return count;
    }


}
