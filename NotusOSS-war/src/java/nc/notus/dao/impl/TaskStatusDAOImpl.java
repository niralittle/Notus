/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nc.notus.dao.impl;

import nc.notus.dao.TaskStatusDAO;
import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.ResultIterator;
import nc.notus.dbmanager.Statement;
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
        int  id = 0;
        String query = "SELECT ts.id, ts.status" +
                       "FROM taskstatus ts WHERE ts.status = ?";
        Statement statement = dbManager.prepareStatement(query);
        statement.setString(1, taskState.toString());
        ResultIterator ri = statement.executeQuery();
        if (ri.next()) {
            return ri.getInt("id");
        }
        return id;
    }

}
