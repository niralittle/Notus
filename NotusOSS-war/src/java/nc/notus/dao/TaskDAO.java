package nc.notus.dao;

import java.util.List;
import nc.notus.entity.Task;

/**
 * Interface of DAO for entity Task
 * @author Igor Litvinenko & Vladimir Ermolenko
 */
public interface TaskDAO extends GenericDAO<Task> {

    /**
     * Method returns list of numberOfRecords choosen Engineer type tasks with paging;
     * hash - virtual column to to prevent Lost Updates for implementing
     * Optimistic Locking using hashes in tasks updates
     * @param offset - offset from start position in paging
     * @param numberOfRecords - quantity of records to fetch
     * @param roleID - id of assigned role for tasks
     * @return list of tasks
     */
    public List<Task> getEngTasks(int offset, int numberOfRecords, int roleID);

    /**
     * Method returns list of numberOfRecords choosen userID tasks with paging;
     * hash - virtual column to to prevent Lost Updates for implementing
     * Optimistic Locking using hashes in tasks updates
     * @param offset - offset from start position in paging
     * @param numberOfRecords - quantity of records to fetch
     * @param userID - user ID
     * @return list of tasks
     */
    public List<Task> getTasksByID(int offset, int numberOfRecords, int userID);

    /**
     * Method assign task to specific user without Lost Updates problem
     * @param taskID - task for update
     * @param employeeID - who is responsible for task fulfilment
     * @param hash - virtual column to to prevent Lost Updates for implementing
     * Optimistic Locking using hashes in tasks updates
     * @return quantity of updated records
     */
    public int assignTask(int taskID, int employeeID, long hash);
}
