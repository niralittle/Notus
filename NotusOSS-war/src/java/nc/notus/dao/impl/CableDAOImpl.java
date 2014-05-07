package nc.notus.dao.impl;

import java.util.ArrayList;
import java.util.List;

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

    public CableDAOImpl(DBManager dbManager) {
        super(dbManager);
    }

    /**
     * Method returns a list of unique type(names) of unused in Port cables
     * @return list of unique type(names) of unused in Port cables
     * @throws DBManagerException 
     */
    @Override
    public List<String> getUniqueTypeFreeCables() throws DBManagerException {
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
			throw new DBManagerException("The error was occured, " + ""
					+ "contact the administrator");
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
    public Cable getFreeCable() throws DBManagerException {
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
			throw new DBManagerException("The error was occured, " + ""
					+ "contact the administrator");
		} finally {
			statement.close();
		}
        return freeCable;
    }

}
