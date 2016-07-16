package cglib;

public class CGlibMain {
	public static void main(String[] args) {
		CGlibProxy proxy = new CGlibProxy();
		SayHello sayHello = proxy.getProxyInstance(SayHello.class);
		sayHello.say();
		sayHello.amdinSay();
	}
}
