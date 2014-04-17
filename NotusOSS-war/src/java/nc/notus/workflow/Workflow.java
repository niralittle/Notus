package nc.notus.workflow;

import nc.notus.dao.OSSUserDAO;
import nc.notus.dao.ServiceOrderStatusDAO;
import nc.notus.dao.TaskDAO;
import nc.notus.dao.TaskStatusDAO;
import nc.notus.dao.impl.OSSUserDAOImpl;
import nc.notus.dao.impl.ServiceOrderStatusDAOImpl;
import nc.notus.dao.impl.TaskDAOImpl;
import nc.notus.dao.impl.TaskStatusDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.entity.OSSUser;
import nc.notus.entity.ServiceOrder;
import nc.notus.entity.Task;
import nc.notus.states.OrderStatus;

/**
 * TODO: documenting. It provides functionality
 * to proceed Orders through workflows.
 * @author Igor Litvinenko
 */
public abstract class Workflow {

    protected ServiceOrder order;

    public Workflow(ServiceOrder order) {
        this.order = order;
    }

    public abstract void proceedOrder();

    protected boolean isNewOrder() {
        DBManager dbManager = new DBManager();
        ServiceOrderStatusDAO sosDAO = new ServiceOrderStatusDAOImpl(dbManager);
        boolean isNewOrder;
        if (order.getServiceOrderStatusID() ==
                    sosDAO.getServiceOrderStatusID(OrderStatus.ENTERING)) {
            isNewOrder = true;
        } else {
            isNewOrder = false;
        }
        dbManager.close();

        return isNewOrder;
    }

    public void assignTask(int taskID, int userID) {
        DBManager dbManager = new DBManager();
        TaskDAO taskDAO = new TaskDAOImpl(dbManager);
        OSSUserDAO userDAO = new OSSUserDAOImpl(dbManager);

        try {
            Task task = taskDAO.find(taskID);
            OSSUser user = userDAO.find(userID);
            if (user.getRoleID() == task.getRoleID()) {
                task.setEmployeeID(userID);
                taskDAO.update(task);
            } else {
                throw new WorkflowException("User group isn't suitable for this task");
            }
            dbManager.commit();
        } finally {
            dbManager.close();
        }
    }

    public void completeTask(int taskID) {
        /*DBManager dbManager = new DBManager();
        TaskDAO taskDAO = new TaskDAOImpl(dbManager);
        TaskStatusDAO taskStatusDAO = new TaskStatusDAOImpl(dbManager);
        Task task = taskDAO.find(taskID);
        task.setTaskStatusID(taskID);*/
    }
}
