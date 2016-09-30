import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import constants.UrlConstants;

public class MyLogger {
	public static final Logger myLogger = Logger.getLogger("Thesaurus");
	private static MyLogger instance = null;
	
	 public static MyLogger getInstance() {
	      if(instance == null) {
	    	  prepareLogger();
	    	  instance = new MyLogger ();
	      }
	      return instance;
	   }
	 


	private static void prepareLogger() {
		 FileHandler myFileHandler;
		try {
			myFileHandler = new FileHandler(UrlConstants.THESAURUS_LOG_FILE_URL);
			myFileHandler.setFormatter(new SimpleFormatter());
	        myLogger.addHandler(myFileHandler);
	        myLogger.setUseParentHandlers(false);
	        myLogger.setLevel(Level.FINEST);
		} catch (SecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		         
		 }
		   

}
