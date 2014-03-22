/**
 * This class should provide helping functions to check if a String startswith a group of structure elements
 * @author steinb
 *
 */
public class Checks{
	/**
	 * Checks if the text starts with a Java Access modifier
	 * @param text
	 * @return
	 */
	public boolean startswithACL(String text){
		boolean result = false ;
		result = result || text.startsWith("public");
		result = result || text.startsWith("protected");
		result = result || text.startsWith("private");
		return result;
	}
	
	public boolean startswithPrimitiveDatatype(String text){
		boolean result = false ;
		result = result || text.startsWith("boolean");
		result = result || text.startsWith("byte");
		result = result || text.startsWith("char");
		result = result || text.startsWith("int");
		result = result || text.startsWith("long");
		result = result || text.startsWith("boolean");
		result = result || text.startsWith("float");
		result = result || text.startsWith("double");
		result = result || text.startsWith("void");
		return result
	}
	

}
