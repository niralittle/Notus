package nc.notus.workflow;

import nc.notus.dao.OSSUserDAO;
import nc.notus.dao.ScenarioDAO;
import nc.notus.dao.ServiceOrderDAO;
import nc.notus.dao.ServiceOrderStatusDAO;
import nc.notus.dao.TaskDAO;
import nc.notus.dao.TaskStatusDAO;
import nc.notus.dao.impl.OSSUserDAOImpl;
import nc.notus.dao.impl.ScenarioDAOImpl;
import nc.notus.dao.impl.ServiceOrderDAOImpl;
import nc.notus.dao.impl.ServiceOrderStatusDAOImpl;
import nc.notus.dao.impl.TaskDAOImpl;
import nc.notus.dao.impl.TaskStatusDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.entity.OSSUser;
import nc.notus.entity.Scenario;
import nc.notus.entity.ServiceOrder;
import nc.notus.entity.ServiceOrderStatus;
import nc.notus.entity.Task;
import nc.notus.states.OrderStatus;
import nc.notus.states.TaskState;
import nc.notus.states.UserRole;

/**
 * This class provides functionality of managing workflow for particular Order.
 * All task and order management focused here.
 * @author Igor Litvinenko
 */
public abstract class Workflow {

    protected ServiceOrder order; // Service Order for which workflow was created

    public Workflow(ServiceOrder order) {
        this.order = order;
    }

    /**
     * This method proceeds Order by creating tasks for
     * corresponding user groups which take part in Order execution
     */
    public abstract void proceedOrder();

    /**
     * This method is used to create tasks and assign it to user groups.
     * Method is <code>protected</code> because it can only be invoked in
     * method of particular Workflow.
     * @param dbManager connection to database encapsulated in DBManager class
     * @param userRole identifies user group to create task for
     */
    protected void createTask(DBManager dbManager, UserRole userRole) {
        TaskDAO taskDAO = new TaskDAOImpl(dbManager);
        TaskStatusDAO taskStatusDAO = new TaskStatusDAOImpl(dbManager);

        Task task = new Task();
        task.setEmployeeID(null);
        task.setRoleID(userRole.toInt());
        task.setServiceOrderID(order.getId());
        task.setTaskStatusID(taskStatusDAO.getTaskStatusID(TaskState.ACTIVE));
        taskDAO.add(task);
    }

    /**
     * This method assigns task to particular user of user group
     * responsible for task execution
     * @param taskID ID of task to assign user to
     * @param userID ID of user to assign
     */
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

    /**
     * This method sets task status to "Completed"
     * @param taskID ID of task
     */
    public void completeTask(int taskID) {
        DBManager dbManager = new DBManager();
        TaskDAO taskDAO = new TaskDAOImpl(dbManager);
        TaskStatusDAO taskStatusDAO = new TaskStatusDAOImpl(dbManager);

        Task task = taskDAO.find(taskID);
        int taskStatusID = taskStatusDAO.getTaskStatusID(TaskState.ACTIVE);
        task.setTaskStatusID(taskStatusID);
        taskDAO.update(task);

        dbManager.commit();
        dbManager.close();
    }

    protected String getOrderStatus(DBManager dbManager) {
        ServiceOrderStatusDAO orderStatusDAO = new ServiceOrderStatusDAOImpl(dbManager);
        int statusID = order.getServiceOrderStatusID();
        ServiceOrderStatus status = orderStatusDAO.find(statusID);
        return status.getStatus();
    }

    protected String getOrderScenario(DBManager dbManager) {
        ScenarioDAO scenarioDAO = new ScenarioDAOImpl(dbManager);
        int scenarioID = order.getScenarioID();
        Scenario scenario = scenarioDAO.find(scenarioID);
        return scenario.getScenario();
    }

    protected void changeOrderStatus(DBManager dbManager, OrderStatus status) {
        ServiceOrderDAO orderDAO = new ServiceOrderDAOImpl(dbManager);
        ServiceOrderStatusDAO orderStatusDAO = new ServiceOrderStatusDAOImpl(dbManager);

        int statusID = orderStatusDAO.getServiceOrderStatusID(status);
        order.setServiceOrderStatusID(statusID);
        orderDAO.update(order);
    }
}
