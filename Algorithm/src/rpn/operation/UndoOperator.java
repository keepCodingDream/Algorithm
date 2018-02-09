package rpn.operation;


import rpn.core.CalculateUnit;
import rpn.core.Operation;
import rpn.util.Stack;

import java.math.BigDecimal;

/**
 * The implement of the subtraction function
 * <p>
 * <p>
 * Undo operation means undo prev operation.If no calculate op just undo the input.
 *
 * @author tracy.
 * @create 2018-02-08 18:44
 **/
@Operation("undo")
public class UndoOperator extends BaseOperator {

    public UndoOperator() {
        super(0);
    }

    @Override
    public BigDecimal execute(CalculateUnit calculateUnit) throws IllegalArgumentException {
        Stack<BigDecimal> backStack = calculateUnit.getBackStack();
        Stack<BigDecimal> stack = calculateUnit.getStack();
        Stack<Integer> popNumbers = calculateUnit.getPopNumbers();
        //if nothing happened.Just pop the input
        if (backStack.isEmpty()) {
            stack.pop();
        } else {
            //ignore latest operation value
            stack.pop();
            Integer latestOpValue = popNumbers.pop();
            BigDecimal[] record = new BigDecimal[latestOpValue];
            int index = 0;
            for (int i = 0; i < latestOpValue; i++) {
                record[index++] = backStack.pop();
            }
            for (int i = index - 1; i >= 0; i--) {
                stack.push(record[i]);
            }
        }
        return null;
    }
}
