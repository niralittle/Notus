package nc.notus.workflow;

import nc.notus.dbmanager.DBManager;
import nc.notus.entity.ServiceOrder;
import nc.notus.states.OrderStatus;
import nc.notus.states.UserRole;
import nc.notus.states.WorkflowScenario;

/**
 * This class provides functionality for "Modify" scenario workflow
 * @author Igor Litvinenko
 */
public class ModifyScenarioWorkflow extends Workflow {
    
    /**
     * This method creates ModifyScenarioWorkflow for given Order.
     * It doesn't proceed Order to execution(See {@link Workflow#proceedOrder()})
     * @param order Order to create Workflow for
     * @throws Workflow exception if Order scenario doesn't match "Modify" scenario
     * workflow
     */
    public ModifyScenarioWorkflow(ServiceOrder order) {
        super(order);
        DBManager dbManager = new DBManager();
        try {
            if (!getOrderScenario(dbManager).equals(WorkflowScenario.MODIFY.toString())) {
                throw new WorkflowException("Cannot proceed Order: wrong order scenario");
            }
        } finally {
            dbManager.close();
        }
    }
    
    /**
     * This method proceeds Order by creating tasks for
     * corresponding user groups which take part in Order execution.
     * Order should have status "Entering" and workflow scenario "Modify"
     */
    @Override
    public void proceedOrder() {
        DBManager dbManager = new DBManager();
        try {
            if (!getOrderStatus(dbManager).equals(OrderStatus.ENTERING.toString())) {
                throw new WorkflowException("Cannot proceed Order: wrong order state");
            }

            changeOrderStatus(dbManager, OrderStatus.PROCESSING);
            createTask(dbManager, UserRole.SUPPORT_ENGINEER);
            dbManager.commit();
        } finally {
            dbManager.close();
        }
    }

    /**
     * This method approves and sends Bill to the Customer.
     * It also changes Order status for modification to "Completed" which
     * automatically leads to updating type of provided service for customer.
     * @param taskID ID of Task for Support Engineer
     */
    public void approveBill(int taskID) {
        DBManager dbManager = new DBManager();
        try {
            if(!isTaskValid(dbManager, taskID, UserRole.SUPPORT_ENGINEER.toInt())) {
                throw new WorkflowException("Given Task is not valid");
            }

            completeTask(dbManager, taskID);
            changeOrderStatus(dbManager, OrderStatus.COMPLETED);
            // TODO: send email here
            dbManager.commit();
        } finally {
            dbManager.close();
        }
    }

}
