package rpn.util;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * my self stack implement
 *
 * @author tracy.
 * @create 2018-02-06 19:54
 **/
public class Stack<T> {
    private static int CAPACITY = 999999;

    private int top = -1;
    private int optTimes = 0;
    private Object[] objs;

    public Stack() {
        this(CAPACITY);
    }

    public Stack(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Illegal capacity:" + capacity);
        }
        CAPACITY = capacity;
        objs = new Object[capacity];
    }

    /**
     * push a obj into the stack
     *
     * @param obj input value
     */
    public void push(T obj) {
        optTimes++;
        if (top == objs.length - 1) {
            throw new IllegalArgumentException("Stack is full!");
        }
        objs[++top] = obj;
    }

    /**
     * push a obj into the stack
     * <p>
     * optTimes not added
     *
     * @param obj input value
     */
    public void pushWithNoOp(T obj) {
        if (top == objs.length - 1) {
            throw new IllegalArgumentException("Stack is full!");
        }
        objs[++top] = obj;
    }

    /**
     * pop a value
     * <p>
     * when pop a value. Put it into a backObjs list
     *
     * @return the top value of this stack
     */
    public T pop() {
        optTimes++;
        if (top == -1) {
            throw new IllegalArgumentException("Stack is empty!");
        }
        Object obj = objs[top--];
        return (T) obj;
    }

    /**
     * Get the top N elements(not pop them)
     *
     * @param numbers how many elements
     * @return all elements array
     */
    public T[] getTopElement(int numbers, Class<? extends T[]> newClass) {
        if (top < numbers - 1) {
            throw new IllegalArgumentException("Stack element not enough!");
        }
        return Arrays.copyOfRange(objs, top + 1 - numbers, top + 1, newClass);
    }

    /**
     * clear current stack
     */
    public void clear() {
        optTimes = 0;
        top = -1;
        objs = new Object[CAPACITY];
    }

    public int getOptTimes() {
        return optTimes;
    }

    public void setOptTimes(int optTimes) {
        this.optTimes = optTimes;
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public int size() {
        return top + 1;
    }

    /**
     * return current stack values
     *
     * @return current stack values as space split
     */
    public String display() {
        StringBuilder builder = new StringBuilder("stack: ");
        for (int i = 0; i <= top; i++) {
            builder.append(NumberUtils.formatDouble((BigDecimal) objs[i], 10));
            builder.append(" ");
        }
        return builder.toString().trim();
    }
}
