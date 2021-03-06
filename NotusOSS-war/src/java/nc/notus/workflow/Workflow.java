package nc.notus.workflow;

import nc.notus.dao.OSSUserDAO;
import nc.notus.dao.ScenarioDAO;
import nc.notus.dao.ServiceInstanceDAO;
import nc.notus.dao.ServiceInstanceStatusDAO;
import nc.notus.dao.ServiceOrderDAO;
import nc.notus.dao.ServiceOrderStatusDAO;
import nc.notus.dao.TaskDAO;
import nc.notus.dao.TaskStatusDAO;
import nc.notus.dao.impl.OSSUserDAOImpl;
import nc.notus.dao.impl.ScenarioDAOImpl;
import nc.notus.dao.impl.ServiceInstanceDAOImpl;
import nc.notus.dao.impl.ServiceInstanceStatusDAOImpl;
import nc.notus.dao.impl.ServiceOrderDAOImpl;
import nc.notus.dao.impl.ServiceOrderStatusDAOImpl;
import nc.notus.dao.impl.TaskDAOImpl;
import nc.notus.dao.impl.TaskStatusDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.DBManagerException;
import nc.notus.entity.OSSUser;
import nc.notus.entity.Scenario;
import nc.notus.entity.ServiceInstance;
import nc.notus.entity.ServiceOrder;
import nc.notus.entity.ServiceOrderStatus;
import nc.notus.entity.Task;
import nc.notus.states.InstanceStatus;
import nc.notus.states.OrderStatus;
import nc.notus.states.TaskState;
import nc.notus.states.UserRole;

/**
 * This class provides functionality of managing workflow for particular Order.
 * All task and order management focused here.
 * @author Igor Litvinenko
 */
public abstract class Workflow {
    /**
     * Service Order for which workflow was created
     */
    protected ServiceOrder order;
    /**
     * Data base manager
     */
    protected DBManager dbManager;
    /**
     * Initialize newly created workflow object
     * @param order Service Order for which workflow was created
     * @param dbManager data base manager
     */
    public Workflow(ServiceOrder order, DBManager dbManager) {
        this.order = order;
        this.dbManager = dbManager;
    }

    /**
     * This method proceeds Order by creating tasks for
     * corresponding user groups which take part in Order execution
     * @throws DBManagerException
     */
    public abstract void proceedOrder() throws DBManagerException;

    /**
     * This method assigns task to particular user of user group
     * responsible for task execution
     * @param taskID ID of task to assign user to
     * @param userID ID of user to assign
     * @throws DBManagerException
     */
    public void assignTask(int taskID, int userID) throws DBManagerException {
        try {
            TaskDAO taskDAO = new TaskDAOImpl(dbManager);
            OSSUserDAO userDAO = new OSSUserDAOImpl(dbManager);

            Task task = taskDAO.find(taskID);
            OSSUser user = userDAO.find(userID);
            if (isTaskValid(taskID, user.getRoleID())) {
                task.setEmployeeID(userID);
                taskDAO.update(task);
            } else {
                throw new DBManagerException("Given Task is not valid");
            }
            dbManager.commit();
        } catch(Exception ex) {
            // need to be logged like:
            //log.error("SQLException", ex);
            dbManager.rollback();

        } finally {
            dbManager.close();
        }
    }

    /**
     * This method is used to create tasks and assign it to user groups.
     * Method is <code>protected</code> because it can only be invoked in
     * Workflow methods.
     * @param userRole identifies user group to create task for
     * @param name task name
     * @throws DBManagerException
     */
    protected void createTask(UserRole userRole, String name) throws DBManagerException {
        TaskDAOImpl taskDAO = new TaskDAOImpl(dbManager);
        TaskStatusDAO taskStatusDAO = new TaskStatusDAOImpl(dbManager);

        Task task = new Task();
        task.setEmployeeID(null);
        task.setRoleID(userRole.toInt());
        task.setServiceOrderID(order.getId());
        task.setTaskStatusID(taskStatusDAO.getTaskStatusID(TaskState.ACTIVE));
        task.setName(name);
        taskDAO.add(task);
    }

