package rpn.core;


import rpn.util.NumberUtils;
import rpn.util.Stack;

/**
 * process the input string
 *
 * @author tracy.
 * @create 2018-02-06 19:41
 **/
@Deprecated
public class ProcesserOld {
    private static final String ERR_MSG = "operator %s (position: %d): insucient parameters";
    private static final String UNKNOWN_OPT_ERR_MSG = "operator %s (position: %d) is unknown operation ";
    private BaseCalculateOperation operator;
    /**
     * current calculate stack
     */
    private Stack<Double> stack;

    /**
     * record the number which has pop out
     */
    private Stack<Double> backStack;

    /**
     * record the numbers of each operation has pop out
     */
    private Stack<Integer> popNumbers;

    public ProcesserOld() {
        operator = new BaseCalculateOperation();
        stack = new Stack<Double>();
        backStack = new Stack<Double>();
        popNumbers = new Stack<Integer>();
    }

    public ProcesserOld(BaseCalculateOperation baseCalculateOperation) {
        this.operator = baseCalculateOperation;
        stack = new Stack<Double>();
        backStack = new Stack<Double>();
        popNumbers = new Stack<Integer>();
    }

    /**
     * process the input String and the final result
     *
     * @param inputString input String
     * @return final result
     */
    public Double processInput(String inputString) {
        if (inputString == null) {
            throw new RuntimeException("input must not null!");
        }
        String[] input = inputString.split(" ");
        if (input.length == 0) {
            throw new RuntimeException("input must not empty string!");
        }
        Double result = null;
        for (String item : input) {
            Double firstNum = null;
            Double secondNum = null;
            try {
                if (NumberUtils.isNumeric(item)) {
                    stack.push(Double.valueOf(item));
                } else {
                    //not number must be operation
                    if (item.equals(BaseCalculateOperation.OP_ADDITION)) {
                        firstNum = stack.pop();
                        secondNum = stack.pop();
                        result = operator.addition(secondNum, firstNum);
                        stack.push(result);
                        saveOperation(firstNum, secondNum);
                    } else if (item.equals(BaseCalculateOperation.OP_SUBTRACTION)) {
                        firstNum = stack.pop();
                        secondNum = stack.pop();
                        result = operator.subtraction(secondNum, firstNum);
                        stack.push(result);
                        saveOperation(firstNum, secondNum);
                    } else if (item.equals(BaseCalculateOperation.OP_MULTIPLICATION)) {
                        firstNum = stack.pop();
                        secondNum = stack.pop();
                        result = operator.multiplication(secondNum, firstNum);
                        stack.push(result);
                        saveOperation(firstNum, secondNum);
                    } else if (item.equals(BaseCalculateOperation.OP_DIVISION)) {
                        firstNum = stack.pop();
                        secondNum = stack.pop();
                        result = operator.division(secondNum, firstNum);
                        stack.push(result);
                        saveOperation(firstNum, secondNum);
                    } else if (item.equals(BaseCalculateOperation.OP_SQRT)) {
                        firstNum = stack.pop();
                        result = operator.sqrt(firstNum);
                        stack.push(result);
                        saveOperation(firstNum);
                    } else if (item.equals(BaseCalculateOperation.OP_CLEAR)) {
                        stack.clear();
                        backStack = new Stack<Double>();
                        popNumbers = new Stack<Integer>();
                    } else if (item.equals(BaseCalculateOperation.OP_UNDO)) {
                        //if nothing happened.Just pop the input
                        if (backStack.isEmpty()) {
                            stack.pop();
                            continue;
                        }
                        //ignore latest operation value
                        stack.pop();
                        Integer latestOpValue = popNumbers.pop();
                        for (int i = 0; i < latestOpValue; i++) {
                            stack.push(backStack.pop());
                        }
                    } else if ("".equals(item.trim())) {
                        //just for more than one space
                        //ignore
                    } else {
                        //deal unknown opt
                        System.out.println(String.format(UNKNOWN_OPT_ERR_MSG, item, stack.getOptTimes()));
                        break;
                    }
                }
            } catch (NumberFormatException e1) {
                throw new IllegalArgumentException(e1);
            } catch (IllegalArgumentException e2) {
                //if this error roll back the stack
                System.out.println(String.format(ERR_MSG, item, stack.getOptTimes()));
                if (secondNum != null) {
                    stack.pushWithNoOp(secondNum);
                }
                if (firstNum != null) {
                    stack.pushWithNoOp(firstNum);
                }
                break;
            } catch (Throwable e) {
                System.out.println("Unknown Exception:");
                e.printStackTrace();
            }
        }
        //what ever print current stack
        System.out.println(stack.display());
        if (result != null) {
            //result = NumberUtils.formatDouble(result, 10);
        }
        return result;
    }

    /**
     * save all the values current operation used
     *
     * @param values all operation values
     */
    private void saveOperation(Object... values) {
        for (Object item : values) {
            backStack.push((Double) item);
        }
        popNumbers.push(values.length);
    }
}
