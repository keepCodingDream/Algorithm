package rpn.operation;


import rpn.core.CalculateUnit;
import rpn.core.Operation;
import rpn.util.Stack;

import java.math.BigDecimal;

/**
 * The implement of the division function
 *
 * @author tracy.
 * @create 2018-02-08 18:44
 **/
@Operation("/")
public class DivisionOperator extends BaseOperator {

    public DivisionOperator() {
        super(2);
    }

    @Override
    public BigDecimal execute(CalculateUnit calculateUnit) throws IllegalArgumentException {

        Stack<BigDecimal> stack = calculateUnit.getStack();
        BigDecimal firstNum = stack.pop();
        BigDecimal secondNum = stack.pop();
        //BigDecimal's divide can not deal any divisor is null.So if the first input is 0, just return.
        if (secondNum.signum() == 0) {
            return secondNum;
        }
        if (firstNum.signum() == 0) {
            throw new IllegalArgumentException("The divisor can not be zero!");
        }
        //15 decimal places of precision.
        return secondNum.divide(firstNum, 15, BigDecimal.ROUND_DOWN);

    }
}
