/**
 * Tmore than one function
 * @author benste
 *
 */
public class T07aNested {
	
	public String openvar, openvar2;
	private String closedvar, closevar2;
	static String staticvar, statcvar2;
	public static final String CONST1 = "one";
	private static final String CONST2 = "two";
	
	public void nameOfFunction(Integer arg0){
		System.out.println("something happens here but no variable");
		String shouldAppear = "I'm a 1st local String in a Method with more than one line";
		Integer shouldAppear2 = 73;
	}
	private void nameOfAnotherFunction(String anotherParameter, String anotherparam2){
		System.out.println("something happens here but no variable");
		String shouldAppear = "I'm a 2nd local String in a Method with more than one line";
		Integer shouldAppear2 = 99;
	}
	private class NestClass {
		private Integer nestedClassvar = 1;
		public void functionInNest (){
			String theNest = "test";
		}
	}
}
