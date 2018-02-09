package rpn.operation;


import rpn.core.CalculateUnit;

import java.math.BigDecimal;

/**
 * base operator define
 *
 * @author tracy.
 * @create 2018-02-08 18:40
 **/
public interface Operator {

    /**
     * define all the subclasses operation action
     *
     * @param calculateUnit current calculate context
     * @return calculate result
     * @throws IllegalArgumentException if input illegal
     */
    BigDecimal execute(CalculateUnit calculateUnit) throws IllegalArgumentException;
}
