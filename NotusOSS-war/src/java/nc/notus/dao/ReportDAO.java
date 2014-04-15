/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nc.notus.dao;

import nc.notus.dbmanager.DBManager;
import nc.notus.entity.Device;

/**
 * Interface of DAO for our reports
 * @author Vladimir Ermolenko
 */
public interface ReportDAO {
    public Device returnMostProfitableRouter(DBManager dbManager);
}
