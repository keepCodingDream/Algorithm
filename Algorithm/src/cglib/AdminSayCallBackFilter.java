package cglib;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import net.sf.cglib.proxy.CallbackFilter;

/**
 * @author tracy
 *         <p>
 *         If the method entered contains the annotation "MyAnnotation" will do
 *         the select with the value of name
 *
 */
public class AdminSayCallBackFilter implements CallbackFilter {

	@Override
	public int accept(Method paramMethod) {
		Annotation[] anoAnnotations = paramMethod.getAnnotations();
		for (Annotation item : anoAnnotations) {
			if (item instanceof MyAnnotation) {
				if (((MyAnnotation) item).name().equals("admin")) {
					return 1;
				}
			}
		}
		return 0;
	}

}
