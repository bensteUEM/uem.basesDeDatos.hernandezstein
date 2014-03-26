/**
 * Function with multiline content within its block
 * @author benste
 *
 */
public class T05dMethod {
	private String testforScope;
	public void nameOfFunction(Integer arg0){
		String shouldAppear = "I'm a local String in a Method with more than one line";
		testforScope = "check if it is detected twice";
	}
}
