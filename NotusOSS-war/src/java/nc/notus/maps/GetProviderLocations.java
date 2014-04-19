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
        private final int START = 1;                                                          
        private final int LAST_RECORD = 5;
    /**                                                                          
     * Gets list of adresses of providerLocations via DAO
     * @return providerList - list of provider locations
     */
    public List<ProviderLocation> getProviderLocations(){
        DBManager dbManager = new DBManager();                                  // REVIEW: try-finally block should be used
        ProviderLocationDAO locDAO = new ProviderLocationDAOImpl(dbManager);
        List<ProviderLocation> providerList = locDAO.getProviderLocations(START, LAST_RECORD);
        dbManager.close();
        return providerList;
    }
                                                                                
}
