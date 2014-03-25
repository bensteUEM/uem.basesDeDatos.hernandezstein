import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

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

		}

		closeFile();
		return noComments.toString();

	}

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
	public ArrayList<JavaCodeBlock> structureCode() {
		// br.reset();// TODO reset BR to start of file
		ArrayList<JavaCodeBlock> result = new ArrayList<JavaCodeBlock>(0);
		ArrayList<String> line = new ArrayList<String>(0);
		while ((line = getNextCodeLine()) != null) {
			if (!(line.isEmpty())) {
				result.add((JavaCodeBlock) structureLine(line, ""));
			}
		}
		return result;
	}

	/**
	 * recursive function to analyse the structure of a String
	 * 
	 * @param line
	 *            the line which should be analysed
	 * @param specialStructure
	 *            - specifies if the following part is part of a more specific
	 *            grammar
	 * @return
	 */
	public JavaCodeBlock structureLine(ArrayList<String> line,
			String specialStructure) {
		String item = line.get(0);
		JavaCodeBlock result = new JavaCodeBlock(item,specialStructure);

		if (Checks.startswithKeyword(item)) {
			if (Checks.startswithACL(item)) { // if the line starts with ACL
				ArrayList<String> sublist = new ArrayList<String>(line.subList(
						1, line.size()));
				result = structureLine(sublist, "");
				result.get(0).setACL(item);
			} // end of ACL
			else if (Checks.startswithHighLevelKey(item)) {
				if (item.equals("class")) {
					// if name of block and curly are not seperated split them
					// now
					if (line.get(1).contains("{")) {
						ArrayList split = new ArrayList<String>(
								Arrays.asList(line.get(1).split("{")));
						line.addAll(1, split);
						line.add(2, "{"); // add the remove seperator
					} // end of split name from curly

					JavaCodeBlock block = new JavaCodeBlock(line.get(1), item); // create
																				// object
																				// with
																				// description

					// subroutine for block starts with (0=class, 1=name 2>=POST
					// CURLY)
					ArrayList<String> sublist = new ArrayList<String>(
							line.subList(2, line.size()));
					block.add(structureLine(sublist, "block"));
					//add the completed block to the result
					result.addAll(block);

				} else {
					LOG.warning("ḧighlevel key not implemented: " + item);
				}

			} // end of highlevel key
				// begin of block treatment start without first curly and will
				// return a special last element once last curly is reached
			else if (specialStructure.equals("block")) {
				// TODO do something with the block content until its ended
				LOG.info("forget about the content of the block for now);");

				// TODO ending block with matching }
				while (!(line.get(line.size()).equals("}")))// temp to finish
															// just for 1 level
															// block ! //TODO
					//TODO put remains after } back into some kind of queue for parent process
					//make line a class var?
				{
					result.add(structureLine(line, ""));
					line.addAll(getNextCodeLine());
					LOG.warning("temporary finish the code on one level");
				}
			} // end of subroutine for block

		} else {
			LOG.warning("unimplemented");
		}
		return result;
	}

	public ArrayList<String> getNextCodeLine() {
		ArrayList<String> line;
		String textLine;
		try {
			textLine = br.readLine(); // for each line
			/*
			 * This part will read a new Line and convert it to an ArrayList
			 * split by Spaces
			 */
			LOG.finest("started with new line");
			java.util.List<String> templine = Arrays
					.asList(textLine.split(" "));
			line = new ArrayList<String>(templine);

			// This part will search for empty items from the line
			ArrayList<String> delete = new ArrayList<String>();
			for (String text : line) {
				if ((text.equals("")) || (text.equals(" ")) || (text == null)) {
					System.out.println("items removed from text");
					delete.add(text);
				}
			}
			// This part removes all empty items from a line
			for (String text : delete) {
				line.remove(text);
			}
			return line;
			// End of looking for empty values
		} catch (IOException e) {
			LOG.warning(e.getMessage());
			return null;
		}
	}
} // End of the TextSplitter class