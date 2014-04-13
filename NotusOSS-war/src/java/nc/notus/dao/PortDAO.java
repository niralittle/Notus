package nc.notus.dao;

import java.util.List;
import nc.notus.entity.Port;

/**
 * Interface of DAO for entity Port
 * @author Vladimir Ermolenko
 */
public interface PortDAO extends GenericDAO<Port> {

    /**
     * Return list of all our free ports in system
     * @return list of all free ports
     */
    public List<Port> getFreePort();
}
