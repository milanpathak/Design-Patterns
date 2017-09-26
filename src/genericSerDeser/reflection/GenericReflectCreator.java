package genericSerDeser.reflection;
/**
 * 
 * @author milan
 *
 */
public class GenericReflectCreator {

	public GenericReflect createGenericReflect(String str){
		GenericReflect genericReflect= new GenericReflect();
		//type=byte, var=ByteValue, value=3
		Class[] signature = new Class[1]; 
		String methodName = "";
		Object[] paramValue = new Object[1];
		String [] strArr = str.trim().split(" ");
		String var="";
		String value ="";
		try{
			var = strArr[0].trim().substring(6, strArr[0].length()-1);
			methodName = strArr[1].trim().substring(4, strArr[1].length()-1);
			value = strArr[2].trim().substring(6, strArr[2].length()-1);
			switch (var){
				case "byte":
					signature[0] = Byte.TYPE;
					paramValue[0] = new Byte(value);
					break;
				case "short":
					signature[0] = Short.TYPE;
					paramValue[0] = new Short(value);
					break;
				case "int":
					signature[0] = Integer.TYPE;
					paramValue[0] = new Integer(value);
					break;
				case "long":
					signature[0] = Long.TYPE;
					paramValue[0] = new Long(value);
					break;
				case "float":
					signature[0] = Float.TYPE;
					paramValue[0] = new Float(value);
					break;
				case "double":
					signature[0] = Double.TYPE;
					paramValue[0] = new Double(value);
					break;
				case "boolean":
					signature[0] = Boolean.TYPE;
					paramValue[0] = new Boolean(value);
					break;
				case "char":
					signature[0] = Character.TYPE;
					paramValue[0] = new Character(value.charAt(0));
					break;
				case "String":
					signature[0] = String.class;
					paramValue[0] = new String(value);
					break;		
			}
		}
		catch(Exception e){
			//System.err.println("Incorrect number");
			//throw(new NumberFormatException());
		}
		
			
		genericReflect.setMethodName(methodName);
		genericReflect.setParamValue(paramValue);
		genericReflect.setSignatureType(signature);
		return genericReflect;
	}
}
