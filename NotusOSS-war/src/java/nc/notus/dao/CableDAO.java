package nc.notus.dao;

/**
 * Interface of DAO for entity Cable
 * @author Igor Litvinenko
 */
public interface CableDAO extends GenericDAO {

    String getCableName(int cableID) throws DAOException;

}
