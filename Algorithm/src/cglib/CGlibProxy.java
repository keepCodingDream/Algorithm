package cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.sf.cglib.proxy.NoOp;

/**
 * @author tracy
 *
 */
public class CGlibProxy implements MethodInterceptor {

	private Enhancer enhancer = new Enhancer();

	@SuppressWarnings("unchecked")
	public <T> T getProxyInstance(Class<T> target) {
		enhancer.setSuperclass(target);
		/**
		 * Set the different callback instance. When CallbackFilter return 1
		 * will execute the index 1 of Callback[]……
		 * <p>
		 * actually the method will execute any index of Callback[]'s instance
		 * whatever AdminSayCallBackFilter returned
		 * <p>
		 * 'NoOp.INSTANCE' means do noting
		 */
		enhancer.setCallbacks(new Callback[] { this, NoOp.INSTANCE });
		enhancer.setCallbackFilter(new AdminSayCallBackFilter());
		return (T) enhancer.create();
	}

	@Override
	public Object intercept(Object object, Method method, Object[] params,
			MethodProxy proxy) throws Throwable {
		System.out.println("call method name:" + method.getName());
		Object result = proxy.invokeSuper(object, params);
		System.out.println("Invoke done");
		return result;
	}

}
