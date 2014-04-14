package nc.notus.dao;

import nc.notus.entity.Port;

/**
 * Interface of DAO for entity Port
 * @author Vladimir Ermolenko
 */
public interface PortDAO extends GenericDAO<Port> {

    /**
     * Return one free port in system for engineer
     * @return one  free port
     */
    public Port getFreePort();                                            
}
