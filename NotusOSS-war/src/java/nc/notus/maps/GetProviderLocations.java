package nc.notus.maps;

import java.util.ArrayList;
import java.util.List;
import nc.notus.dao.ProviderLocationDAO;
import nc.notus.dbmanager.DBManager;
import nc.notus.dao.impl.ProviderLocationDAOImpl;
import nc.notus.dbmanager.DBManagerException;
import nc.notus.entity.ProviderLocation;

/**
 * Class for getting Provider Locations via DAO
 * @author Roman, Alina
 */
public class GetProviderLocations {

    private final int START = 1;
    private final int NUMBER_OF_RECORDS = 5;

    /**
     * Gets list of adresses of providerLocations via DAO
     * @return providerList - list of provider locations
     */
    public List<ProviderLocation> getProviderLocations() throws DBManagerException {
        DBManager dbManager = new DBManager();
        List<ProviderLocation> providerList = new ArrayList<ProviderLocation>();
        try {                                         
            ProviderLocationDAO locDAO = new ProviderLocationDAOImpl(dbManager);
            providerList = locDAO.getProviderLocations(START, NUMBER_OF_RECORDS);
        } finally {
            dbManager.close();
        }
        return providerList;
    }
}
