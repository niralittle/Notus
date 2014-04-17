/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nc.notus.dao.impl;

import nc.notus.dao.TaskStatusDAO;
import nc.notus.dbmanager.DBManager;
import nc.notus.entity.TaskStatus;
import nc.notus.states.TaskState;

/**
 *
 * @author Igor Litvinenko & Vladimir Ermolenko
 */
public class TaskStatusDAOImpl extends GenericDAOImpl<TaskStatus> implements TaskStatusDAO {

    public TaskStatusDAOImpl(DBManager dbManager) {
        super(dbManager);
    }
     /**
     * Method return id of our task
     * @param taskState
     * @return id of task
     */
    public int getTaskStatusID(TaskState taskState) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
