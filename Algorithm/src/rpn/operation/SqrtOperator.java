package rpn.operation;


import rpn.core.CalculateUnit;
import rpn.core.Operation;
import rpn.util.Stack;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * The implement of the sqrt function.
 *
 * @author tracy.
 * @create 2018-02-08 18:44
 **/
@Operation("sqrt")
public class SqrtOperator extends BaseOperator {

    private static final BigDecimal CONSTANT_2 = BigDecimal.valueOf(2.0);
    private static final int PRECISION = 20;

    public SqrtOperator() {
        super(1);
    }

    @Override
    public BigDecimal execute(CalculateUnit calculateUnit) throws IllegalArgumentException {
        Stack<BigDecimal> stack = calculateUnit.getStack();
        BigDecimal item = stack.pop();
        if (item.signum() < 0) {
            throw new IllegalArgumentException("sqrt can not use less than 0 input");
        }
        MathContext mc = new MathContext(PRECISION, RoundingMode.HALF_UP);
        int cnt = 0;
        BigDecimal x = item;
        while (cnt < PRECISION) {
            x = (x.add(item.divide(x, mc))).divide(CONSTANT_2, mc);
            cnt++;
        }
        return x;
    }
}
