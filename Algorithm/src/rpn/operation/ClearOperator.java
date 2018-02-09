package rpn.operation;


import rpn.core.CalculateUnit;
import rpn.core.Operation;
import rpn.util.Stack;

import java.math.BigDecimal;

/**
 * The implement of the subtraction function
 *
 * @author tracy.
 * @create 2018-02-08 18:44
 **/
@Operation("clear")
public class ClearOperator extends BaseOperator {

    public ClearOperator() {
        super(0);
    }

    @Override
    public BigDecimal execute(CalculateUnit calculateUnit) throws IllegalArgumentException {
        Stack<BigDecimal> stack = calculateUnit.getStack();
        stack.clear();
        calculateUnit.setBackStack(new Stack<>());
        calculateUnit.setPopNumbers(new Stack<>());
        return null;
    }
}
