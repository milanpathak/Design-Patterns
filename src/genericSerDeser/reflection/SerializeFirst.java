package genericSerDeser.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class SerializeFirst implements SerializeStrategy{

	@Override
	public String serialize(Object obj, Field field) {
		StringBuffer sb = new StringBuffer("<type=");
		String fieldName = field.getName();
		String methodName = "get"+fieldName;
		Class cls = obj.getClass();
		
		Class[] signature = new Class[1];  
		try {
			Object noparams[] = {};
			Method meth = cls.getMethod(methodName);
			Object result = meth.invoke(obj, noparams);
			if(fieldName.contains("String"))
				sb.append(fieldName.substring(0, fieldName.length()-5)+", ");
			else
				sb.append(fieldName.substring(0, fieldName.indexOf('V')).toLowerCase()+", ");
			//sb.append(fieldName.substring(0, fieldName.length()-5).toLowerCase()+", ");
			sb.append("var="+fieldName+", ");
			sb.append("value="+result+">");
			//System.out.println(sb);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}	
}
