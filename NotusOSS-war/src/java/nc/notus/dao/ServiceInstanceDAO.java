package nc.notus.dao;

import java.util.List;
import nc.notus.dbmanager.DBManagerException;
import nc.notus.entity.ServiceInstance;

/**
 * Interface of DAO for entity ServiceInstance.
 * @author Vladimir Ermolenko
 */
public interface ServiceInstanceDAO extends GenericDAO<ServiceInstance> {
    
    /**
     * Method that returns list of ServiceInstances for userID
     * @param userID
     * @param offset - offset from start position in paging
     * @param numberOfRecords - quantity of records to fetch
     * @return list ServiceInstances  
     */
    public List<ServiceInstance> getServiceInstancesByUserID(int userID, 
            int offset, int numberOfRecords) throws DBManagerException;
}
