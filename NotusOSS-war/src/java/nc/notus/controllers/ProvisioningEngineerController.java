package nc.notus.controllers;

import nc.notus.dao.ServiceOrderDAO;
import nc.notus.dao.impl.ServiceOrderDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.DBManagerException;
import nc.notus.entity.ServiceOrder;
import nc.notus.workflow.DisconnectScenarioWorkflow;
import nc.notus.workflow.NewScenarioWorkflow;

public class ProvisioningEngineerController extends AbstractController {

	/**
	 * Default constructor. Used to internal transactions.
	 */
	public ProvisioningEngineerController() {
		super();
	}
	
	public ProvisioningEngineerController(DBManager dbManager) {
		super(dbManager);
	}
	
	
	public void createCircuit(int orderID, int taskID, String circuit) throws DBManagerException {
		ServiceOrderDAOImpl orderDAO = null;
		ServiceOrder order = null;
		NewScenarioWorkflow wf = null;
		try {
			if (isInternal) {
				dbManager = new DBManager();
			}
			orderDAO = new ServiceOrderDAOImpl(dbManager);
			order = orderDAO.find(orderID);			// find order
			
			//proceed to action
			wf = new NewScenarioWorkflow(order, dbManager);
			wf.createCircuit(taskID, circuit);

			if (isInternal) {
				dbManager.commit();
			}
			actionStatus = "Curcuit for location, " + order.getServiceLocation() +
					", successfully created!";
		} catch (DBManagerException wfExc) {
			if (isInternal) {
				dbManager.rollback();
			}
			throw new DBManagerException("Error was occured. ", wfExc);
		} finally {
			if (isInternal) {
				dbManager.close();
			}
		}
	}
	
	
	public void removeCircuit(int orderID, int taskID) throws DBManagerException {
		
		ServiceOrderDAO orderDAO = null;
		ServiceOrder order = null;
		DisconnectScenarioWorkflow wf = null;
		try {
			if (isInternal) {
				dbManager = new DBManager();
			}
			orderDAO = new ServiceOrderDAOImpl(dbManager);
			order = orderDAO.find(orderID);			// find order
			
			//proceed to action
			wf = new DisconnectScenarioWorkflow(order, dbManager);
			wf.removeCurcuitFromSI(taskID);

			if (isInternal) {
				dbManager.commit();
			}
			actionStatus = "Curcuit for location, " + order.getServiceLocation() +
					", successfully removed!";
		} catch (DBManagerException wfExc) {
			if (isInternal) {
				dbManager.rollback();
			}
			throw new DBManagerException("Error was occured. ", wfExc);
		} finally {
			if (isInternal) {
				dbManager.close();
			}
		}
	}
	
}
