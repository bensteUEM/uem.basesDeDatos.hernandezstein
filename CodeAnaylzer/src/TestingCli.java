import java.io.File;

public class TestingCli {

	/**
	 * @param args
	 *            Not used in this case
	 * @author tbd
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// CodeAnalyzer program = new CodeAnalyzer();

		/* GUI testing part
		Gui gui = new Gui();
		gui.setVisible(true);
		*/

		/* TextSplitter Testing Part */
		String path1 = "TestFiles"+File.separator+"01 - SmallSample.java";
		String path2 = "TestFiles"+File.separator+"03d - SmallSampleCOMMENT.java";
		TextSplitter test = new TextSplitter(path2);
		//test.structureCode();
		System.out.println(test.curlyCountLeveled());
		/* TextSplitter Testing Part END*/

	}

}