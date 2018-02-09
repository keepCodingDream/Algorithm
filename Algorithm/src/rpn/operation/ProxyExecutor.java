package rpn.operation;


import org.reflections.Reflections;
import rpn.core.CalculateUnit;
import rpn.core.Operation;
import rpn.util.Stack;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author tracy.
 * @create 2018-02-08 21:24
 **/
public class ProxyExecutor {

    private static Map<String, BaseOperator> operatorMap;

    private static final String PACKAGE_NAME = "com.tracy.rpn.operation";

    private static final String UNKNOWN_OPT_ERR_MSG = "operator %s (position: %d) is unknown operation ";

    static {
        Reflections reflections = new Reflections(PACKAGE_NAME);
        Set<Class<?>> classesList = reflections.getTypesAnnotatedWith(Operation.class);
        operatorMap = new HashMap<>(classesList.size());
        for (Class<?> item : classesList) {
            Operation operation = item.getAnnotation(Operation.class);
            try {
                operatorMap.put(operation.value(), (BaseOperator) item.newInstance());
            } catch (Throwable e) {
                e.printStackTrace();
                // this place can not fail.So just print and shutdown
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Proxy provided to execute calculate work
     *
     * @param operation     operation
     * @param calculateUnit calculate context/Basic unit
     * @return calculate result
     */
    public BigDecimal execute(String operation, CalculateUnit calculateUnit) {
        Stack<BigDecimal> stack = calculateUnit.getStack();
        BaseOperator operator = operatorMap.get(operation);
        if (operator == null) {
            if (stack == null) {
                System.out.println(String.format(UNKNOWN_OPT_ERR_MSG, operation, -1));
            } else {
                System.out.println(String.format(UNKNOWN_OPT_ERR_MSG, operation, stack.getOptTimes()));
            }
            return null;
        }
        return operator.doCalculate(calculateUnit);
    }
}
