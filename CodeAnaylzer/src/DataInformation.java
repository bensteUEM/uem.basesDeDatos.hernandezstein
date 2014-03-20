/**
 * This class can be used to describe one programming element used in any sourcecode part
 * @author benste
 *
 */
public class DataInformation {
	private String name;
	private String datatype;
	// private String initValue; //additional Function not needed in rev 1
	
	// optional ones for non Variable Types only
	private String returnValue;
	private String parameters;
	
	// GET and SET methods
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
}
