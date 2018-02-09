package rpn.core;

/**
 * This class package the base operation for calculate.
 * If some other user want to enhance the calculate's function.Just extend this class.(The method and constants are all 'protected')
 *
 * @author tracy.
 * @create 2018-02-06 19:13
 **/
@Deprecated
public class BaseCalculateOperation {
    protected static final String OP_ADDITION = "+";
    protected static final String OP_SUBTRACTION = "-";
    protected static final String OP_MULTIPLICATION = "*";
    protected static final String OP_DIVISION = "/";
    protected static final String OP_SQRT = "sqrt";
    protected static final String OP_UNDO = "undo";
    protected static final String OP_CLEAR = "clear";

    /**
     * addition operation
     *
     * @param a first params
     * @param b second params
     * @return calculate result
     */
    public double addition(double a, double b) {
        return a + b;
    }

    /**
     * subtraction operation
     *
     * @param a first params
     * @param b second params
     * @return calculate result
     */
    public double subtraction(double a, double b) {
        return a - b;
    }

    /**
     * multiplication operation
     *
     * @param a first params
     * @param b second params
     * @return calculate result
     */
    public double multiplication(double a, double b) {
        return a * b;
    }

    /**
     * division operation
     *
     * @param a first params
     * @param b second params
     * @return calculate result
     */
    public double division(double a, double b) {
        return a / b;
    }

    /**
     * sqrt operation
     *
     * @param a target number
     * @return calculate result
     * @throws IllegalArgumentException if a<0
     */
    public double sqrt(double a) {
        if (a < 0) {
            throw new IllegalArgumentException("input must be not null!");
        }
        return Math.sqrt(a);
    }

}
