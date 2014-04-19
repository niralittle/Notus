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
    /*                                                                          // REVIEW: documentation with /** expected
     * Gets list of adresses of providerLocations via DAO
     */
<<<<<<< HEAD
    public List<ProviderLocation> getProviderLocations(){
        DBManager dbManager = new DBManager();
=======
    private void getAddressesList(){
        DBManager dbManager = new DBManager();                                  // REVIEW: try-finally block should be used
>>>>>>> e59e9accbe4781605a86ceb15c51193484989aa3
        ProviderLocationDAO locDAO = new ProviderLocationDAOImpl(dbManager);
        int start = 1;                                                          // REVIEW: local variables as constants
        int lastRecord = 5;
        List<ProviderLocation> providerList = locDAO.getProviderLocations(start, lastRecord);
        dbManager.close();
        return providerList;
    }
<<<<<<< HEAD
=======
                                                                                // REVIEW: documentation expected
    public List<String> getLocations(){                                         // REVIEW: method for method? really?
        getAddressesList();
        return addressesList;
    }


>>>>>>> e59e9accbe4781605a86ceb15c51193484989aa3
}
