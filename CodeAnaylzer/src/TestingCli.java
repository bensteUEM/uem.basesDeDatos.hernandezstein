public class TestingCli {

	/**
	 * @param args Not used in this case
	 * @author tbd
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//CodeAnalyzer program = new CodeAnalyzer();
		
		/*Gui gui = new Gui();
		gui.setVisible(true);*/
		
		System.out.println(deleteComments());
		//String sss = "''";
		//char c2 = '"';
		
	}
	
	public static String deleteComments(){
		// Opens & reads the file
		TextSplitter ts = new TextSplitter("C:/Users/David/Desktop/MainCl2.txt");
		ts.openFile();
		String content = ts.readFile();
		
		// Needed variables
		boolean insideLineComment = false;
		boolean insideMultilineComment = false;
		StringBuilder noComments = new StringBuilder();
		
		// Code processing
		for(int i=0; i<content.length(); i++){ // Detects '/**' comment type
			System.out.print(content.charAt(i) );
			if(String.valueOf(content.charAt(i)).equals("/") && String.valueOf(content.charAt(i+1)).equals("*") 
					&& String.valueOf(content.charAt(i+2)).equals("*") ){
				System.out.println("/** Comment detected");
				insideMultilineComment = true;
			}
			
			if(String.valueOf(content.charAt(i)).equals("/") && String.valueOf(content.charAt(i+1)).equals("*") 
					&& !(String.valueOf(content.charAt(i+2)).equals("*")) ){
				System.out.println("/* Comment detected");
				insideMultilineComment = true;
			}
			
			if(String.valueOf(content.charAt(i)).equals("/") && String.valueOf(content.charAt(i+1)).equals("/") ){
				System.out.println("// Comment detected");
				insideLineComment = true;
			}
			
			if(String.valueOf(content.charAt(i)).equals("\n") &&  insideLineComment==true){
				insideLineComment = false;
			}
			
			if(i>1 && String.valueOf(content.charAt(i-2)).equals("*") && String.valueOf(content.charAt(i-1)).equals("/") ){
				System.out.println("End of comment");
				insideMultilineComment = false;
			}
			
			if(insideLineComment==false && insideMultilineComment==false){
				noComments.append(content.charAt(i));
			}
			
		}

		ts.closeFile();
		return noComments.toString();

	}

}

// DEBUGGING PURPOSE

// New Line detection
/* 		for(int i=0; i<cont.length(); i++){ // Detects new line
			if(String.valueOf(cont.charAt(i)).equals("\n") ){
				System.out.print("a");
			}
		}
 

// Single line comment detection
for(int i=0; i<cont.length(); i++){ // Detects '//' comment type
	if(String.valueOf(cont.charAt(i)).equals("/") && String.valueOf(cont.charAt(i+1)).equals("/") ){
		System.out.print("Comment!");
	}
}

// Multiline comment detection
		for(int i=0; i<cont.length(); i++){ // Detects '/*' comment type
			if(String.valueOf(cont.charAt(i)).equals("/") && String.valueOf(cont.charAt(i+1)).equals("*") 
					&& !(String.valueOf(cont.charAt(i+2)).equals("*")) ){
				System.out.print("/* Comment detected");
			}
		}
// Javadoc detection
 		for(int i=0; i<cont.length(); i++){ // Detects '/**' comment type
			if(String.valueOf(cont.charAt(i)).equals("/") && String.valueOf(cont.charAt(i+1)).equals("*") 
					&& String.valueOf(cont.charAt(i+2)).equals("*") ){
				System.out.print("/** Comment detected");
			}
		}
 

// Quote detection
 		for(int i=0; i<cont.length(); i++){ // Detects quotes
			if( cont.charAt(i) == '"' ){
				System.out.print("Quote detected");
			}
		}
 */