import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.lang.model.element.Element;

/**
 * This class can be used to describe one programming element used in any
 * sourcecode part
 * 
 * @author benste
 * 
 */
public class DataInformation implements Serializable {

	private static final long serialVersionUID = 6860038564004371640L;

	private final static Logger LOG = Logger.getLogger(DataInformation.class
			.getName());

	private String name = "";
	private String scope = "";
	private String datatype = "";
	// private String initValue; //additional Function not needed in rev 1

	// optional ones for non Variable Types only
	private String returnValue = "";
	private String parameters = "";
	public Element sourceElement = null;
	private String modifier = "";

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

	/**
	 * Default toString method compying with Project Specification § category of
	 * programming element, data type, scope, return value if applicable
	 * 
	 * @author bensteUEM
	 */
	public String toString(){
		return (this.getName() + "\t\t"+this.getDatatype()+ "\t\t"+this.getScope()+ "\t\t"+this.getReturnType()+ "\t\t"+this.getParameters());
	}

	/**
	 * Abstract method to get the generic KIND of object safed
	 * 
	 * @return CLASS, METHOD or VARIABLE
	 */
	public String getKind() {
		LOG.entering("DataInformation", "getKind");
		if (this.datatype.contains("CLASS")) {
			return "CLASS";
		} else if (this.datatype.contains("METHOD")) {
			return "METHOD";
		} else if (this.datatype.contains("VARIABLE")
				|| (this.datatype.contains("Field"))
				|| (this.datatype.contains("Parameter"))) {
			return "VARIABLE";
		}
		LOG.warning("Datatype can not be associated with any generic KIND");
		return "";
	}
	
	public static String[] getTableHeaders(){
		String[] columnValues = { "Name", "Datatype",
				"Scope", "ReturnType" , "Parameters" };
		return columnValues;
	}
	
	public Object[] toTableRow(){
		ArrayList<String> values = new ArrayList<String>(0);
		values.add(this.getName());
		values.add(this.getDatatype());
		values.add(this.getScope());
		values.add(this.getReturnType());
		values.add(this.getParameters());
		return values.toArray();
	}
}
