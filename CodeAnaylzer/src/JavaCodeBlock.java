import java.util.ArrayList;

@SuppressWarnings("serial")
public class JavaCodeBlock extends ArrayList<JavaCodeBlock> {

	private String text = "";
	private String modifier = "";
	private String acl = "public";
	private String type;

	/**
	 * Prohibited Constructor without value
	 */
	@SuppressWarnings("unused")
	private JavaCodeBlock() {
	}

	/**
	 * Constructor for our Java Code Block based on an ArrayList Structure
	 * 
	 * @param text - text/ name description of this block
	 * @param type - type of block (String or Structure of Block)
	 */
	public JavaCodeBlock(String text, String type) {
		super(0); // create the real array structure with size 0
		this.text = text;
		this.type = type;
	}
	
	/**
	 * @param the
	 *            text to set
	 */
	public String getText(String text) {
		return this.text;
	}

	public boolean isBlock() {
		return true;
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

}
