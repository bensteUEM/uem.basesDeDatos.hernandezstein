/**
 * Tmore than one function
 * @author benste
 *
 */
public class T05gMethod {
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
