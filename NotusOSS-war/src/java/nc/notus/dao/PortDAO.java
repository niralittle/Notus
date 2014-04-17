package nc.notus.dao;

import nc.notus.entity.Port;

/**
 * Interface of DAO for entity Port
 * @author Vladimir Ermolenko
 */
public interface PortDAO extends GenericDAO<Port> {

    /**
     * Return one free port in system for engineer
     * portStatus field in SQL select - it's a flag with 0 value as a free port and with 1 value when port is connected
     * @return one free port or null if allthe ports are busy
     */
    public Port getFreePort();                                            
}
