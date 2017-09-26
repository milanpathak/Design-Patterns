package genericSerDeser.reflection;

import java.util.List;

/**
 * @author milan
 *
 */
public interface CompareObjects {

	public List<Object> deserialize (List<String> inputList);
	
	public List<String> serialize (List<Object> objectList);
}
