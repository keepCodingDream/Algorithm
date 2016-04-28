package dynamicProxies;

/**
 * @author tracy
 *
 */
public class ProxyImpl implements ProxyInterface {

  @Override
  public void doSomething(String name) {
    System.out.println("Test dynamic proxy.User said:" + name);
  }

}
