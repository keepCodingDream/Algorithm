package rpn.core;

import java.lang.annotation.*;

/**
 * use for determine a class is a operation related service
 *
 * @author tracy.
 * @create 2018-02-08 18:31
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Operation {
    /**
     * Tell the context which kind of operation this instance will provided
     */
    String value();
}
