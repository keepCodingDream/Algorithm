package rpn.operation;


import rpn.core.CalculateUnit;
import rpn.core.Operation;
import rpn.util.Stack;

import java.math.BigDecimal;

/**
 * The implement of the addition function
 *
 * @author tracy.
 * @create 2018-02-08 18:44
 **/
@Operation("+")
public class AdditionOperator extends BaseOperator {

    public AdditionOperator() {
        super(2);
    }

    @Override
    public BigDecimal execute(CalculateUnit calculateUnit) throws IllegalArgumentException {
        Stack<BigDecimal> stack = calculateUnit.getStack();
        BigDecimal firstNum = stack.pop();
        BigDecimal secondNum = stack.pop();
        return firstNum.add(secondNum);
    }
}
