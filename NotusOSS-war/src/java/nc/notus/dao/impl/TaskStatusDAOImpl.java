package nc.notus.dao.impl;

import org.apache.log4j.Logger;

import nc.notus.dao.DAOException;
import nc.notus.dao.TaskStatusDAO;
import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.DBManagerException;
import nc.notus.dbmanager.ResultIterator;
import nc.notus.dbmanager.Statement;
import nc.notus.entity.TaskStatus;
import nc.notus.states.TaskState;

/**
 * Interface of DAO for entity TaskStatus
 * @author Igor Litvinenko & Vladimir Ermolenko
 */
public class TaskStatusDAOImpl extends GenericDAOImpl<TaskStatus> implements TaskStatusDAO {

	private static Logger logger = Logger.getLogger(TaskStatusDAOImpl.class.getName());
	
    public TaskStatusDAOImpl(DBManager dbManager) {
        super(dbManager);
    }
     /**
     * Method returns id of task
     * @param taskState - class that enumerates possible status of Tasks
     * @return id of task
     * @throws DAOException if task was not found
     */
    @Override
    public int getTaskStatusID(TaskState taskState) throws DBManagerException {
    	if (taskState == null) {
    		logger.error("Passed parameter <taskState> is null. ");
    		throw new DBManagerException("Passed parameter <taskState> is null."
    				+ " Can't proccess null reference!");
    	} 
    	Statement statement = null;
    	ResultIterator ri = null;
		
    	try {
			String query = "SELECT ts.id, ts.status "
					     + "FROM taskstatus ts WHERE ts.status = ?";
			statement = dbManager.prepareStatement(query);
			statement.setString(1, taskState.toString());
			
			ri = statement.executeQuery();
			if (ri.next()) {
				return ri.getInt("id");
			} else {
				throw new DBManagerException("Task status was not found");
			}
		} catch (DBManagerException exc) {
			throw new DBManagerException("The error was occured, "
					+ "contact the administrator");
		} finally {
			statement.close();
		}
    }
}
