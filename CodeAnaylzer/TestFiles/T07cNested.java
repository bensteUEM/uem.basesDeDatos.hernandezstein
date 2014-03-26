/**
 * Tmore than one function
 * 
 * @author benste
 * 
 */
public class T07cNested {

	public String openvar;

	private class PrivateNestClass {
		private Integer nestedPrivateClassvarPrivate = 1;
		public Integer nestedPrivateClassvarPublic = 1;
	}

	class PublicNestClass {
		private Integer nestedPublicClassvarPrivate = 1;
		public Integer nestedPublicClassvarPublic = 1;
	}

	public void main(String[] args) {
		openvar = "nestedPublicClassvarPrivate";
	}
}
