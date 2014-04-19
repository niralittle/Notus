package nc.notus.dao;

import nc.notus.entity.TaskStatus;
import nc.notus.states.TaskState;

/**
 *                                                                              // REVIEW: documentation expected
 * @author Igor Litvinenko & Vladimir Ermolenko
 */
public interface TaskStatusDAO extends GenericDAO<TaskStatus> {

    /**                                                                         // REVIEW: id of our task? who are we?
     * Method return id of our task                                             // REVIEW: (here and everywhere): method returns, not method return
     * @param taskState                                                         // REVIEW: document param
     * @return id of task
     */
    public int getTaskStatusID(TaskState taskState);
}
