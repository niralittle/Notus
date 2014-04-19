package nc.notus.dao;

import nc.notus.entity.TaskStatus;
import nc.notus.states.TaskState;

/**
 * Interface of DAO for entity TaskStatus   
 * @author Igor Litvinenko & Vladimir Ermolenko
 */
public interface TaskStatusDAO extends GenericDAO<TaskStatus> {

    /**                                                                      
     * Method returns id of task
     * @param taskState - class that enumerates possible status of Tasks
     * @return id of task
     * @throws DAOException if task was not found
     */
    public int getTaskStatusID(TaskState taskState);
}
