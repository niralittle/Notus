package nc.notus.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import nc.notus.dao.CableDAO;
import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.DBManagerException;
import nc.notus.dbmanager.ResultIterator;
import nc.notus.dbmanager.Statement;
import nc.notus.entity.Cable;

/**
 * Implementation of DAO for entity Cable
 * @author Igor Litvinenko & Vladimir Ermolenko & Panchenko Dmytro
 */
public class CableDAOImpl extends GenericDAOImpl<Cable> implements CableDAO {

	private static Logger logger = Logger.getLogger(CableDAOImpl.class.getName());
	
    public CableDAOImpl(DBManager dbManager) {
        super(dbManager);
    }

    /**
     * Method returns a list of unique type(names) of unused in Port cables
     * @return list of unique type(names) of unused in Port cables
     */
    @Override
    public List<String> getUniqueTypeFreeCables() {
    	List<String> freeCables = null;
    	Statement statement = null;
    	ResultIterator ri = null;
    	
    	String query  = "SELECT c.cable " +
                        "FROM cable c " +
                        "LEFT JOIN port p ON c.id = p.cableid " +
                        "WHERE p.cableid IS NULL " +
                        "GROUP BY c.cable";
		try {
			statement = dbManager.prepareStatement(query);
			ri = statement.executeQuery();

			freeCables = new ArrayList<String>();
			while (ri.next()) {
				freeCables.add(ri.getString("cable"));
			}
		} catch (DBManagerException exc) {
			logger.error(exc.getMessage(), exc);
		} finally {
			statement.close();
		}
    	
		return freeCables;    
    }

    /**
     * Method returns first unused in Port cable
     * @return cable wich is unused in Port cable
     * or NULL
     * if there is no available one
     */
    @Override
    public Cable getFreeCable() {
        Cable freeCable = null;
        Statement statement = null;
        String query  = "SELECT c.id, c.cable " +
                        "FROM cable c " +
                        "LEFT JOIN port p ON c.id = p.cableid " +
                        "WHERE p.cableid IS NULL " +
                        "AND rownum =1";
		try {
			statement = dbManager.prepareStatement(query);
			ResultIterator ri = statement.executeQuery();
			if (ri.next()) {
				freeCable = new Cable();
				freeCable.setId(ri.getInt("id"));
				freeCable.setCable(ri.getString("cable"));
			}
		} catch (DBManagerException exc) {
			logger.error(exc.getMessage(), exc);
		} finally {
			statement.close();
		}
        return freeCable;
    }

}
