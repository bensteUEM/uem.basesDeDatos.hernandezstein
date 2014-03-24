import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class reads a .JAVA file and splits the source code into different
 * logical blocks
 * 
 * @author benste, davidh
 * 
 */
public class TextSplitter {

	private File file;
	private FileReader fr;
	private BufferedReader br;

	/**
	 * Constructor to create a new Instance from a File
	 * 
	 * @param inputfile
	 * @return ArrayList of Objects (either String or ArrayList<Object> )
	 */
	public TextSplitter(String pathToSourceCode) {
		// this.file = ...fileSourceCode =
		// TODO
		this.file = new File(pathToSourceCode);
		openFile();
	}

	/**
	 * This method is used to open a .JAVA file.
	 * 
	 * @return true if the file was successfully opened false in other cases.
	 */
	public boolean openFile() {
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			return true;
		} catch (IOException e) {
			return false;
		}
	} // End of openFile() method

	/**
	 * This method is used to open and read all the content inside a .JAVA file.
	 * 
	 * @return A String containing all the code.
	 */
	public String readFile() {

		String fileContent = "";

		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			fileContent = sb.toString();
			return fileContent;

		} catch (IOException e) {
			e.printStackTrace();
			return fileContent;
		}
	} // End of the readFile() method

	/**
	 * This method is used to close a .JAVA file.
	 * 
	 * @return true if the file was successfully closed false in other cases.
	 */
	public boolean closeFile() {
		try {
			br.close();
			fr.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	} // End of closeFile method

	/**
	 * This method counts the number of valid curly brackets used in the file
	 * 
	 * @return true if the number of curly braces is balanced, false in every
	 *         other case.
	 */
	public boolean curlyCountLeveled() {

		// Opens and reads the file
		openFile();
		String content = readFile();

		// Needed variables
		int level = 0;
		boolean insideLineComment = false;
		boolean insideMultilineComment = false;
		boolean insideSQuotes = false;
		boolean insideQuotes = false;

		// Code processing
		for (int i = 0; i < content.length(); i++) {

			if (String.valueOf(content.charAt(i)).equals("/")
					&& String.valueOf(content.charAt(i + 1)).equals("*")
					&& String.valueOf(content.charAt(i + 2)).equals("*")) {
				insideMultilineComment = true;
			}

			if (String.valueOf(content.charAt(i)).equals("/")
					&& String.valueOf(content.charAt(i + 1)).equals("*")
					&& !(String.valueOf(content.charAt(i + 2)).equals("*"))) {
				insideMultilineComment = true;
			}

			if (String.valueOf(content.charAt(i)).equals("/")
					&& String.valueOf(content.charAt(i + 1)).equals("/")) {
				insideLineComment = true;
			}

			if (String.valueOf(content.charAt(i)).equals("\n")
					&& insideLineComment == true) {
				insideLineComment = false;
			}

			if (content.charAt(i) == '"' && insideLineComment == false
					&& insideMultilineComment == false
					&& insideSQuotes == false) {
				if (insideQuotes == false) {
					insideQuotes = true;
				} else {
					insideQuotes = false;
				}
			}

			if (String.valueOf(content.charAt(i)).equals("'")
					&& insideLineComment == false
					&& insideMultilineComment == false && insideQuotes == false) {
				if (insideSQuotes == false) {
					insideSQuotes = true;
				} else {
					insideSQuotes = false;
				}
			}

			if (String.valueOf(content.charAt(i)).equals("*")
					&& String.valueOf(content.charAt(i + 1)).equals("/")) {
				insideMultilineComment = false;
			}

			if (content.charAt(i) == '{' && insideLineComment == false
					&& insideMultilineComment == false && insideQuotes == false
					&& insideSQuotes == false) {
				level++;
			}
			if (content.charAt(i) == '}' && insideLineComment == false
					&& insideMultilineComment == false && insideQuotes == false
					&& insideSQuotes == false) {
				level--;
			}

		} // End of the for loop

		if (level == 0) {
			closeFile();
			return true;
		} else {
			closeFile();
			return false;
		}

	} // End of the curlyCountLeveled method

	/**
	 * This method returns a string that contains all the .JAVA file content
	 * without comments.
	 * 
	 * @return A string that contains all the file content without comments.
	 */
	public String deleteComments() {
		// Opens & reads the file
		openFile();
		String content = readFile();

		// Needed variables
		boolean insideLineComment = false;
		boolean insideMultilineComment = false;
		StringBuilder noComments = new StringBuilder();

		// Code processing
		for (int i = 0; i < content.length(); i++) { // Detects '/**' comment
														// type
			System.out.print(content.charAt(i));
			if (String.valueOf(content.charAt(i)).equals("/")
					&& String.valueOf(content.charAt(i + 1)).equals("*")
					&& String.valueOf(content.charAt(i + 2)).equals("*")) {
				System.out.println("/** Comment detected");
				insideMultilineComment = true;
			}

			if (String.valueOf(content.charAt(i)).equals("/")
					&& String.valueOf(content.charAt(i + 1)).equals("*")
					&& !(String.valueOf(content.charAt(i + 2)).equals("*"))) {
				System.out.println("/* Comment detected");
				insideMultilineComment = true;
			}

			if (String.valueOf(content.charAt(i)).equals("/")
					&& String.valueOf(content.charAt(i + 1)).equals("/")) {
				System.out.println("// Comment detected");
				insideLineComment = true;
			}

			if (String.valueOf(content.charAt(i)).equals("\n")
					&& insideLineComment == true) {
				insideLineComment = false;
			}

			if (i > 1 && String.valueOf(content.charAt(i - 2)).equals("*")
					&& String.valueOf(content.charAt(i - 1)).equals("/")) {
				System.out.println("End of comment");
				insideMultilineComment = false;
			}

			if (insideLineComment == false && insideMultilineComment == false) {
				noComments.append(content.charAt(i));
			}

		}

		closeFile();
		return noComments.toString();

	}

	protected void finalize() throws Throwable {
		closeFile();
		super.finalize();
	} // End of finalize() method

} // End of the TextSplitter class