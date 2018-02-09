package rpn.core;


import rpn.util.Stack;

import java.math.BigDecimal;

/**
 * The base unit of each calculate
 *
 * @author tracy.
 * @create 2018-02-08 20:03
 **/
public class CalculateUnit {
    /**
     * current calculate stack
     */
    private Stack<BigDecimal> stack;

    /**
     * record the number which has pop out
     */
    private Stack<BigDecimal> backStack;

    /**
     * record the numbers of each operation has pop out
     */
    private Stack<Integer> popNumbers;

    /**
     * save the element current calculate needed
     */
    private BigDecimal[] elementNeed;


    public Stack<BigDecimal> getStack() {
        return stack;
    }

    public void setStack(Stack<BigDecimal> stack) {
        this.stack = stack;
    }

    public Stack<BigDecimal> getBackStack() {
        return backStack;
    }

    public void setBackStack(Stack<BigDecimal> backStack) {
        this.backStack = backStack;
    }

    public Stack<Integer> getPopNumbers() {
        return popNumbers;
    }

    public void setPopNumbers(Stack<Integer> popNumbers) {
        this.popNumbers = popNumbers;
    }

    public BigDecimal[] getElementNeed() {
        return elementNeed;
    }

    public void setElementNeed(BigDecimal[] elementNeed) {
        this.elementNeed = elementNeed;
    }
}
