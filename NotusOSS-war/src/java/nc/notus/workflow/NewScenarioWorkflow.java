package nc.notus.workflow;

import nc.notus.dao.RoleDAO;
import nc.notus.dao.ServiceOrderDAO;
import nc.notus.dao.impl.RoleDAOImpl;
import nc.notus.dao.impl.ServiceOrderDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.entity.ServiceOrder;
import nc.notus.states.OrderStatus;

/**
 *
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
        RoleDAO roleDAO = new RoleDAOImpl(dbManager);
        ServiceOrderDAO orderDAO = new ServiceOrderDAOImpl(dbManager);
        int statusID = orderDAO.getServiceOrderStatusID(status);
        order.setServiceOrderStatusID(statusID);
        orderDAO.update(order);
    }
}
