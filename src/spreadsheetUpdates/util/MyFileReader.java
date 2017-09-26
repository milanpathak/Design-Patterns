package spreadsheetUpdates.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MyFileReader implements FileReaderInterface{
	File file;
	List<String> stringList;
	public MyFileReader(String fileName){
			file = new File(fileName);		
	}
	public List<String> read(){
		Scanner s = null;
		String data;
		int noOfStudent = 0;
		stringList = new ArrayList<String>();
		if(!file.exists()){
			System.err.println("incorrect fileName");
			System.exit(1);
		}
		try {
			s = new Scanner(file);
			if(!s.hasNext()){
				System.err.println("file is empty");
				System.exit(1);
			}			
			while(s.hasNext()  ){
				data = s.nextLine();
				stringList.add(data);
				//System.out.println(data);
				noOfStudent++;
			}
		} catch (FileNotFoundException e) {
			System.err.println("Exception : file not found");
			System.exit(1);
			e.printStackTrace();
		}finally{
			s.close();
		}
		return stringList;
	}
}
