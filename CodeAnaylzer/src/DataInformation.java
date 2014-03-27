import javax.lang.model.element.Element;

/**
 * This class can be used to describe one programming element used in any
 * sourcecode part
 * 
 * @author benste
 * 
 */
public class DataInformation {
	private String name = "";
	private String scope = "";
	private String datatype = "";
	// private String initValue; //additional Function not needed in rev 1

	// optional ones for non Variable Types only
	private String returnValue = "";
	private String parameters = "";
	public Element sourceElement = null;
	private String modifier= "";

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

	public String getReturnType() {
		return returnValue;
	}

	public void setReturnType(String returnValue) {
		this.returnValue = returnValue;
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}
	
	public String getModifiers() {
		return this.modifier;
	}
	
	public void setModifiers(String modifier) {
		this.modifier = modifier;
	}

	public String toString(String tabs) {
		String text = "";
		text += ("\n" + tabs + "Element is called \t" + this.getName());
		text += ("\n" + tabs + "Kind of Element is: \t" + this.getDatatype());
		text += ("\n" + tabs + "Scope of Element is: \t" + this.getScope());
		text += ("\n" + tabs + "Modifiers are: \t\t" + this.getModifiers());
		text += ("\n" + tabs + "Return Value is: \t" + this.getReturnType());
		text += ("\n" + tabs + "Parameters are: \t" + this.getParameters());
		
		return text;
	}


}
