package spreadsheetUpdates.util;

import java.util.List;

/**
 * 
 * @author milan
 *
 */
public interface FileDisplayInterface {
	
	public void writeScheduleToFile(String fileName, List<String> output, int total);

}
