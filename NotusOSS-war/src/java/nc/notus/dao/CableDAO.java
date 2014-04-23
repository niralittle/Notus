package nc.notus.dao;

import java.util.List;
import nc.notus.entity.Cable;

/**
 * Interface of DAO for entity Cable
 * @author Igor Litvinenko & Vladimir Ermolenko
 */
public interface CableDAO extends GenericDAO<Cable> {

    /**
     * Method returns a list of unique type(names) of unused in Port cables
     * @return list of unique type(names) of unused in Port cables
     */
    public List<String> getUniqueTypeFreeCables();
    
    /**
     * Method returns first unused in Port cable
     * @return cable wich is unused in Port cable
     */
    public Cable getFreeCable();
}
