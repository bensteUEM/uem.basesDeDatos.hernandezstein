import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.annotation.processing.AbstractProcessor;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class TestingCli {

	/**
	 * @param args
	 *            Not used in this case
	 * @author tbd
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// CodeAnalyzer program = new CodeAnalyzer();

		//Gui gui = new Gui(); gui.setVisible(true);
		

		/* TextSplitter Testing Part 
		
		String path2 = "TestFiles" + File.separator
				+ "03d - SmallSampleCOMMENT.java";
		TextSplitter test = new TextSplitter(path2);
		// test.structureCode();
		System.out.println(test.curlyCountLeveled());
		 TextSplitter Testing Part END 
		 */
		String path1 = "TestFiles" + File.separator + "T05dMethod.java";
		String path2 = "TestFiles" + File.separator + "T07bFOR.java";
		String path3 = "TestFiles" + File.separator + "T07cNested.java";
		String path4 = "TestFiles" + File.separator + "T07dFOR.java";
		ArrayList<String> paths = new ArrayList(0);
		paths.add(path3);
		
		// Code Anaylzer Process ...
		//Get an instance of java compiler
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

		//Get a new instance of the standard file manager implementation
		StandardJavaFileManager fileManager = compiler.
		        getStandardFileManager(null, null, null);
		        
		// Get the list of java file objects, in this case we have only 
		// one file, TestClass.java
		Iterable<? extends JavaFileObject> compilationUnits1 = 
		        fileManager.getJavaFileObjectsFromStrings(paths);
		
		CompilationTask task = compiler.getTask(null, fileManager, null, null,
				null, compilationUnits1);

		// Create a list to hold annotation processors
		LinkedList<AbstractProcessor> processors = new LinkedList<AbstractProcessor>();

		// Add an annotation processor to the list
		//processors.add(new CodeAnalyzerProcessorBENSTE());
		processors.add(new CodeAnalyzerProcessor());
		
		// Set the annotation processor to the compiler task
		task.setProcessors(processors);

		// Perform the compilation task.
		task.call();

	}

}