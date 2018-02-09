package rpn.core;


import rpn.operation.ProxyExecutor;
import rpn.util.NumberUtils;
import rpn.util.Stack;

import java.math.BigDecimal;

/**
 * The core processor of this system
 *
 * @author tracy.
 * @create 2018-02-08 21:41
 **/
public class Processor {
    private static final String ERR_MSG = "operator %s (position: %d): insucient parameters";
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
     * Basic calculate unit.Cover stack、backStack、popNumbers and so on
     */
    private CalculateUnit calculateUnit;

    /**
     * The core operation executor
     */
    private ProxyExecutor proxyExecutor;

    public Processor() {
        stack = new Stack<>();
        backStack = new Stack<>();
        popNumbers = new Stack<>();
        calculateUnit = new CalculateUnit();
        calculateUnit.setBackStack(backStack);
        calculateUnit.setStack(stack);
        calculateUnit.setPopNumbers(popNumbers);
        proxyExecutor = new ProxyExecutor();
    }

    /**
     * do the calculate
     *
     * @param inputString input String
     * @return calculate result
     */
    public BigDecimal processInput(String inputString) {
        if (inputString == null) {
            throw new RuntimeException("input must not null!");
        }
        String[] input = inputString.split(" ");
        if (input.length == 0) {
            throw new RuntimeException("input must not empty string!");
        }
        BigDecimal result = null;
        for (String item : input) {
            //deal empty string(more than one space)
            if (item == null || "".equals(item.trim())) {
                continue;
            }
            if (NumberUtils.isNumeric(item)) {
                stack.push(new BigDecimal(item));
            } else {
                try {
                    result = proxyExecutor.execute(item, calculateUnit);
                    //None result will return null
                    if (result != null) {
                        stack.push(result);
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println(String.format(ERR_MSG, item, stack.getOptTimes()));
                    break;
                }
            }
        }
        System.out.println(stack.display());
        return result;
    }
}
