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
import nc.notus.dao.PortDAO;
import nc.notus.dao.ServiceInstanceDAO;
import nc.notus.dao.ServiceInstanceStatusDAO;
import nc.notus.dao.TaskDAO;
import nc.notus.dao.TaskStatusDAO;
import nc.notus.dao.impl.PortDAOImpl;
import nc.notus.dao.impl.ServiceInstanceDAOImpl;
import nc.notus.dao.impl.ServiceInstanceStatusDAOImpl;
import nc.notus.dao.impl.TaskDAOImpl;
import nc.notus.dao.impl.TaskStatusDAOImpl;
import nc.notus.entity.Port;
import nc.notus.entity.Task;
import nc.notus.states.InstanceStatus;
import nc.notus.states.TaskState;
import nc.notus.states.UserRole;

/**
 * This class provides functionality for "New" scenarion workflow
 * @author Igor Litvinenko
 */
public class NewScenarioWorkflow extends Workflow {

    public NewScenarioWorkflow(ServiceOrder order) {
        super(order);
    }

    @Override
    public void proceedOrder() {
        if(!this.isNewOrder()) {
            throw new WorkflowException("Order with status " +
                    "\"Entering\" is expected");
        }

        DBManager dbManager = new DBManager();
        ServiceOrderDAO orderDAO = new ServiceOrderDAOImpl(dbManager);
        PortDAO portDAO = new PortDAOImpl(dbManager);
        TaskDAO taskDAO = new TaskDAOImpl(dbManager);
        TaskStatusDAO taskStatusDAO = new TaskStatusDAOImpl(dbManager);

        changeOrderStatus(dbManager, OrderStatus.PROCESSING);
        ServiceInstance serviceInstance = createServiceInstance(dbManager);

        // Link Order with SI
        order.setServiceInstanceID(serviceInstance.getId());
        orderDAO.update(order);

        // Find free port
        Port port = portDAO.getFreePort();
        if(port == null) {
            Task task = new Task();
            task.setEmployeeID(null);
            task.setRoleID(UserRole.INSTALLATION_ENGINEER.toInt());
            task.setServiceOrderID(order.getId());
            task.setTaskStatusID(taskStatusDAO.getTaskStatusID(TaskState.ACTIVE));
            taskDAO.add(task);
        }

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

    private ServiceInstance createServiceInstance(DBManager dbManager) {
        ServiceInstanceDAO siDAO = new ServiceInstanceDAOImpl(dbManager);
        ServiceInstanceStatusDAO sisDAO = new ServiceInstanceStatusDAOImpl(dbManager);

        ServiceInstance serviceInstance = new ServiceInstance();
        serviceInstance.setCircuitID(null);
        serviceInstance.setPortID(null);
        Calendar cal = java.util.Calendar.getInstance();
        Date date = new Date(cal.getTimeInMillis());
        serviceInstance.setServiceInstanceDate(date);
        int statusID = sisDAO.getServiceInstanceStatusID(InstanceStatus.PLANNED);
        serviceInstance.setServiceInstanceStatusID(statusID);

        Object id = siDAO.add(serviceInstance);
        serviceInstance.setId((Integer)id);

        return serviceInstance;
    }
}
