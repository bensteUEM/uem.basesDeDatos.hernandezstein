import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 * This class will implement the GUI functionalities include Loading a File
 * Searching for all occurrences of one Analyzed Item in a List
 * 
 * @author David
 * 
 */
public class Gui extends JFrame {

	private static final long serialVersionUID = 4384002466857611830L;
	private JButton jbBrowse;
	private JButton jbAbout;
	private JTextField jtfSearch;
	private JButton jbSearch;
	private JTable jlDisplay;
	private JScrollPane scrollBar;

	// Calculates the dimension of the computer screen
	// Sets the window size & location from the screen width and height data
	Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	final int FRAME_WIDTH = d.width / 4 * 3;
	final int FRAME_HEIGHT = d.height / 2;
	private ActionListener guiLi;
	private DefaultTableModel symbols;
	private TextSplitter tool;
	private String filePath;

	/**
	 * This is the constructor of the class
	 */
	public Gui() {

		// Sets up a GridBag Layout
		GridBagLayout gridBagLayout = new GridBagLayout();
		getContentPane().setLayout(gridBagLayout);

		createElements();
		configureElements();
		linkOperations();
		layoutElements();
		clearOldDataFile();

	} // End of the constructor of the class

	private void createElements() {
		// Sets up the 'Browse File...' button
		jbBrowse = new JButton("Browse File...");
		jbAbout = new JButton("About");

		// symbols = new DefaultTableModel(Object[][] data, numberof rows)
		symbols = new DefaultTableModel(new Vector<String>(), 0);
		// Sets up the display list
		jlDisplay = new JTable(this.symbols);

		// Places the JTextArea inside a ScrollPane
		scrollBar = new JScrollPane(jlDisplay);

		// Sets up the search text field
		jtfSearch = new JTextField();
		// Sets up the 'Search' button
		jbSearch = new JButton("Search");

		// Sets up the button listening
		guiLi = new GuiListener();

		// Create the real Tool
		this.tool = null;
	}

	private void configureElements() {
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setLocation(d.width / 2 - FRAME_WIDTH / 2, d.height / 2 - FRAME_HEIGHT
				/ 2);

		setTitle("Source Code Analyzer - Table of Symbols"); // Sets up the window title
		setResizable(true); // Resizable window
		
		ImageIcon icon = createImageIcon("/resources/img/uem_icon.gif","UEM Logo");
		setIconImage(icon.getImage());

		// Declares a custom font & some colors
		final Font FONT_1 = new Font("Helvetica", Font.PLAIN, 12);
		final Color BUTTONS_COLOR = Color.DARK_GRAY;
		final Color BACKGROUND_COLOR = Color.LIGHT_GRAY;
		final Color UEMCOLOR = new Color(143, 27, 39);
		getContentPane().setBackground(BACKGROUND_COLOR);

		// apply to elements
		jbBrowse.setFont(FONT_1);
		jbBrowse.setBackground(BUTTONS_COLOR);
		jbBrowse.setForeground(Color.WHITE);
		jbBrowse.setActionCommand("pressBrowse");

		jbAbout.setFont(FONT_1);
		jbAbout.setBackground(UEMCOLOR);
		jbAbout.setForeground(Color.WHITE);
		jbAbout.setActionCommand("pressAbout");

		jtfSearch.setFont(FONT_1);
		jbSearch.setFont(FONT_1);
		jbSearch.setBackground(BUTTONS_COLOR);
		jbSearch.setForeground(Color.WHITE);
		jbSearch.setActionCommand("pressSearch");

		// TABLE
		jlDisplay.setDefaultRenderer(String.class,
				new GuiDefaultTableCellRenderer());
		// seems not to be applied ... related to #39 Github

		for (Object name : DataInformation.getTableHeaders()) {
			this.symbols.addColumn(name);
			/*
			 * Prep for #35 on Github if (name.equals("Scope") ||
			 * name.equals("Datatype")){ setColumnCount(int columnCount); //Sets
			 * the number of columns in the model.
			 * //this.symbols.getColumn().setMinWidth(250); } /* TableColumn c =
			 * new TableColumn(0,200); c.setMinWidth(500);
			 * c.setHeaderValue(name);
			 */
		}
	}

