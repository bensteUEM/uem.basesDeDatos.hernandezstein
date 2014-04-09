import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.logging.Logger;

public class DataInformationFile {
	private final static Logger LOG = Logger.getLogger(TextSplitter.class
			.getName());
	final static String FILE_PATH = "results" + File.separator + "dataInfo.dat";

	static{
		File theFile = new File(FILE_PATH);
		File theDir =  new File(theFile.getParent());

		// if the directory does not exist, create it
		if (!theDir.exists()) {
			LOG.info("creating directory: " + FILE_PATH);
			theDir.mkdir();
		}
	}
	
	/**
	 * Method to be used from other classes to append one information object to
	 * a storage
	 * 
	 * @param oneInformation
	 *            A dataInformation object
	 * @return success of operation
	 * @author David
	 */
	public static boolean saveToStorage(DataInformation oneInformation) {
		
		File file = new File(FILE_PATH);
		LOG.finer("File defined: " + file);
		try {
			if (file.length() == 0) {
				LOG.fine("File was empty");
				startFile(oneInformation);
				return true;
			} else {
				FileOutputStream fos = new FileOutputStream(FILE_PATH, true);
				LOG.fine("File now linked to Stream: " + fos);
				CustomOOS coos = new CustomOOS(fos);
				LOG.finer("Created customer OOS: " + coos);
				coos.writeObject(oneInformation);
				LOG.fine("Wrote Object: " + oneInformation.getName());
				coos.close();
				fos.close();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	} // End of saveToStorage method

	/**
	 * Starts the data storage file.
	 * 
	 * @param oneInformation
	 *            A dataInformation object
	 */
	private static void startFile(DataInformation oneInformation) {
		LOG.entering("DataInformation", "startFile");

		try {
			FileOutputStream fos = new FileOutputStream(FILE_PATH);
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			oos.writeObject(oneInformation);

			oos.close();
			fos.close();
			
			LOG.finest("Filled new ResultFile with 1st object");

		} catch (Exception e) {
			LOG.severe(e.getMessage());
		}
	} // End of start file method

	/**
	 * Load all saved objects from the storage for usage as objects
	 * 
	 * @return ArrayList with DataInformationObjects
	 * @author David
	 */
	public static ArrayList<DataInformation> loadAllFromStorage() {
		ArrayList<DataInformation> list = new ArrayList<DataInformation>(0);
		try {
			FileInputStream fis = new FileInputStream(FILE_PATH);
			ObjectInputStream ois = new ObjectInputStream(fis);

			// Reads rest
			Object aux = null;
			while ((aux = ois.readObject()) != null) {
				if (aux instanceof DataInformation)
					list.add((DataInformation) aux);

			}
			ois.close();
			fis.close();
			return list;
		} catch (EOFException e1) {
			// End of file
			return list;
		} catch (FileNotFoundException e) {
			System.out.println("No File with Symbols found");
			return list;
		} catch (Exception e2) {
			e2.printStackTrace();
			return null;
		}

	} // End of loadFromStorage method

	/**
	 * This function will return the DataInformation Object stored for the Parent
	 * 
	 * @return String defining the Scope
	 * @author benste
	 */
	public static DataInformation getParentElement(String nameOfParent) {
		LOG.entering("DataInformationFile","loadParamParentMethod");
		ArrayList<DataInformation> data = loadAllFromStorage();
		
		// Generate an iterator. Start just after the last element.
		ListIterator<DataInformation> list = data.listIterator(data.size());

		// Iterate in reverse.
		while(list.hasPrevious()) {
			DataInformation obj =  list.previous();
			if (obj.getName().equals(nameOfParent)){
				LOG.finer("Found object which is parent: "+nameOfParent);
				return obj;
			} //end if
		} //end while

		LOG.info("No parent found with name: "+nameOfParent+" - assuming Top Level");
		return null;

	} // End of loadFromStorage method
	
	
	
	/**
	 * Clears the storage and leaves it empty for the next append to be the
	 * first item
	 * 
	 * @author David
	 */
	public static void clearStorage() {
		try {
			FileWriter fw = new FileWriter(FILE_PATH); // Content will be
														// overwritten
			BufferedWriter bw = new BufferedWriter(fw); // Writes inside file
														// using a buffer

			bw.write("");

			bw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	} // End of clearStorage method

}
