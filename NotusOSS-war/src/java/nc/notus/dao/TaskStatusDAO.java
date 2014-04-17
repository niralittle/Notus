/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nc.notus.dao;

import nc.notus.entity.TaskStatus;
import nc.notus.states.TaskState;

/**
 *
 * @author Igor Litvinenko & Vladimir Ermolenko
 */
public interface TaskStatusDAO extends GenericDAO<TaskStatus> {

    /**
     * Method return id of our task
     * @param taskState
     * @return id of task
     */
    public int getTaskStatusID(TaskState taskState);
}
