/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nc.notus.dao.impl;

import nc.notus.dao.TaskDAO;
import nc.notus.dbmanager.DBManager;
import nc.notus.entity.Task;

/**
 *
 * @author Igor Litvinenko
 */
public class TaskDAOImpl extends GenericDAOImpl<Task> implements TaskDAO {

    public TaskDAOImpl(DBManager dbManager) {
        super(dbManager);
    }
}
