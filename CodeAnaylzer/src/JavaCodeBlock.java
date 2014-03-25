import java.util.ArrayList;

@SuppressWarnings("serial")
public class JavaCodeBlock extends ArrayList<JavaCodeBlock> {

	private String text = ""; // descriptive text, e.g. name of the variable
	private String modifier = ""; // modifying arguments used in this block
	private String acl = "public"; // acl modifier
	private String type; // type of element
	public ArrayList<String> codeRemain = new ArrayList<String>(0);

	// This part of the Code is left over from the Line after finishing a
	// logical structure

	/**
	 * Prohibited Constructor without value
	 */
	@SuppressWarnings("unused")
	private JavaCodeBlock() {
	}

	/**
	 * Constructor for our Java Code Block based on an ArrayList Structure
	 * 
	 * @param text
	 *            - text/ name description of this block
	 * @param type
	 *            - type of block (String or Structure of Block)
	 */
	public JavaCodeBlock(String text, String type) {
		super(0); // create the real array structure with size 0
		this.text = text;
		this.type = type;
		System.out.println("text&type=" + text + "&" + type); // TODO debug
	}

	/**
	 * @param the
	 *            text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return this.text;
	}

	public String getACL() {
		return this.acl;
	}

	public void setACL(String newACL) {
		this.acl = newACL;
	}

	public String getModifier() {
		return this.modifier;
	}

	public void addModifier(String newModifier) {
		this.modifier += " " + newModifier;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param the type
	 */
	public void setType(String type) {
		this.type= type ;
	}

	public String toString() {
		return toString(0);
	}

	public String toString(int level) {
		String tabs = "";
		int i = level;
		while (i > 0) {
			tabs += "\t";
			i--;
		}

		String text = tabs + this.getACL() + " " + this.text + "<" + this.type
				+ ">";

		if (this.size() > 0) {
			text += "[";
			for (JavaCodeBlock item : this.subList(0, this.size())) {
				text += ("\n" + item.toString(level + 1));
			}
			text += ("\n" + tabs + "]");
		}

		return text;
	}

}
