import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * Custom ObjectOuputStream. Designed so that writeStreamHeader()
 * do not add an overhead to the storage file when the stream starts.
 * @author David
 *
 */
public class CustomOOS extends ObjectOutputStream {
	
	/** 
	 * Constructor.
	 */
	public CustomOOS(OutputStream out) throws IOException{
		super(out);
	}

	/**  
	 * Constructor without parameters.
	 */
	protected CustomOOS() throws IOException, SecurityException{
		super();
	}

	/**  
	 * Redefined method. It would not write a header in
	 * the storage file.
	 */
	protected void writeStreamHeader() throws IOException{

	}

}