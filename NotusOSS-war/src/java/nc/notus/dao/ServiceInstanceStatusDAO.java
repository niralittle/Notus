package nc.notus.dao;

import nc.notus.entity.ServiceInstanceStatus;
import nc.notus.states.InstanceStatus;

/**
 * Interface of DAO for entity ServiceInstanceStatus                                                                             // REVIEW: documentation expected
 * @author Igor Litvinenko
 */
public interface ServiceInstanceStatusDAO extends GenericDAO<ServiceInstanceStatus> {

    /**
     * Method returns ID of status of ServiceInstance
     * @param InstanceStatus status of Service Instance
     * @return ID of given SI Status
     */
    public int getServiceInstanceStatusID(InstanceStatus status);
}
