package dynamicProxies;

import java.lang.reflect.Proxy;

/**
 * @author tracy
 *
 */
public class ProxyTest {
  public static void main(String[] args) {
    ProxyImpl proxyImpl = new ProxyImpl();
    ProxyInterface proxy = (ProxyInterface) Proxy.newProxyInstance(ProxyInterface.class.getClassLoader(),
        new Class[] { ProxyInterface.class }, new ProxyHandler(proxyImpl));
    proxy.doSomething("Hello test dynaic proxy");
  }
}
