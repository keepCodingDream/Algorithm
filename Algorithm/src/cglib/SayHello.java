package cglib;

/**
 * @author tracy
 *
 */
public class SayHello {
	public void say() {
		System.out.println("hello world!");
	}

	@MyAnnotation(name = "admin")
	public void amdinSay() {
		System.out.println("This is admin say!");
	}
}
