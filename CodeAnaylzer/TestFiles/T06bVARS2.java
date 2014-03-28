/**
 * Tmore than one function
 * @author benste
 *
 */
public class T06bVARS2 {
	
	public String openvar, openvar2;
	private String closedvar, closevar2;
	static String staticvar, statcvar2;
	public static final String CONST1, CONST1b;
	private static final String CONST2, CONST2b;
	
	public void nameOfFunction(Integer arg0){
		System.out.println("something happens here but no variable");
		String shouldAppear = "I'm a 1st local String in a Method with more than one line";
		Integer shouldAppear2 = 73;
	}
	private void nameOfAnotherFunction(String anotherParameter){
		System.out.println("something happens here but no variable");
		String shouldAppear = "I'm a 2nd local String in a Method with more than one line";
		Integer shouldAppear2 = 99;
	}
}