	protected ImageIcon createImageIcon(String path, String description) {
		java.net.URL imgURL = getClass().getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL, description);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	private void linkOperations() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Disposes window on
		// close
		jbBrowse.addActionListener(guiLi); // Registers the object as a listener
		// of the component
		jbAbout.addActionListener(guiLi); // Registers the object as a listener
		// of the component
		jbSearch.addActionListener(guiLi); // Registers the object as a listener
		// of the component
	}

	private void layoutElements() {

		// Create the Gridbag Layout
		GridBagConstraints constraints_jbSearch = new GridBagConstraints();
		constraints_jbSearch.gridx = 1; // The starting column of the component
		constraints_jbSearch.gridy = 1; // The starting row of the component
		constraints_jbSearch.gridwidth = 1; // The number of columns the
											// component use
		constraints_jbSearch.gridheight = 1; // The number of rows the component
												// use

		// Adds the button & constraints to the frame
		getContentPane().add(jbSearch, constraints_jbSearch);

		// Create the Gridbag Layout
		GridBagConstraints constraints_jbBrowse = new GridBagConstraints();
		constraints_jbBrowse.gridx = 0;
		constraints_jbBrowse.gridy = 0;
		constraints_jbBrowse.gridwidth = 1;
		constraints_jbBrowse.gridheight = 1;
		constraints_jbBrowse.fill = GridBagConstraints.HORIZONTAL;
		getContentPane().add(jbBrowse, constraints_jbBrowse);

		// Create the Gridbag Layout
		GridBagConstraints constraints_jbAbout = new GridBagConstraints();
		constraints_jbAbout.gridx = 1;
		constraints_jbAbout.gridy = 0;
		constraints_jbAbout.gridwidth = 2;
		constraints_jbAbout.gridheight = 1;
		constraints_jbAbout.fill = GridBagConstraints.HORIZONTAL;
		getContentPane().add(jbAbout, constraints_jbAbout);

		// Create the Gridbag Layout
		GridBagConstraints constraints_jtfSearch = new GridBagConstraints();
		constraints_jtfSearch.gridx = 0;
		constraints_jtfSearch.gridy = 1;
		constraints_jtfSearch.gridwidth = 1;
		constraints_jtfSearch.gridheight = 1;
		constraints_jtfSearch.weightx = 1.0;
		constraints_jtfSearch.fill = GridBagConstraints.HORIZONTAL;
		getContentPane().add(jtfSearch, constraints_jtfSearch);

		// Create the Gridbag Layout
		GridBagConstraints constraints_jlDisplay = new GridBagConstraints();
		constraints_jlDisplay.gridx = 0;
		constraints_jlDisplay.gridy = 2;
		constraints_jlDisplay.gridwidth = 2;
		constraints_jlDisplay.gridheight = 1;
		constraints_jlDisplay.weightx = 1.0;
		constraints_jlDisplay.weighty = 1.0;
		constraints_jlDisplay.fill = GridBagConstraints.BOTH;
		// Add scrollbar Element to ContentPane with defined Gridbag layout
		getContentPane().add(scrollBar, constraints_jlDisplay);
	}

	/**
	 * This function replaces the existing data in the jiDisplay table with the
	 * values specified
	 * 
	 * @param data
	 *            ArrayList<DataInformation> with all object to be added
	 * @author benste
	 */
	private void addToTableResults(ArrayList<DataInformation> data) {
		// reset Table Content
		clearTable();

		for (DataInformation item : data) {
			this.symbols.addRow(item.toTableRow());
		} // Rows
	}

	private void clearTable() {
		while (symbols.getRowCount() > 0) {
			symbols.removeRow(0);
		}
	}

	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath
	 *            the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
		tool = new TextSplitter(filePath);
	}

	/**
	 * Cleans the dataInfo file
	 */
	public void clearOldDataFile() {
		DataInformationFile.clearStorage();
	}

	/**
	 * This is the button listener class.
	 * 
	 * @author David
	 * 
	 */
	private class GuiListener implements ActionListener {

		/**
		 * This method is invoked when an action occurs.
		 */
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("pressBrowse")) { // Is invoked when
																// 'Browse'
																// button is
																// pressed
				onPressBrowse();

			} else if (e.getActionCommand().equals("pressSearch")) { // Is
																		// invoked
																		// when
																		// 'Search'
																		// button
																		// is
																		// pressed
				searchItem();
			} else if (e.getActionCommand().equals("pressAbout")) { // Is
																	// invoked
																	// when
				// 'About' button is pressed
				JOptionPane
						.showMessageDialog(
								getContentPane(),
								"<html><h1> v0.99c</h1><a rel=\"license\" href=\"http://creativecommons.org/licenses/by-nc-sa/4.0/\"><img alt=\"Creative Commons License\" style=\"border-width:0\" src=\"http://i.creativecommons.org/l/by-nc-sa/4.0/88x31.png\" /></a><br /><span xmlns:dct=\"http://purl.org/dc/terms/\" property=\"dct:title\">Code Analyzer</span> by <span xmlns:cc=\"http://creativecommons.org/ns#\" property=\"cc:attributionName\">David H. and bensteUEM</span> is licensed under a <a rel=\"license\" href=\"http://creativecommons.org/licenses/by-nc-sa/4.0/\">Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License</a>.<br />Based on a work at <a xmlns:dct=\"http://purl.org/dc/terms/\" href=\"https://github.com/bensteUEM/uem.basesDeDatos.hernandezstein\" rel=\"dct:source\">https://github.com/bensteUEM/uem.basesDeDatos.hernandezstein</a></html>");

			}
		} // End of the actionPerformed method

		/**
		 * Method which is excecuted once a file is selected
		 */
		public void onPressBrowse() {

			// TODO limit visible FileTypes to .JAVA
			JFileChooser chooser = new JFileChooser(".");
			javax.swing.filechooser.FileFilter filter = new FileNameExtensionFilter(
					"Java Source Code (*.java)",
					new String[] { "JAVA", "java" });
			chooser.setFileFilter(filter);
			chooser.setAcceptAllFileFilterUsed(false);

			int returnVal = chooser.showOpenDialog(chooser);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File chosenFile = chooser.getSelectedFile();
				setFilePath(chosenFile.getPath());
				tool = new TextSplitter(getFilePath());

				// redirect errors and messages
				tool.safeMessageOutput(true);
				// start analysis
				tool.compilingProcedure();

				// Check if error messages do exist
				String errors = tool.getErrorOutput();
				if (errors.length() > 0) {
					JOptionPane.showMessageDialog(getContentPane(),
							"Errors in the SourceCode: \n\n" + errors);
				} else { // Otherwise show finish Dialog
					String messages = tool.getMessageOutput();
					JOptionPane.showMessageDialog(getContentPane(),
							"Analysis finished with following message: \n\n"
									+ messages);
					// Excecute Search Item to Refresh to List with all items
					searchItem();
				}
				// change the output streams back to normal
				tool.safeMessageOutput(false);

			} else {
				JOptionPane
						.showMessageDialog(getContentPane(),
								"Something went wrong opening the File, please try again");
			}
		}

		/**
		 * This method is called when 'Search' button is pressed.
		 */
		public void searchItem() {
			String searchKey = jtfSearch.getText();

			ArrayList<DataInformation> data = DataInformationFile
					.loadAllFromStorage();
			ArrayList<DataInformation> filtered = new ArrayList<DataInformation>();

			for (DataInformation item : data) {
				if (searchKey.equals("") || item.getName().contains(searchKey)) {
					// Save all elements which match search
					filtered.add(item);
				}
			}
			// add Items to an empty table
			addToTableResults(filtered);

		} // End of the searchItem method

	} // End of the GuiListener class

} // End of the Gui class.
