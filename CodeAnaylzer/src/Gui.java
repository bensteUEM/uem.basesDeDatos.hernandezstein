import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

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
	private JTextField jtfSearch;
	private JButton jbSearch;
	private JList<String> jlDisplay;

	/**
	 * This is the constructor of the class
	 */
	public Gui() {

		// Calculates the dimension of the computer screen
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

		// Sets the window size & location from the screen width and height data
		final int FRAME_WIDTH = d.width / 2;
		final int FRAME_HEIGHT = d.height / 2;
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setLocation(d.width / 2 - FRAME_WIDTH / 2, d.height / 2 - FRAME_HEIGHT
				/ 2);
		setTitle("Main Window"); // Sets up the window title
		setResizable(true); // Resizable window
		Image icon1 = Toolkit.getDefaultToolkit().getImage("img/uem_icon.gif"); // Gets
																				// an
																				// image
																				// from
																				// a
																				// file
		setIconImage(icon1); // Sets an image as the icon of the window
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Disposes window on
															// close

		// Declares a custom font & some colors
		final Font FONT_1 = new Font("Arial", Font.PLAIN, 12);
		final Color BUTTONS_COLOR = Color.DARK_GRAY;
		final Color BACKGROUND_COLOR = Color.LIGHT_GRAY;
		getContentPane().setBackground(BACKGROUND_COLOR);

		// Sets up a GridBag Layout
		GridBagLayout gridBagLayout = new GridBagLayout();
		getContentPane().setLayout(gridBagLayout);

		// Sets up the button listening
		GuiListener guiLi = new GuiListener();

		// Sets up the 'Browse File...' button
		jbBrowse = new JButton("Browse File...");

		jbBrowse.setFont(FONT_1);
		jbBrowse.setBackground(BUTTONS_COLOR);
		jbBrowse.setForeground(Color.WHITE);
		jbBrowse.setActionCommand("pressBrowse");
		jbBrowse.addActionListener(guiLi); // Registers the object as a listener
											// of the component

		// Now we specify constraints for components that are laid out using the
		// GridBagLayout class
		GridBagConstraints constraints_jbBrowse = new GridBagConstraints();
		constraints_jbBrowse.gridx = 0; // The starting column of the component
		constraints_jbBrowse.gridy = 0; // The starting row of the component
		constraints_jbBrowse.gridwidth = 2; // The number of columns the
											// component use
		constraints_jbBrowse.gridheight = 1; // The number of rows the component
												// use
		constraints_jbBrowse.fill = GridBagConstraints.HORIZONTAL; // Fills the
																	// space
																	// horizontally
																	// with the
																	// component
		getContentPane().add(jbBrowse, constraints_jbBrowse); // Adds the button
																// & constraints
																// to the frame

		// Sets up the search text field
		jtfSearch = new JTextField();

		jtfSearch.setFont(FONT_1);

		// Now we specify constraints for components that are laid out using the
		// GridBagLayout class
		GridBagConstraints constraints_jtfSearch = new GridBagConstraints();
		constraints_jtfSearch.gridx = 0; // The starting column of the component
		constraints_jtfSearch.gridy = 1; // The starting row of the component
		constraints_jtfSearch.gridwidth = 1; // The number of columns the
												// component use
		constraints_jtfSearch.gridheight = 1; // The number of rows the
												// component use
		constraints_jtfSearch.weightx = 1.0; // Expands the component column
												// horizontally
		constraints_jtfSearch.fill = GridBagConstraints.HORIZONTAL; // Fills the
																	// space
																	// horizontally
																	// with the
																	// component
		getContentPane().add(jtfSearch, constraints_jtfSearch); // Adds the
																// button &
																// constraints
																// to the frame

		// Sets up the 'Search' button
		jbSearch = new JButton("Search");

		jbSearch.setFont(FONT_1);
		jbSearch.setBackground(BUTTONS_COLOR);
		jbSearch.setForeground(Color.WHITE);
		jbSearch.setActionCommand("pressSearch");
		jbSearch.addActionListener(guiLi); // Registers the object as a listener
											// of the component

		// Now we specify constraints for components that are laid out using the
		// GridBagLayout class
		GridBagConstraints constraints_jbSearch = new GridBagConstraints();
		constraints_jbSearch.gridx = 1; // The starting column of the component
		constraints_jbSearch.gridy = 1; // The starting row of the component
		constraints_jbSearch.gridwidth = 1; // The number of columns the
											// component use
		constraints_jbSearch.gridheight = 1; // The number of rows the component
												// use
		// constraints_jbSearch.fill = GridBagConstraints.HORIZONTAL; // Fills
		// the space horizontally with the component
		getContentPane().add(jbSearch, constraints_jbSearch); // Adds the button
																// & constraints
																// to the frame

		// Sets up the display list
		jlDisplay = new JList<String>();
		JScrollPane scrollBar = new JScrollPane(jlDisplay); // Places the
																// JTextArea
																// inside a
																// ScrollPane

		// Now we specify constraints for components that are laid out using the
		// GridBagLayout class
		GridBagConstraints constraints_jlDisplay = new GridBagConstraints();
		constraints_jlDisplay.gridx = 0; // The starting column of the
											// component
		constraints_jlDisplay.gridy = 2; // The starting row of the component
		constraints_jlDisplay.gridwidth = 2; // The number of columns the
												// component use
		constraints_jlDisplay.gridheight = 1; // The number of rows the
												// component use
		constraints_jlDisplay.weightx = 1.0; // Expands the component column
												// horizontally
		constraints_jlDisplay.weighty = 1.0; // Expands the component row
												// vertically
		constraints_jlDisplay.fill = GridBagConstraints.BOTH; // Fills the
																// space
																// horizontally
																// with the
																// component
		getContentPane().add(scrollBar, constraints_jlDisplay); // Adds the
																	// list &
																	// constraints
																	// to the
																	// frame

	} // End of the constructor of the class

	/**
	 * This is the button listener class.
	 * @author David
	 * 
	 */
	private class GuiListener implements ActionListener {

		// Creates a file chooser
		final JFileChooser fc = new JFileChooser();

		private String filePath = "";

		/**
		 * This method is invoked when an action occurs.
		 */
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("pressBrowse")) { // Is invoked when
																// 'Browse'
																// button is
																// pressed

				int returnVal = fc.showOpenDialog(this.fc);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					filePath = file.getAbsolutePath().replace(
							File.separatorChar, '/'); //TODO replace command necessary??
					
				}

			}
			if (e.getActionCommand().equals("pressSearch")) { // Is invoked when
																// 'Search'
																// button is
																// pressed
				searchItem();
			}
		} // End of the actionPerformed method
		
		/**
		 * This method is called when 'Search' button is pressed.
		 */
		public void searchItem(){
			String input = "";
			if(jtfSearch.getText().equals("")){
				JOptionPane.showMessageDialog(null,"Please, insert "
						+ "a valid identifier.", "Warning", 
						JOptionPane.WARNING_MESSAGE);
			} else {
				input = jtfSearch.getText();
				//TODO Invoke here method & display results in jlDisplay
				// The file path selected by user
				// may be accessed by this.filepath
				
			}
		} // End of the searchItem method

	} // End of the GuiListener class

} // End of the Gui class.
