package dynamicProxies;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProxyHandler implements InvocationHandler {
  private Object proxied;

  public ProxyHandler(Object proxied) {
    this.proxied = proxied;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    System.out.println("ProxyHandler.invoke() before");
    Object result = method.invoke(proxied, args);
    System.out.println("ProxyHandler.invoke() after");
    return result;
  }

}
