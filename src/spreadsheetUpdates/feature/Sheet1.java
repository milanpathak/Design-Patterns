package spreadsheetUpdates.feature;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import spreadsheetUpdates.util.Logger;
import spreadsheetUpdates.util.Logger.DebugLevel;


/**
 * 
 * @author milan
 *
 */

public class Sheet1 {
	boolean isCycle = false;
	List<String> outputForFile = new ArrayList<>();
	/**
	 * This method takes user input list and process it the way Excel Spreadsheet does
	 * @param inputList
	 * @return
	 */
	public Map<String, Cell> demo(List<String> inputList) {
		
		Map<String, Cell> cellPool = Cell.cellPool;
		int totalValue = 0;
		for(int i=0; i<inputList.size(); i++){
			String str = inputList.get(i);
			String observer, subject1, subject2=null;
			int subject1Value, subject2Value;
			observer = str.substring(0, str.indexOf("="));
			if(str.contains("+")){
				subject1 = str.substring(str.indexOf("=")+1, str.indexOf("+"));
				subject2 = str.substring(str.indexOf("+")+1);				
			}
			else{
				subject1 = str.substring(str.indexOf("=")+1);
			}	
			Cell subjectCell1 = null;
			Cell subjectCell2 = null;
			Set subjectCellList = new HashSet();
			/**
			 * Set Subject Cell1 properties
			 */
			if(cellPool.containsKey(subject1))
				subjectCell1 = cellPool.get(subject1);
			if(subjectCell1 == null && isCell(subject1)){
				subjectCell1 = new Cell(subject1);
				cellPool.put(subject1, subjectCell1);					
			}
			if(subjectCell1 != null)
				subjectCellList.add(subjectCell1);
			else
				subjectCellList.add(subject1);			
			/**
			 * Set Subject Cell2 properties
			 */
			if(cellPool.containsKey(subject2)){
				subjectCell2 = cellPool.get(subject2);
			}
			if(subjectCell2 == null && isCell(subject2)){
				subjectCell2 = new Cell(subject2);
				cellPool.put(subject2, subjectCell2);
			}
			if(subjectCell2 != null)
				subjectCellList.add(subjectCell2);
			else
				subjectCellList.add(subject2);
			/**
			 * Set Observer Cell properties
			 */
			Cell observerCell = null;
			if(cellPool.containsKey(observer) && cellPool.get(observer).getSubjectList()== null &&
					!isCycle(new Cell(observer, subjectCellList), observer)){
				observerCell = cellPool.get(observer);
				observerCell.setSubjectList(subjectCellList);
			}
			else if(observerCell==null && !isCycle(new Cell(observer, subjectCellList, "dummy"), observer)){
				observerCell = new Cell(observer, subjectCellList);
				if(cellPool.get(observer)!=null){
					observerCell.setObserverList(cellPool.get(observer).getObserverList());
				}
			}
			else{
				isCycle = false;
				Logger.writeMessage("Cycle Found at line "+(i+1), DebugLevel.CYCLE);
				outputForFile.add("Cycle found, Line "+(i+1)+" ignored");
				continue;
			}
			/**
			 * Calculate Cell Values based on formulae
			 * i.e. a1 = b1+c1; 2 operands
			 */
			if( (!isCell(subject1) && !isCell(subject2) && null!=subject2) ||	//both are values
			( (cellPool.get(subject1)!= null && cellPool.get(subject1).getValue()!= null) &&
					  (cellPool.get(subject2)!= null && cellPool.get(subject2).getValue()!= null) ) ||	//both cell has values
			( (cellPool.get(subject1)!= null && cellPool.get(subject1).getValue()!= null) && 
					  (!isCell(subject2))  ) ||	//cell1 has value and subject2 is value 
			( (cellPool.get(subject2)!= null && cellPool.get(subject2).getValue()!= null) && 
					  (!isCell(subject1))  ) 	//cell2 has value and subject1 is value
			){
				if( (cellPool.get(subject1)!= null && cellPool.get(subject1).getValue()!= null) &&
					  (cellPool.get(subject2)!= null && cellPool.get(subject2).getValue()!= null) ) {//both cell has values)
					subject1Value = cellPool.get(subject1).getValue();
					subject2Value = cellPool.get(subject2).getValue();
					
				}else if((cellPool.get(subject1)!= null && cellPool.get(subject1).getValue()!= null) && 
						  (!isCell(subject2))  ) {	//cell1 has value and subject2 is value )
					subject1Value = cellPool.get(subject1).getValue();
					subject2Value = Integer.parseInt(subject2);
					
				}else if((cellPool.get(subject2)!= null && cellPool.get(subject2).getValue()!= null) && 
						  (!isCell(subject1))  ) {	//cell2 has value and subject1 is value
					subject1Value = Integer.parseInt(subject1);
					subject2Value = cellPool.get(subject2).getValue();
					
				}else{
					subject1Value = Integer.parseInt(subject1);
					subject2Value = Integer.parseInt(subject2);					
				}
				if(!isCycle(observerCell, observer)){
					observerCell.setValues(subject1Value+subject2Value);
					cellPool.put(observer, observerCell);
					outputForFile.add(str+" ==> "+observer+" = "+(subject1Value+subject2Value));
				}	
			}
			else if(cellPool.get(subject1).getValue()== null || cellPool.get(subject2).getValue()== null){
				cellPool.put(observer, observerCell);
			}
			/**
			 * i.e. a1 = b1 one operand
			 */
			else if(!isCell(subject1) ||
					(cellPool.get(subject1)!= null && cellPool.get(subject1).getValue()!= null &&
					!isCycle(observerCell, observer))){
				if(!isCell(subject1)) 
					subject1Value = Integer.parseInt(subject1);
				else 
					subject1Value = cellPool.get(subject1).getValue();
				observerCell = cellPool.get(observerCell.toString());
				observerCell.setValues(subject1Value);
				cellPool.put(observer, observerCell);
				outputForFile.add(str+" == > observer = "+(subject1Value));
			}
			isCycle = false;			
		}	
		
		return cellPool;
	}
	/**
	 * This method detects whether current assignment causes cycle  
	 * @param observer
	 * @param initObserver
	 * @return
	 */
	private boolean isCycle (Cell observer, String initObserver){
		Set subjectList = observer.getSubjectList();
		if(subjectList!= null){
			Iterator i1 = subjectList.iterator();
			while(i1.hasNext()){
			Object tempObject = i1.next();	
				if(tempObject instanceof Cell){
					Cell subject = (Cell)tempObject;
					if(initObserver.equals(subject.getName())){
						Logger.writeMessage("Cycle Found, Current assignment for "+ initObserver+" ignored", DebugLevel.CYCLE);
						isCycle = true;
						return true;
					}
					else{
						isCycle(subject, initObserver);
					}
				}	
			}
		}
		
		return isCycle;
	}
	
	/**
	 * This method checks whether current String is a Cell or Value
	 * @param string
	 * @return
	 */
	private boolean isCell(String string){
		if(null == string){
			return false;
		}
		Integer no = null;
		try{
			no = Integer.parseInt(string);			
		}catch(Exception e){
			//System.out.println("operand is value");
		}
		if(no == null)
			return true;
		else
			return false;
	}	
	/**
	 * This method calculates total of all cell values
	 * @param cellPool
	 * @return
	 */
	public int calculateTotal(Map<String, Cell> cellPool){
		int totalValue =0;
		Iterator<String> it = cellPool.keySet().iterator();
		while(it.hasNext()){
			Cell c =  cellPool.get(it.next());
			Integer cellVal = c.getValue();
			if(null != cellVal){
				Logger.writeMessage(c+" = "+cellVal, DebugLevel.TOTAL);
				totalValue += cellVal;
			}
			
		}		
		Logger.writeMessage("total = "+totalValue, DebugLevel.TOTAL);
		return totalValue ;
	}
	public List<String> getOutputForFile() {
		return outputForFile;
	}
}
