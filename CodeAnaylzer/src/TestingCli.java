import java.io.File;
import java.io.FileNotFoundException;
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

		// Gui gui = new Gui(); gui.setVisible(true);

		/*
		 * TextSplitter Testing Part
		 * 
		 * String path2 = "TestFiles" + File.separator +
		 * "03d - SmallSampleCOMMENT.java"; TextSplitter test = new
		 * TextSplitter(path2); // test.structureCode();
		 * System.out.println(test.curlyCountLeveled()); TextSplitter Testing
		 * Part END
		 */
		ArrayList<String> paths = new ArrayList(0);
		String path1 = "TestFiles" + File.separator + "T05dMethod.java";
		paths.add(path1);
		String path2 = "TestFiles" + File.separator + "T02bSmallSampleVAR.java";
		paths.add(path2);
		String path3 = "TestFiles" + File.separator + "T08bpackage.java";
		paths.add(path3);
		String path4 = "TestFiles" + File.separator + "T08aTry.java";
		paths.add(path4);
		
		TextSplitter t = new TextSplitter(paths.get(0));
		t.compilingProcedure(t.getFileName());
	}
}
