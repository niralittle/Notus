package nc.notus.dao.impl;

import java.util.ArrayList;
import java.util.List;
import nc.notus.dao.CableDAO;
import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.ResultIterator;
import nc.notus.dbmanager.Statement;
import nc.notus.entity.Cable;

/**
 * Implementation of DAO for entity Cable
 * @author Igor Litvinenko & Vladimir Ermolenko
 */
public class CableDAOImpl extends GenericDAOImpl<Cable> implements CableDAO {

    public CableDAOImpl(DBManager dbManager) {
        super(dbManager);
    }

    /**
     * Method returns a list of unique type(names) of unused in Port cables
     * @return list of unique type(names) of unused in Port cables
     */
    @Override
    public List<String> getUniqueTypeFreeCables() {
        
        String query  = "SELECT c.cable " +
                        "FROM cable c " +
                        "LEFT JOIN port p ON c.id = p.cableid " +
                        "WHERE p.cableid IS NULL " +
                        "GROUP BY c.cable";
        Statement statement = dbManager.prepareStatement(query);
        ResultIterator ri = statement.executeQuery();
        List<String> freeCables = new ArrayList<String>();
        while (ri.next()){
            freeCables.add(ri.getString("cable"));
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
        String query  = "SELECT c.id, c.cable " +
                        "FROM cable c " +
                        "LEFT JOIN port p ON c.id = p.cableid " +
                        "WHERE p.cableid IS NULL " +
                        "AND rownum =1";
        Statement statement = dbManager.prepareStatement(query);
        ResultIterator ri = statement.executeQuery();
        if (ri.next()){
            freeCable = new Cable();
            freeCable.setId(ri.getInt("id"));
            freeCable.setCable(ri.getString("cable"));
        }
        return freeCable;
    }

}
