import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.annotation.processing.AbstractProcessor;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;

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
	private FileHandler fh;
	private File file;
	private FileReader fr;
	private BufferedReader br;
	private PrintStream normalStream;
	private ByteArrayOutputStream normalStreamArray;
	private ByteArrayOutputStream errorStreamArray;
	private PrintStream errorStream;

	/**
	 * Constructor to create a new Instance from a File
	 * 
	 * @param inputfile
	 * @return ArrayList of Objects (either String or ArrayList<Object> )
	 */
	public TextSplitter(String pathToSourceCode) {
		LOG.setLevel(Level.ALL);
		try {
			fh = new FileHandler("Logs" + File.separator + "TextSplitter.log");
			LOG.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
		LOG.fine("Text Splitter LOG initialized created");

		this.file = new File(pathToSourceCode);
		this.openFile();
	}

	/**
	 * prohibited constructor with too few arguments
	 */
	private TextSplitter() {
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
	 * takes into acocunt comments
	 * 
	 * @return true if the number of curly braces is balanced, false in every
	 *         other case.
	 * @author David
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
	 * Method that actually starts a compilation and analysis the code while
	 * doing so
	 * 
	 * @param paths
	 */
	public void compilingProcedure() {
		LOG.entering("TextSplitter", "compilingProcedure");
		// Code Anaylzer Process ...

		// Get an instance of java compiler
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

		// Get a new instance of the standard file manager implementation
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(
				null, null, null);
		LOG.finest("set up compiler and file Manager");

		// Get the list of java file objects, in this case we have only
		// one file, TestClass.java
		ArrayList<String> paths = new ArrayList<String>();
		paths.add(this.file.getPath());
		LOG.finest("set up paths");

		Iterable<? extends JavaFileObject> compilationUnits1 = fileManager
				.getJavaFileObjectsFromStrings(paths);

		CompilationTask task = compiler.getTask(null, fileManager, null, null,
				null, compilationUnits1);
		LOG.finest("set up compilation task");

		// Create a list to hold annotation processors
		LinkedList<AbstractProcessor> processors = new LinkedList<AbstractProcessor>();

		// Add an annotation processor to the list
		// processors.add(new CodeAnalyzerProcessorBENSTE());
		processors.add(new CodeAnalyzerProcessor());

		// Set the annotation processor to the compiler task
		task.setProcessors(processors);
		LOG.finest("set up processors");

		DataInformationFile.clearStorage();
		LOG.finer("reset DataInformation File");

		// Perform the compilation task.
		LOG.fine("will start now");
		task.call();
		LOG.fine("finished now");
	}

	/**
	 * @return the file name
	 */
	public String getFileName() {
		return file.getPath();
	}

	public void safeMessageOutput(boolean shouldSafe) {
		// CUSTOM CONSOLE OUTPUT STREAM
		if (shouldSafe) {
			// We first create a stream to hold the output
			normalStreamArray = new ByteArrayOutputStream();
			errorStreamArray = new ByteArrayOutputStream();
			normalStream = new PrintStream(normalStreamArray);
			errorStream = new PrintStream(errorStreamArray);
			
			// We tell system to use our output stream
			System.setOut(normalStream);
			System.setErr(errorStream);
			
		}
		// We set the output back to console
		else {
			System.out.flush();
			System.setOut(System.out);
			System.setErr(System.err);
		}
	}
	
	public String getMessageOutput(){
		return normalStreamArray.toString();
	}
	public String getErrorOutput(){
		return errorStreamArray.toString();
	}
} // End of the TextSplitter class
