public class JavaCodeString implements JavaStructure {
	private String text = "";

	/**
	 * Prohibited Constructor without value
	 */
	@SuppressWarnings("unused")
	private JavaCodeString() {
	}

	/**
	 * Constructor for our own String class
	 * 
	 * @param text
	 */
	public JavaCodeString(String text) {
		this.text = text;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * simple string parsing
	 */
	public String toString() {
		return this.text;
	} // End toString()

	@Override
	public boolean isBlock() {
		// TODO Auto-generated method stub
		return false;
	}
}
