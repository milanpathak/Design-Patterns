package spreadsheetUpdates.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

/**
 * 
 * @author milan
 *
 */

public class Results implements StdoutDisplayInterface, FileDisplayInterface {

    public void writeScheduleToScreen() {

    }
    /**
     * This method writes output to the file
     */
    public void writeScheduleToFile(String fileNanme, List<String> studentList, int avg) {
    	PrintWriter writer = null;
		try {
			writer = new PrintWriter(new File(fileNanme));
			for(int i=0; i<studentList.size(); i++){
	    		writer.println(studentList.get(i).toString());
	    	}	  
			writer.print(avg);
		} catch (FileNotFoundException e) {
			System.err.println("Output file path incorrect");
			System.err.println("total " +avg);
			e.printStackTrace();
			System.exit(1);
		} finally{
			writer.close();
		}
    	

    }
    
} 


