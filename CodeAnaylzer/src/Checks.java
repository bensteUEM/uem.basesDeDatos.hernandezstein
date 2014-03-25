/**
 * This class should provide helping functions to check if a String startswith a
 * group of structure elements
 * 
 * @author steinb
 * 
 */
public class Checks {
	/**
	 * Checks if the text starts with a characters which are known to be a
	 * JavaKeyword
	 * 
	 * @return
	 */
	public static boolean startswithKeyword(String text) {
		return startswithACL(text) || startswithPrimitiveDatatype(text)
				|| startswithHighLevelKey(text) || startswithClassMod(text)
				|| startswithFunctionMod(text)
				|| startswithControlStructure(text) || startswithModifier(text)
				|| startswithOtherSimple(text) || startswithSpecial(text)
				|| startswithExcluded(text);
	}

	/**
	 * Checks if the text starts with a Java Access modifier
	 * 
	 * @param text
	 * @return
	 */
	public static boolean startswithACL(String text) {
		boolean result = false;
		result = result || text.startsWith("public");
		result = result || text.startsWith("protected");
		result = result || text.startsWith("private");
		return result;
	}

	/**
	 * Checks if the text starts with a primitive datatype
	 * 
	 * @param text
	 * @return
	 */
	public static boolean startswithPrimitiveDatatype(String text) {
		boolean result = false;
		result = result || text.startsWith("boolean");
		result = result || text.startsWith("byte");
		result = result || text.startsWith("char");
		result = result || text.startsWith("int");
		result = result || text.startsWith("long");
		result = result || text.startsWith("boolean");
		result = result || text.startsWith("float");
		result = result || text.startsWith("double");
		result = result || text.startsWith("void");
		return result;
	}

	/**
	 * Checks if the text start with a highlevel keyword, one that is used
	 * during or before class definition
	 * 
	 * @param text
	 * @return
	 */
	public static boolean startswithHighLevelKey(String text) {
		boolean result = false;
		result = result || text.startsWith("abstract");
		result = result || text.startsWith("class");
		result = result || text.startsWith("interface");
		result = result || text.startsWith("strictfp");
		result = result || text.startsWith("import");
		result = result || text.startsWith("package");
		return result;
	}

	/**
	 * checks if the text starts with a Class level modifier
	 * 
	 * @param text
	 * @return
	 */
	public static boolean startswithClassMod(String text) {
		boolean result = false;
		result = result || text.startsWith("extends");
		return result;
	}

	/**
	 * checks if the text starts with a function/method level modifier
	 * 
	 * @param text
	 * @return
	 */
	public static boolean startswithFunctionMod(String text) {
		boolean result = false;
		result = result || text.startsWith("throws");
		return result;
	}

	/**
	 * Checks if the text starts with a Control Structure
	 * 
	 * @param text
	 * @return
	 */
	public static boolean startswithControlStructure(String text) {
		boolean result = false;
		result = result || text.startsWith("if");
		result = result || text.startsWith("else");
		result = result || text.startsWith("for");
		result = result || text.startsWith("while");
		result = result || text.startsWith("do");
		result = result || text.startsWith("switch");
		result = result || text.startsWith("case");
		result = result || text.startsWith("default");
		result = result || text.startsWith("try");
		result = result || text.startsWith("catch");
		result = result || text.startsWith("assert");
		return result;
	}

	/**
	 * Checks for general modifiers which are used either before variables,
	 * classes or functions
	 * 
	 * @param text
	 * @return
	 */
	public static boolean startswithModifier(String text) {
		boolean result = false;
		result = result || text.startsWith("final");
		result = result || text.startsWith("static");
		result = result || text.startsWith("volatile");
		result = result || text.startsWith("transient");
		result = result || text.startsWith("synchronized");
		return result;
	}

	/**
	 * Checks if the text starts with a modifier which does not require special
	 * treatment
	 * 
	 * @param text
	 * @return
	 */
	public static boolean startswithOtherSimple(String text) {
		boolean result = false;
		result = result || text.startsWith("continue");
		result = result || text.startsWith("break");
		result = result || text.startsWith("throw");
		result = result || text.startsWith("return");
		return result;
	}

	/**
	 * starts with a special item usually related to variable references
	 * 
	 * @param text
	 * @return
	 */
	public static boolean startswithSpecial(String text) {
		boolean result = false;
		result = result || text.startsWith("this");
		result = result || text.startsWith("super");
		result = result || text.startsWith("new");
		return result;
	}

	/**
	 * Indicates that the whole method can be ignored for analysis
	 * 
	 * @param text
	 * @return
	 */
	public static boolean startswithExcluded(String text) {
		boolean result = false;
		result = result || text.startsWith("native");
		return result;
	}

}
