/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nc.notus.maps;

import java.util.ArrayList;
import java.util.List;
import nc.notus.dao.ProviderLocationDAO;
import nc.notus.dbmanager.DBManager;
import nc.notus.dao.impl.ProviderLocationDAOImpl;
import nc.notus.entity.ProviderLocation;
/**
 * Class for getting Provider Locations via DAO
 * @author Roman
 */
public class GetProviderLocations {

    private List<String> addressesList;
    /*
     * Gets list of adresses of providerLocations via DAO
     */
    public List<ProviderLocation> getProviderLocations(){
        DBManager dbManager = new DBManager();
        ProviderLocationDAO locDAO = new ProviderLocationDAOImpl(dbManager);
        int start = 1;
        int lastRecord = 5;
        List<ProviderLocation> providerList = locDAO.getProviderLocations(start, lastRecord);
        dbManager.close();
        return providerList;
    }
}
