/**
 * This class has one function with a VOID return value and an Integer arg0 as a paramenter
 * The scope of the parameter is only within this function
 * @author benste
 *
 */
public class T05eMethod {
	public void nameOfFunction(Integer arg0){
		System.out.println("something happens here but no variable");
		String shouldAppear = "I'm a local String in a Method with more than one line";
		Integer shouldAppear2 = "73";
	}
}
