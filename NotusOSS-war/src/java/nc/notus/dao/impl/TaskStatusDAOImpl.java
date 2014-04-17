/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nc.notus.dao.impl;

import nc.notus.dao.TaskStatusDAO;
import nc.notus.dbmanager.DBManager;
import nc.notus.entity.TaskStatus;

/**
 *
 * @author Igor Litvinenko
 */
public class TaskStatusDAOImpl extends GenericDAOImpl<TaskStatus> implements TaskStatusDAO {

    public TaskStatusDAOImpl(DBManager dbManager) {
        super(dbManager);
    }

}
