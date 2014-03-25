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
	public JavaCodeBlock structureCode() {
		// br.reset();// TODO reset BR to start of file
		JavaCodeBlock result = new JavaCodeBlock(file.getName(), "file");
		LOG.info("created empty File object level code block \n"+result);
		JavaCodeBlock part = new JavaCodeBlock("", "TEMP part");

		ArrayList<String> line = new ArrayList<String>(0);
		while ((line = getNextCodeLine()) != null) {
			if (part.codeRemain.size() > 0) {
				line.addAll(0, part.codeRemain);
				part.codeRemain = null;
			}
			result.add(structureLine(line));
			LOG.info("added following part to result: \n" + part
					+ "\n result looks now like this: \n" + result);
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
	public JavaCodeBlock structureLine(ArrayList<String> line) {
		JavaCodeBlock result = new JavaCodeBlock("", "");
		JavaCodeBlock subresult= new JavaCodeBlock("", "");
		if (!(line.isEmpty())) {
			LOG.info("The following List has been passed for analysis " + line);
			String item = line.get(0);

			if (Checks.startswithKeyword(item)) {
				if (Checks.startswithACL(item)) { // if the line starts with ACL
					ArrayList<String> sublist = new ArrayList<String>(
							line.subList(1, line.size()));
					result = structureLine(sublist);
					LOG.info("ACL got the following sublist back:" + result);
					result.setACL(item);
				} // end of ACL
				else if (Checks.startswithHighLevelKey(item)) {
					if (item.equals("class")) {
						// if name of block and curly are not seperated split
						// them now
						if (line.get(1).contains("{")) {
							ArrayList<String> split = new ArrayList<String>(
									Arrays.asList(line.get(1).split("{")));
							line.remove(1); // remove the old elements
							line.addAll(1, split); // add the content in
													// seperated form
						} // end of split name from curly
						else { // if it hasnt been splitted the curly needs to
								// be removed
							line.remove("{");
						}
						result = new JavaCodeBlock(line.get(1),item);
						// create object with description subroutine for block
						// starts with (0=class, 1=name 2>=POST CURLY)
						ArrayList<String> sublist = new ArrayList<String>(
								line.subList(2, line.size()));
						subresult = structureLine(sublist);
						if (subresult.size()>0){ 
							result.add(subresult);
						}

					} else {
						LOG.warning("highlevel key not implemented: " + item);
					}

				} // end of highlevel key
					// begin of block treatment start without first curly and
					// will
					// return a special last element once last curly is reached

			} else {
				LOG.warning("unimplemented: " + line);
			}
		} else {
			LOG.info("a block structure is suspected and to be analysed: "
					+ line);
			boolean finishedBlock = false;
			ArrayList<String> codeRemain = null; // var to be used for remains
													// after a }
			while (!finishedBlock) {
				if (line.size() == 0) {
					line.addAll(getNextCodeLine()); // if there is nothing more
													// in the line get the next
													// line
				}

				// TODO if there is more than 1 element in a row with a }

				// if there is only the one text with a } not seperated by space
				if ((line.get(0).contains("}") && (line.get(0) == "}"))) {
					// split at the }
					LOG.info("TEXT}TEXT case");
					ArrayList<String> split = new ArrayList<String>(
							Arrays.asList(line.get(1).split("}")));
					line.remove(line.get(0));
					line.add(0, split.get(0));
					if (split.size() > 1) { // only if there is a predecessor
											// and successor part in the line
						codeRemain = new ArrayList<String>((line.subList(1,
								line.size() - 1)));
					}
					finishedBlock = true;
				}
				// if there is only the }
				else if (line.get(0).equals("}")) { // if it hasnt been splitted
													// the
					// curly needs to
					// be removed
					LOG.info("} case");
					line.remove("}");
					finishedBlock = true;
				} else {
					LOG.info("no Curly" + line);
				}
			}
			if (codeRemain != null) {
				result.codeRemain = codeRemain;
			}

		} // end of subroutine for block
		LOG.info("the following result is returned: " + result);
		return result;
	}

	/**
	 * Function getting the next line which has code until either an error is
	 * reached or the last line which will Log an information and return null
	 * 
	 * @author benste
	 * @return ArrayList of Strings with all items of the line split by spaces
	 */
	public ArrayList<String> getNextCodeLine() {
		ArrayList<String> line;
		String textLine;
		try {
			textLine = br.readLine(); // for each line
			/*
			 * This part will read a new Line and convert it to an ArrayList
			 * split by Spaces
			 */
			if (textLine != null) {
				LOG.finest("started with new line");
				java.util.List<String> templine = Arrays.asList(textLine
						.split(" "));
				line = new ArrayList<String>(templine);

				// This part will search for empty items from the line
				ArrayList<String> delete = new ArrayList<String>();
				for (String text : line) {
					if ((text.equals("")) || (text.equals(" "))
							|| (text == null)) {
						LOG.fine("items removed from text");
						delete.add(text);
					}
				}
				// This part removes all empty items from a line
				for (String text : delete) {
					line.remove(text);
				}
				if (line.size() == 0) {
					// LOG.info("empty line, nesting next one");
					return getNextCodeLine();
				} else {
					LOG.finest("A line will be returned" + line);
					return line;
				}
				// End of looking for empty values
			} else {
				LOG.info("Last line has been reached");
			}
		} catch (IOException e) {
			LOG.warning(e.getMessage());
		}
		return null;
	}
} // End of the TextSplitter class