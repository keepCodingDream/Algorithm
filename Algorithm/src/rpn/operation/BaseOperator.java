package rpn.operation;


import rpn.core.CalculateUnit;
import rpn.util.Stack;

import java.math.BigDecimal;

/**
 * Determine all the basic operation actions
 *
 * @author tracy.
 * @create 2018-02-08 19:51
 **/
public abstract class BaseOperator implements Operator {

    /**
     * How many element required of thBaseOperatoris operation
     */
    private int operationNum;
    private static final BigDecimal[] EMPTY_ARRAY = {};

    public BaseOperator(int operationNum) {
        this.operationNum = operationNum;
    }

    /**
     * The action real do.
     *
     * @param calculateUnit Basic Calculate Unit Context
     * @return Calculate result
     */
    public BigDecimal doCalculate(CalculateUnit calculateUnit) {
        preAction(calculateUnit);
        BigDecimal result;
        try {
            result = execute(calculateUnit);
        } catch (Throwable e) {
            rollBack(calculateUnit);
            throw e;
        }
        postAction(calculateUnit);
        return result;
    }

    /**
     * Just record the elements will pop and calculate
     *
     * @param calculateUnit Basic Calculate Unit Context
     */
    protected void preAction(CalculateUnit calculateUnit) {
        if (operationNum > 0) {
            Stack<BigDecimal> stack = calculateUnit.getStack();
            if (operationNum > stack.size()) {
                //wrong action also add the optTimes.
                //This error course of do not have enough elements.So add current numbers it has and plus 1(the fail operation)
                stack.setOptTimes(stack.getOptTimes() + stack.size() + 1);
                throw new IllegalArgumentException("operation failed!Do not have enough element");
            }
            calculateUnit.setElementNeed(stack.getTopElement(operationNum, BigDecimal[].class));
        } else {
            calculateUnit.setElementNeed(EMPTY_ARRAY);
        }
    }


    /**
     * When action finish successfully.Save record!
     *
     * @param calculateUnit Basic Calculate Unit Context
     */
    protected void postAction(CalculateUnit calculateUnit) {
        if (operationNum > 0) {
            saveOperation(calculateUnit, calculateUnit.getElementNeed());
        }
    }

    /**
     * when execute function failed.Just roll back the stack
     *
     * @param calculateUnit CalculateUnit
     */
    protected void rollBack(CalculateUnit calculateUnit) {
        BigDecimal[] elementNeeds = calculateUnit.getElementNeed();
        if (elementNeeds != null && elementNeeds.length > 0) {
            Stack<BigDecimal> stack = calculateUnit.getStack();
            if (stack != null) {
                for (BigDecimal item : elementNeeds) {
                    stack.pushWithNoOp(item);
                }
                //clean cache
                calculateUnit.setElementNeed(null);
            }
        }
    }

    /**
     * save all the values current operation used
     *
     * @param calculateUnit Basic Calculate Unit Context
     * @param values        all the current element used
     */
    private void saveOperation(CalculateUnit calculateUnit, BigDecimal... values) {
        if (values.length > 0) {
            Stack<Integer> popNumbers = calculateUnit.getPopNumbers();
            Stack<BigDecimal> backStack = calculateUnit.getBackStack();
            for (BigDecimal item : values) {
                backStack.push(item);
            }
            //note each action how many elements used
            popNumbers.push(values.length);
        }
    }
}
