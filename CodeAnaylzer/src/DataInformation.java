import javax.lang.model.element.Element;

/**
 * This class can be used to describe one programming element used in any
 * sourcecode part
 * 
 * @author benste
 * 
 */
public class DataInformation {
	private String name;
	private String scope;
	private String datatype;
	// private String initValue; //additional Function not needed in rev 1

	// optional ones for non Variable Types only
	private String returnValue;
	private String parameters;
	public Element sourceElement;

	// GET and SET methods

	/**
	 * @return the scope
	 */
	public String getScope() {
		return scope;
	}

	/**
	 * @param scope
	 *            the scope to set
	 */
	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDatatype() {
		return datatype;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

	public String getReturnValue() {
		return returnValue;
	}

	public void setReturnValue(String returnValue) {
		this.returnValue = returnValue;
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	public String toString(String tabs) {
		String text = "";
		text += ("\n" + tabs + "Element is called: " + this.getName());
		text += ("\n" + tabs + "Kind of Element is: " + this.getDatatype());
		text += ("\n" + tabs + "Assumed Scope of Element is: " + this.getScope());
		text += ("\n" + tabs + "Modifiers are: " + this.getParameters());
		return text;
	}
}
