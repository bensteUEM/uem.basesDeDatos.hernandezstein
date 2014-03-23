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
		
		System.out.println(curlyBraceBalance());
		//String sss = "''";
		
	}
	
	public static boolean curlyBraceBalance(){
		// Opens & reads the file
		TextSplitter ts = new TextSplitter("C:/Users/David/Desktop/MainCl2.txt");
		ts.openFile();
		String content = ts.readFile();
		
		// Needed variables
		int level = 0;
		boolean insideLineComment = false;
		boolean insideMultilineComment = false;
		boolean insideQuotes = false;
		boolean insideSQuotes = false;
		
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
			
			if(content.charAt(i) == '"' && insideLineComment==false && insideMultilineComment==false){
				if(insideQuotes == false){
					System.out.println("Inside Quotes");
					insideQuotes = true;
				} else {
					System.out.println("Outside Quotes");
					insideQuotes = false;
				}
			}
			
			if(String.valueOf(content.charAt(i)).equals("'") && insideLineComment==false 
					&& insideMultilineComment==false && insideQuotes==false){
				if(insideSQuotes == false){
					System.out.println("Inside Simple Quotes");
					insideSQuotes = true;
				} else {
					System.out.println("Outside Simple Quotes");
					insideSQuotes = false;
				}
			}
			
			if(String.valueOf(content.charAt(i)).equals("*") && String.valueOf(content.charAt(i+1)).equals("/") ){
				System.out.println("End of comment");
				insideMultilineComment = false;
			}
			
			if(content.charAt(i) == '{' && insideLineComment==false && insideMultilineComment==false  
					&& insideQuotes==false && insideSQuotes==false){
				level++;
			}
			if(content.charAt(i) == '}' && insideLineComment==false && insideMultilineComment==false 
					&& insideQuotes==false && insideSQuotes==false){
				level--;
			}
			
		}
		
		if(level == 0){
			ts.closeFile();
			return true;
		} else {
			ts.closeFile();
			return false;
		}
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