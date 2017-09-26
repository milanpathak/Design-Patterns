package spreadsheetUpdates.feature;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import spreadsheetUpdates.observer.Observer;
import spreadsheetUpdates.observer.Subject;
import spreadsheetUpdates.util.Logger;
import spreadsheetUpdates.util.Logger.DebugLevel;

public class Cell implements Subject, Observer{

	private Integer value;
	private String name;
	private Set subjectList;
	private Set observerList;
	static Map<String, Cell> cellPool = new HashMap<String, Cell>();
	public Cell(String cellName) {
		name = cellName;
		observerList = new HashSet();
		Logger.writeMessage("Cell "+name+" created ", DebugLevel.CONSTRUCTOR);
	}
	
	/*
	 * constructor when Cell as Observer
	 */
	public Cell(String cellName, Set<Cell> subjectCellList){
		name = cellName;
		Logger.writeMessage("Cell "+name+" created ", DebugLevel.CONSTRUCTOR);
		subjectList = subjectCellList;
		Iterator i1 = subjectCellList.iterator();
		while(i1.hasNext() ){
			Object subject = i1.next();
			if(subject instanceof Subject)
				((Cell)subject).registerObserver(this);
		}
	}
	public Cell(String cellName, Set<Cell> subjectCellList, String dummy){
		name = cellName;
		subjectList = subjectCellList;		
	}
	/**
	 * Overriden from Subject
	 */
	public void registerObserver(Observer observerCell){
		if(this.observerList == null){
			this.observerList = new HashSet<Cell>();
		}
		this.observerList.add((Cell)observerCell);
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	/**
	 * Overriden from Object
	 */
	@Override
	public void update(int cellValue) {
		value = cellValue;
		Logger.writeMessage(this+" = "+this.value, DebugLevel.VALUE_UPDATE);
	}

	public void setValues(int cellValue){
		value = cellValue;
		Logger.writeMessage(this+" = "+this.value, DebugLevel.VALUE_UPDATE);
		if(observerList!=null)
			notifyObservers(this);
	}
	/**
	 * Overriden from Subject
	 */
	@Override
	public void removeObserver(Observer o) {
		Iterator i = observerList.iterator();
		while(i.hasNext()){
			Cell c = (Cell)i.next();
			if(c.equals(o)){
				observerList.remove(c);
			}
		}		
	}
	/**
	 * Overriden from Subject
	 */
	@Override
	public void notifyObservers(Cell initSubjectCell) {
		Iterator i1 = observerList.iterator();
		while (i1.hasNext()){
			Cell c = (Cell) i1.next();
			c = cellPool.get(c.name);
			Set subjCellList = c.getSubjectList();
			int tempVal=0;
			Iterator i2 = subjCellList.iterator();
			while (i2.hasNext()) {
				Object tempObject = i2.next();				
				Cell subjectCell;
				int value;
				if(tempObject instanceof Cell){
					subjectCell = (Cell)tempObject;
					if(subjectCell.equals(initSubjectCell) )
						tempVal += initSubjectCell.getValue();
					else if (cellPool.containsKey(subjectCell.name) && cellPool.get(subjectCell.name).value != null)
						tempVal += cellPool.get(subjectCell.name).value;
				}
				else{
					value = Integer.parseInt((String)tempObject);
					tempVal += value;
				}				
			}
			c.update(tempVal);
			if(c.getObserverList()!= null && c.getObserverList().size()>0){
				c.setValues(tempVal);
			}
		}		
	}

	public Integer getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	public Set getSubjectList() {
		return subjectList;
	}

	public Set<Cell> getObserverList() {
		return observerList;
	}

	/**
	 * Cell assignment can include multiple Cells, i.e. a1=b1+c1
	 * b1,c1 are subjects here and a1 is in observerList of both b1 and c1 
	 * @param subjectCellList
	 */
	public void setSubjectList(Set subjectCellList) {
		subjectList = subjectCellList;
		Iterator iterator = subjectList.iterator();
		while(iterator.hasNext()){
			Object obj = iterator.next();
			if(obj instanceof Subject){
				Subject subject = (Subject)obj;
				subject.registerObserver(this);
			}	
		}
	}

	public void setObserverList(Set<Cell> observerList) {
		this.observerList = observerList;
	}
	/**
	 * equal() and hashCode() are implemented to uniquely identify each cell
	 * To avoid repeated element   
	 */
	@Override
	public boolean equals(Object obj) {
		return (this.name .equals( ((Cell)obj).name));
	}
	@Override
	public int hashCode() {
		return this.name.hashCode();
	}
}