    /**
     * This method sets task status to "Completed".
     * Method is <code>protected</code> because it can only be invoked in
     * Workflow methods.
     * @param taskID ID of task
     * @throws DBManagerException
     */
    protected void completeTask(int taskID) throws DBManagerException {
        TaskDAO taskDAO = new TaskDAOImpl(dbManager);
        TaskStatusDAO taskStatusDAO = new TaskStatusDAOImpl(dbManager);

        Task task = taskDAO.find(taskID);
        int taskStatusID = taskStatusDAO.getTaskStatusID(TaskState.COMPLETED);
        task.setTaskStatusID(taskStatusID);
        taskDAO.update(task);
    }

    /**
     * This method gets status of given Service Order
     * @throws DBManagerException
     * @return status of given Service Order
     */
    protected String getOrderStatus() throws DBManagerException {
        ServiceOrderStatusDAO orderStatusDAO = new ServiceOrderStatusDAOImpl(dbManager);
        int statusID = order.getServiceOrderStatusID();
        ServiceOrderStatus status = orderStatusDAO.find(statusID);
        return status.getStatus();
    }

    /**
     * This method gets scenario of given Service Order
     * @throws DBManagerException
     * @return scenario of given Service Order
     */
    protected String getOrderScenario() throws DBManagerException {
        ScenarioDAO scenarioDAO = new ScenarioDAOImpl(dbManager);
        int scenarioID = order.getScenarioID();
        Scenario scenario = scenarioDAO.find(scenarioID);
        return scenario.getScenario();
    }

    /**
     * This method changes status of Service Instance
     * @param status new status of Service Instance
     * @throws DBManagerException
     */
    protected void changeServiceInstanceStatus(InstanceStatus status) throws DBManagerException {
        ServiceInstanceDAO siDAO = new ServiceInstanceDAOImpl(dbManager);
        ServiceInstanceStatusDAO sisDAO = new ServiceInstanceStatusDAOImpl(dbManager);

        int statusID = sisDAO.getServiceInstanceStatusID(status);
        ServiceInstance si = siDAO.find(order.getServiceInstanceID());
        si.setServiceInstanceStatusID(statusID);
        siDAO.update(si);
    }

    /**
     * This method changes status of Service Order
     * @param status new status of Service Order
     * @throws DBManagerException
     */
    protected void changeOrderStatus(OrderStatus status) throws DBManagerException {
        ServiceOrderDAO orderDAO = new ServiceOrderDAOImpl(dbManager);
        ServiceOrderStatusDAO orderStatusDAO = new ServiceOrderStatusDAOImpl(dbManager);

        int statusID = orderStatusDAO.getServiceOrderStatusID(status);
        order.setServiceOrderStatusID(statusID);
        orderDAO.update(order);
    }

    /**
     * This method checks whether given Task is active, connected with
     * current Order and was created for given User Role
     * @param taskID ID of Task to validate
     * @param userRoleID ID of User Role the Task was created for
     * @return <code>true</code> if Task is valid for execution and
     * <code>false</code> otherwise
     * @throws DBManagerException
     */
    protected boolean isTaskValid(int taskID, int userRoleID) throws DBManagerException {
        TaskDAO taskDAO = new TaskDAOImpl(dbManager);
        TaskStatusDAO taskStatusDAO = new TaskStatusDAOImpl(dbManager);

        Task task = taskDAO.find(taskID);
        if (task.getServiceOrderID() != order.getId()) {
            return false;
        } else if (task.getRoleID() != userRoleID) {
            return false;
        } else {
            int activeStatusID = taskStatusDAO.getTaskStatusID(TaskState.ACTIVE);
            if (task.getTaskStatusID() != activeStatusID) {
                return false;
            } else {
                return true;
            }
        }
    }
}
