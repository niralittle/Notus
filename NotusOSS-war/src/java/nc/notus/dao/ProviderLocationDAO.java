package nc.notus.dao;

import java.util.List;
import nc.notus.entity.ProviderLocation;

/**
 * Interface of DAO for entity ProviderLocation
 * @author Vladimir Ermolenko
 */
public interface ProviderLocationDAO extends GenericDAO<ProviderLocation> {

    /**
     * Method return list of all(numberOfRecords) provided locations with paging // REVIEW: update documentation - all(numberOfRecords)
     * @param offset - offset from start position in paging
     * @param numberOfRecords - quantity of records to fetch
     * @return list of providerLocations
     */
    public List<ProviderLocation> getProviderLocations(int offset, int numberOfRecords);

}
