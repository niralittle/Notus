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
    /*                                                                          // REVIEW: documentation with /** expected
     * Gets list of adresses of providerLocations via DAO
     */
    public List<ProviderLocation> getProviderLocations(){
        DBManager dbManager = new DBManager();                                  // REVIEW: try-finally block should be used
        ProviderLocationDAO locDAO = new ProviderLocationDAOImpl(dbManager);
        int start = 1;                                                          // REVIEW: local variables as constants
        int lastRecord = 5;
        List<ProviderLocation> providerList = locDAO.getProviderLocations(start, lastRecord);
        dbManager.close();
        return providerList;
    }
                                                                                // REVIEW: documentation expected
}
