import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class reads a .JAVA file and splits the source code into different logical blocks
 * @author benste, davidh
 *
 */
public class TextSplitter {
	
	private File file;
	private FileReader fr;
	private BufferedReader br;
	
	/**
	 * Constructor to create a new Instance from a File
	 * @param inputfile
	 * @return ArrayList of Objects (either String or ArrayList<Object> )
	 */
	public TextSplitter(String pathToSourceCode){
		// this.file = ...fileSourceCode = 
		// TODO
		this.file = new File(pathToSourceCode);
	}
	
	/**
	 * This method is used to open a .JAVA file.
	 * @return true if the file was successfully opened
	 * false in other cases.
	 */
	public boolean openFile(){
		try{
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			return true;
		}
		catch(IOException e){
			return false;
		}
	} // End of openFile() method

	/**
	 * This method is used to open and read all the
	 * content inside a .JAVA file.
	 * @return A String containing all the code.
	 */
	public String readFile(){

		String fileContent = "";

		try{
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append(System.lineSeparator());
	            line = br.readLine();
	        }
	        fileContent = sb.toString();
	        return fileContent;
	        
	    }
		catch (IOException e){
			e.printStackTrace();
			return fileContent;
		}
	} // End of the readFile() method
	
	/**
	 * This method is used to close a .JAVA file.
	 * @return true if the file was successfully closed
	 * false in other cases.
	 */
	public boolean closeFile(){
		try{
			br.close();
			fr.close();
			return true;
		}
		catch(IOException e){
			return false;
		}
	} // End of closeFile method
	
	/**
	 * This method counts the number of curly brackets used in the file
	 * //TODO - iterate through File and
	 * @return
	 */
	public boolean curlyCountLeveled(){
		// Count 
		
		int curlyCounter = 0;
		int quoteCounter = 0;
		String s1 = "Hello";
		s1.charAt(1);
		
		for(int i=0; i<readFile().length() ; i++){
			if(readFile().charAt(i) == '{'){
				curlyCounter++;
			}
			if(readFile().charAt(i) == '}'){
				curlyCounter--;
			}
		}
		if(curlyCounter == 0){
			return true;
		} else {
			return false;
		}
		
	}
	



}
