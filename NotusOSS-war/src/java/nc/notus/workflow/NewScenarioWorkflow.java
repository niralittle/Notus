package nc.notus.workflow;

import java.sql.Date;
import nc.notus.dao.ServiceOrderDAO;
import nc.notus.dao.ServiceOrderStatusDAO;
import nc.notus.dao.impl.ServiceOrderDAOImpl;
import nc.notus.dao.impl.ServiceOrderStatusDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.entity.ServiceInstance;
import nc.notus.entity.ServiceOrder;
import nc.notus.states.OrderStatus;
import java.util.Calendar;
import nc.notus.dao.ServiceInstanceDAO;
import nc.notus.dao.ServiceInstanceStatusDAO;
import nc.notus.dao.impl.ServiceInstanceDAOImpl;
import nc.notus.dao.impl.ServiceInstanceStatusDAOImpl;
import nc.notus.states.InstanceStatus;

/**
 * This class provides functionality for "New" scenarion workflow
 * @author Igor Litvinenko
 */
public class NewScenarioWorkflow implements Workflow {

    private ServiceOrder order;

    public NewScenarioWorkflow(ServiceOrder order) {
        // TODO: check order status(should be 'entering'
        this.order = order;
    }

    @Override
    public void proceedOrder() {
        DBManager dbManager = new DBManager();
        changeOrderStatus(dbManager, OrderStatus.PROCESSING);
        ServiceInstance serviceInstance = createServiceInstance(dbManager);
//        // Link Order with SI
//        order.setServiceInstanceID(serviceInstance.getId());
//        ServiceOrderDAO orderDAO = new ServiceOrderDAOImpl(dbManager);
//        orderDAO.update(order);
        dbManager.commit();
        dbManager.close();
        
//        
    }

    private void changeOrderStatus(DBManager dbManager, OrderStatus status) {
        ServiceOrderDAO orderDAO = new ServiceOrderDAOImpl(dbManager);
        ServiceOrderStatusDAO orderStatusDAO = new ServiceOrderStatusDAOImpl(dbManager);
        int statusID = orderStatusDAO.getServiceOrderStatusID(status);
        order.setServiceOrderStatusID(statusID);
        orderDAO.update(order);
    }

    private ServiceInstance createServiceInstance(DBManager dbManager) {
        ServiceInstance serviceInstance = new ServiceInstance();

        serviceInstance.setCircuitID(null);
        serviceInstance.setPortID(null);

        Calendar cal = java.util.Calendar.getInstance();
        Date date = new Date(cal.getTimeInMillis());
        serviceInstance.setServiceInstanceDate(date);

        ServiceInstanceStatusDAO statusDAO;
        statusDAO = new ServiceInstanceStatusDAOImpl(dbManager);
        int statusID = statusDAO.getServiceInstanceStatusID(InstanceStatus.PLANNED);
        serviceInstance.setServiceInstanceStatusID(statusID);

        ServiceInstanceDAO siDAO = new ServiceInstanceDAOImpl(dbManager);
        Object id = siDAO.add(serviceInstance);
        serviceInstance.setId((Integer)id);

        return serviceInstance;
    }
}
