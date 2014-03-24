import java.io.File;
import java.util.ArrayList;

/**
 * This Code Analyzer will analyze a structured code block
 * 
 * @author benste
 * 
 */
public class CodeAnalyzer {
	private ArrayList<Object> structureSourceCode; // remaining source code part
													// to be anaylzed
	private String currentScope; // current Scope in Class.method.part writing
	private ArrayList<DataInformation> symbols;

	/**
	 * Constructor without Data should not be allowed
	 */
	private CodeAnalyzer() {
	}

	/**
	 * Constructor to create a new Instance from an existing ArrayList with
	 * Structured Code Blocks
	 * 
	 * @param currentScope
	 *            // Scope used up to here
	 * @param structureSourceCode
	 *            // Source Code to be anaylzed
	 */
	public CodeAnalyzer(ArrayList<Object> structureSourceCode) {
		this.structureSourceCode = structureSourceCode;
		this.symbols = this.anaylzeBlock("", this.structureSourceCode);
		// TODO
	}

	/**
	 * Main method to run for analyzing the existing content parts
	 * 
	 * @param structureSourceCodeBlock
	 * @return symbols table part of this block
	 */
	public ArrayList<DataInformation> anaylzeBlock(String currentScope,
			ArrayList<Object> structureSourceCode) {
		// localSymbols table
		// use first item to see whats happening in this block
		// for each part check type
		// if string -> anaylzeLine - if result add to localSymbols
		// if ArrayList<Object> -> analyzeBlock (recursion!), append resutls to
		// local Symbols
		// return symbols
		return null; // TODO TEMP
	}

	/**
	 * Main method to run for analyzing the existing content LINE
	 * 
	 * @param structureSourceCodeLine
	 * @return symbols table part of this block
	 */
	public DataInformation anaylzeLine(String structureSourceCodeLine) {
		// if important return full symbol entry
		return null; // todo
	}

	/**
	 * @return the symbols
	 */
	public ArrayList<DataInformation> getSymbols() {
		return symbols;
	}
}
