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
		TextSplitter test = new TextSplitter("TestFiles"+File.separator+"01 - SmallSample.java");
		test.structureCode();
		/* TextSplitter Testing Part END*/
	}

}