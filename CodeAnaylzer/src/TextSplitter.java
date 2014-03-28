import java.awt.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

import deprecated.JavaStructure;

/**
 * This class reads a .JAVA file and splits the source code into different
 * logical blocks
 * 
 * @author benste, davidh
 * 
 */
public class TextSplitter {
	private final static Logger LOG = Logger.getLogger(TextSplitter.class
			.getName());
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
		this.openFile();
		LOG.fine("Text Splitter created");
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
			LOG.fine("the following file has been opened  " + file);
			return true;
		} catch (IOException e) {
			LOG.warning("issues opening the file: " + e);
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

		} // End of for loop

		closeFile();
		return noComments.toString();

	} // End of delete comments method.

	protected void finalize() throws Throwable {
		closeFile();
		super.finalize();
	} // End of finalize() method

	/**
	 * This method is used to read the file line by line and structure it into
	 * smaller parts which represent its structure
	 * 
	 * @return structured SourceCode
	 * @author benste
	 */
	public ArrayList<JavaStructure> structureCode() {
		ArrayList<String> line;
		String textLine;
		try {
			while (null != (textLine = br.readLine())) {
				/*
				 * This part will read a new Line and convert it to an ArrayList
				 * split by Spaces
				 */
				LOG.finest("started with new line");
				System.out.println(textLine); // TODO DEBUG line =
				java.util.List<String> templine = Arrays.asList(textLine
						.split(" "));
				line = new ArrayList<String>(templine);

				// This part will search for empty items from the line
				ArrayList<String> delete = new ArrayList<String>();
				for (String text : line) {
					if ((text.equals("")) || (text.equals(" "))
							|| (text == null)) {
						System.out.println("items removed from text");
						delete.add(text);
					}
				}
				// This part removes all empty items from a line 
				for (String text : delete) {
					line.remove(text);
				}
				delete = null;
				// End of looking for empty values

				// Skip the line if its empty
				if (line.isEmpty()) {
					continue;
				}

				// TODO continue here with analysis
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null; // TODO DEBUG
	} // End of structureCode method
	
	 /**
     * Starts the data storage file.
     * @param oneInformation A dataInformation object
     */
	private static void startFile(DataInformation oneInformation){
		final String FILE_PATH = "files/dataInfo.dat"; 
		try{
			FileOutputStream fos = new FileOutputStream(FILE_PATH);
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			oos.writeObject(oneInformation);
			oos.close();
			fos.close();

		}
		catch (Exception e){
			e.printStackTrace();
		}
	} // End of start file method
	
	/**
	 * Method to be used from other classes to append one information object to a storage
	 * @param oneInformation A dataInformation object
	 * @return success of operation
	 * @author David
	 */
	public static boolean saveToStorage(DataInformation oneInformation){
		final String FILE_PATH = "files/dataInfo.dat";
		File file = new File(FILE_PATH);
		
		try{
			if(file.length() == 0){
				startFile(oneInformation);
				return true;
			} else {
				FileOutputStream fos = new FileOutputStream(FILE_PATH, true);
				CustomOOS coos = new CustomOOS(fos);
				coos.writeObject(oneInformation);
				coos.close();
				fos.close();
				return true;
			}
		}
		catch (Exception e){
			e.printStackTrace();
			return false;
		}
	} // End of saveToStorage method
	
	/**
	 * Load all saved objects from the storage for usage as objects
	 * @return ArrayList with DataInformationObjects
	 */
	public ArrayList<DataInformation> loadFromStorage(){
		final String FILE_PATH = "files/dataInfo.dat";
		ArrayList<DataInformation> list = new ArrayList<DataInformation>();
		try{
			FileInputStream fis = new FileInputStream(FILE_PATH);
			ObjectInputStream ois = new ObjectInputStream(fis);

			// Reads First object
			Object aux = ois.readObject();

			// Reads rest
			while (aux != null) {
				if (aux instanceof DataInformation)
					list.add((DataInformation) aux);
				aux = ois.readObject();

			}
			ois.close();
			fis.close();
			return list;
		}
        catch (EOFException e1){
            // End of file
            return list;
        }
        catch (Exception e2){
            e2.printStackTrace();
            return null;
        }	
		
	} // End of loadFromStorage method
	
	/**
	 * Clears the storage and leaves it empty for the next append to be the first item
	 * @param oneInformation
	 * @author David
	 */
	public static void clearStorage(DataInformation oneInformation){
		try{
			final String FILE_PATH = "files/dataInfo.dat";
			FileWriter fw = new FileWriter(FILE_PATH); // Content will be overwritten
			BufferedWriter bw = new BufferedWriter(fw); // Writes inside file using a buffer

			bw.write("");

			bw.close();
			fw.close();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	} // End of clearStorage method
	
} // End of the TextSplitter class