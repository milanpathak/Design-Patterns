package genericSerDeser.driver;

import genericSerDeser.reflection.CompareObjects;
import genericSerDeser.reflection.CompareObjectsImpl;
import genericSerDeser.util.FileReaderInterface;
import genericSerDeser.util.Logger;
import genericSerDeser.util.MyFileReader;
import genericSerDeser.util.Results;
import genericSerDeser.util.Logger.DebugLevel;

import java.util.ArrayList;
import java.util.List;


/**
 * 
 * @author milan
 *
 */

public class Driver {
	public static void main(String[] args) {
		if(args.length < 3){
			System.err.println("please provide valid input arguments");
			System.exit(1);
		}
		String inputFile = args[0];
		String outputFile = args[1];
		int DEBUG_VALUE = Integer.parseInt(args[2]);
		Logger.setDebugValue(DEBUG_VALUE);		
		List<String> inputList = new ArrayList<String>();		
		FileReaderInterface inputReader = new MyFileReader(inputFile);
		Results result = new Results();
		int firstCount=0, secondCount =0;
		inputList = inputReader.read();
		CompareObjects compareObjects = new CompareObjectsImpl();
		List<Object> objList = compareObjects.deserialize(inputList);
		List<String> outputStrings = compareObjects.serialize(objList);
		result.writeScheduleToFile(outputFile, outputStrings);
		Logger.writeMessage(outputStrings.size()+" lines printed on file "+outputFile, DebugLevel.OUT);			
	}
}
