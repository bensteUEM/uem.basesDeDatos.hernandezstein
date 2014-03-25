import java.io.File;

public class TestingCli {

	/**
	 * @param args
	 * @author David & benste
	 */

	public static void main(String[] args) {
		TextSplitter test;
		int TEST = 51; // david < 50 == benste > 50
		/* Test Scenario */

		if (TEST == 1) { // Curly Test David
			String path2 = "TestFiles" + File.separator
					+ "03d - SmallSampleCOMMENT.java";
			test = new TextSplitter(path2);
			System.out.println(test.curlyCountLeveled());
		} else if (TEST == 2) { // GUI TEST David
			Gui gui = new Gui();
			gui.setVisible(true);
			
			
		} else if (TEST == 51) { // Structure Test benste
			String path1 = "TestFiles" + File.separator
					+ "01 - SmallSample.java";
			String path2 = "TestFiles" + File.separator
					+ "02 - SmallSampleVAR.java";
			/*02b - SmallSampleVAR.java
			02c - SmallSampleVAR.java
			02d - SmallSampleVAR.java
			02e - SmallSampleVAR.java
			*/
			test = new TextSplitter(path1);

			System.out.println(test.structureCode());
		}

		else {
			System.out.println("No test selected");
		}

	}

}