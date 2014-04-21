package nc.notus.workflow;

import nc.notus.dao.CableDAO;
import nc.notus.dao.CircuitDAO;
import nc.notus.dao.PortDAO;
import nc.notus.dao.ServiceInstanceDAO;
import nc.notus.dao.impl.CableDAOImpl;
import nc.notus.dao.impl.CircuitDAOImpl;
import nc.notus.dao.impl.PortDAOImpl;
import nc.notus.dao.impl.ServiceInstanceDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.entity.Port;
import nc.notus.entity.ServiceInstance;
import nc.notus.entity.ServiceOrder;
import nc.notus.states.InstanceStatus;
import nc.notus.states.OrderStatus;
import nc.notus.states.PortState;
import nc.notus.states.UserRole;
import nc.notus.states.WorkflowScenario;

/**
 * This class provides functionality for "Disconnect" scenario workflow
 * @author Igor Litvinenko
 */
public class DisconnectScenarioWorkflow extends Workflow {

    /**
     * This method creates DisconnectScenarioWorkflow for given Order.
     * It doesn't proceed Order to execution(See {@link Workflow#proceedOrder()})
     * @param order Order to create Workflow for
     * @throws Workflow exception if Order scenario doesn't match "Disconnect"
     * scenario workflow
     */
    public DisconnectScenarioWorkflow(ServiceOrder order) {
        super(order);
        DBManager dbManager = new DBManager();
        try {
            if (!getOrderScenario(dbManager).equals(WorkflowScenario.DISCONNECT.toString())) {
                throw new WorkflowException("Cannot proceed Order: wrong order scenario");
            }
        } finally {
            dbManager.close();
        }
    }

    /**
     * This method proceeds Order by creating tasks for
     * corresponding user groups which take part in Order execution.
     * Order should have status "Entering" and workflow scenario "Disconnect"
     */
    @Override
    public void proceedOrder() {
        DBManager dbManager = new DBManager();
        try {
            if (!getOrderStatus(dbManager).equals(OrderStatus.ENTERING.toString())) {
                throw new WorkflowException("Cannot proceed Order: wrong order state");
            }

            changeOrderStatus(dbManager, OrderStatus.PROCESSING);
            createTask(dbManager, UserRole.PROVISION_ENGINEER);
            dbManager.commit();
        } finally {
            dbManager.close();
        }
    }

    /**
     * This method unassigns given Port from given Service Instance.
     * It also unlinks Circuit from SI and deletes the Circuit.
     * It sets status of given task to "Completed".
     * After execution it automatically creates task to Customer Support
     * Engineer group.
     * @param taskID ID of task for Provisioning Engineer
     * @param portID ID of port to unlink SI from
     * @param serviceInstanceID ID of SI to unlink from port
     */
    public void unassignPort(int taskID, int portID, int serviceInstanceID) {
        if (order.getServiceInstanceID() != serviceInstanceID) {
            throw new WorkflowException("Given SI is not connected " +
                    "with current Order");
        }

        DBManager dbManager = new DBManager();
        try {
            if (!isTaskValid(dbManager, taskID,
                    UserRole.PROVISION_ENGINEER.toInt())) {
                throw new WorkflowException("Given Task is not valid");
            }
            ServiceInstanceDAO siDAO = new ServiceInstanceDAOImpl(dbManager);
            CircuitDAO circuitDAO = new CircuitDAOImpl(dbManager);

            ServiceInstance si = siDAO.find(serviceInstanceID);
            int circuitID = si.getCircuitID();
            si.setCircuitID(null);
            si.setPortID(null);
            siDAO.update(si);

            circuitDAO.delete(circuitID);

            this.completeTask(dbManager, taskID);
            this.createTask(dbManager, UserRole.INSTALLATION_ENGINEER);
            dbManager.commit();
        } finally {
            dbManager.close();
        }
    }

    /**
     * This method unplugs Cable from specified Port.
     * It sets Port status to "Free" and changes status of Task
     * to "Completed" after execution.
     * After it's done method automatically creates task for Provisioning Engineer
     * @param taskID taskID ID of task for installation engineer
     * @param cableID ID of Cable to unplug
     * @param portID ID of Port to unplug Cable from
     */
    public void unplugCableFromPort(int taskID, int cableID, int portID) {
        DBManager dbManager = new DBManager();
        try {
            if (!isTaskValid(dbManager, taskID,
                    UserRole.INSTALLATION_ENGINEER.toInt())) {
                throw new WorkflowException("Given Task is not valid");
            }

            PortDAO portDAO = new PortDAOImpl(dbManager);
            Port port = portDAO.find(portID);
            port.setCableID(null);
            port.setPortStatus(PortState.FREE.toInt());
            portDAO.update(port);

            CableDAO cableDAO = new CableDAOImpl(dbManager);
            cableDAO.delete(cableID);

            completeTask(dbManager, taskID);
            changeServiceInstanceStatus(dbManager, InstanceStatus.DISCONNECTED);
            changeOrderStatus(dbManager, OrderStatus.COMPLETED);
            // TODO: send email here
            dbManager.commit();
        } finally {
            dbManager.close();
        }
    }
}