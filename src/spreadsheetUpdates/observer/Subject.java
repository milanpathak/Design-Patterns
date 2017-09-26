package spreadsheetUpdates.observer;
import spreadsheetUpdates.feature.Cell;

public interface Subject {
	public void registerObserver(Observer o);
	public void removeObserver(Observer o);
	public void notifyObservers(Cell c);
}
