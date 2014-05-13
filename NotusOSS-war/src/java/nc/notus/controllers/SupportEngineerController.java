package nc.notus.controllers;

import nc.notus.dao.TaskDAO;
import nc.notus.dao.impl.OSSUserDAOImpl;
import nc.notus.dao.impl.ServiceOrderDAOImpl;
import nc.notus.dao.impl.TaskDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.DBManagerException;
import nc.notus.email.Email;
import nc.notus.email.EmailSender;
import nc.notus.email.NewPasswordEmail;
import nc.notus.entity.OSSUser;
import nc.notus.entity.ServiceOrder;
import nc.notus.entity.Task;
import nc.notus.workflow.NewScenarioWorkflow;

/**
 * Class which contains Support Engineer's actions
 * @author Dima
 */
public class SupportEngineerController extends AbstractController {

    /**
     * Default constructor. Used to internal transactions.
     */
    public SupportEngineerController() {
        super();
    }

    /**
     * Initializes controller with dbManager
     * @param dbManager data base manager
     */
    public SupportEngineerController(DBManager dbManager) {
        super(dbManager);
    }

    /**
     * Send bill to customer user.
     * (Implementation note: Commit changes in the DB after updating user)
     * @param taskID id of task for Support Engoneer
     * @throws DBManagerException
     */
    public void sendBillToCustomer(int taskID) throws DBManagerException {
        NewScenarioWorkflow wf = null;
        ServiceOrderDAOImpl orderDAO = null;
        TaskDAO taskDAO = null;
        try {
            if (isInternal) {
                dbManager = new DBManager();
            }
            taskDAO = new TaskDAOImpl(dbManager);
            Task task = taskDAO.find(taskID);
            int orderID = task.getServiceOrderID();

            orderDAO = new ServiceOrderDAOImpl(dbManager);
            ServiceOrder order = orderDAO.find(orderID);

            wf = new NewScenarioWorkflow(order, dbManager);
            wf.approveBill(taskID);

            if (isInternal) {
                dbManager.commit();
            }
        } catch (DBManagerException ex) {
            if (isInternal) {
                dbManager.rollback();
            }
            throw new DBManagerException("Error was occured while sending bill.", ex);
        } finally {
            if (isInternal) {
                dbManager.close();
            }
        }
    }

    /**
     * Method for changing customer's password
     * Sends "change password" e-mail
     * @param userID customer's id
     * @param newPassword new password
     * @throws DBManagerException
     */
    public void changeCustomerPassword(int userID, String newPassword) throws DBManagerException {
        OSSUserDAOImpl userDAO = null;
        try {
            if (isInternal) {
                dbManager = new DBManager();
            }
            userDAO = new OSSUserDAOImpl(dbManager);
            // get user by id and set him new password
            OSSUser user = userDAO.find(userID);
            user.setPassword(newPassword);
            userDAO.update(user);
            if (isInternal) {
                dbManager.commit();
            }
            
            Email mail = new NewPasswordEmail(user.getFirstName(), newPassword);
            EmailSender emailSender = new EmailSender();
            emailSender.sendEmail(userID, mail);
        } catch (DBManagerException ex) {
            if (isInternal) {
                dbManager.rollback();
            }
            throw new DBManagerException(
                    "Error was occured while changing password.", ex);
        } finally {
            if (isInternal) {
                dbManager.close();
            }
        }
    }
}
