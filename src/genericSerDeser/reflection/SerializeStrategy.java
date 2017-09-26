package genericSerDeser.reflection;

import java.lang.reflect.Field;

public interface SerializeStrategy {

	public String serialize (Object obj, Field field);
}
