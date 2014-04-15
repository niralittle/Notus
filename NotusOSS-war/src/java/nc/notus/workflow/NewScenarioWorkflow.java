package nc.notus.workflow;

import nc.notus.dao.ServiceOrderDAO;
import nc.notus.dao.ServiceOrderStatusDAO;
import nc.notus.dao.impl.ServiceOrderDAOImpl;
import nc.notus.dao.impl.ServiceOrderStatusDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.entity.ServiceOrder;
import nc.notus.states.OrderStatus;

/**
 * This class provides functionality for "New" scenarion workflow
 * @author Igor Litvinenko
 */
public class NewScenarioWorkflow implements Workflow {

    private ServiceOrder order;

    public NewScenarioWorkflow(ServiceOrder order) {
        this.order = order;
    }

    @Override
    public void proceedOrder() {
        DBManager dbManager = new DBManager();
        changeOrderStatus(dbManager, OrderStatus.PROCESSING);
        dbManager.commit();
        dbManager.close();
    }

    private void changeOrderStatus(DBManager dbManager, OrderStatus status) {
        ServiceOrderDAO orderDAO = new ServiceOrderDAOImpl(dbManager);
        ServiceOrderStatusDAO orderStatusDAO = new ServiceOrderStatusDAOImpl(dbManager);
        int statusID = orderStatusDAO.getServiceOrderStatusID(status);
        order.setServiceOrderStatusID(statusID);
        orderDAO.update(order);
    }
}
