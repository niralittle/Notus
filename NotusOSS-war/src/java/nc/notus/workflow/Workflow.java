package nc.notus.workflow;

/**
 * This interface generalizes behaviour of workflows. It provides functionality
 * to proceed Orders through workflows.
 * @author Igor Litvinenko
 */
public interface Workflow {

    /**
     * This method is used to start Order execution process
     */
    void proceedOrder();
}
