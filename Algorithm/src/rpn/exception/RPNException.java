package rpn.exception;

/**
 * The base exception for this function
 *
 * @author tracy.
 * @create 2018-02-06 19:29
 **/
public class RPNException extends RuntimeException {
    public RPNException() {
        super();
    }

    public RPNException(String errMsg) {
        super(errMsg);
    }
}
