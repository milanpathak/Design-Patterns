package genericSerDeser.reflection;

import java.lang.reflect.Field;

public class SerializeSecond implements SerializeStrategy{

	@Override
	public String serialize(Object obj, Field field) {

		StringBuffer sb = new StringBuffer("<type=");
		String fieldName = field.getName();
		switch (fieldName) {
		case "DoubleValue":
			sb.append(fieldName.substring(0, fieldName.length()-5).toLowerCase()+", ");
			sb.append("var="+fieldName+", ");
			sb.append("value="+((Second)obj).getDoubleValue()+">");
			//System.out.println(sb);
			break;
		case "LongValue":
			sb.append(fieldName.substring(0, fieldName.length()-5).toLowerCase()+", ");
			sb.append("var="+fieldName+", ");
			sb.append("value="+((Second)obj).getLongValue()+">");
			//System.out.println(sb);
			break;
		case "ShortValue":
			sb.append(fieldName.substring(0, fieldName.length()-5).toLowerCase()+", ");
			sb.append("var="+fieldName+", ");
			sb.append("value="+((Second)obj).getShortValue()+">");
			//System.out.println(sb);
			break;
		case "StringValue":
			sb.append("String"+", ");
			sb.append("var="+fieldName+", ");
			sb.append("value="+((Second)obj).getStringValue()+">");
			//System.out.println(sb);
			break;
		case "DoubleValue2":
			sb.append(fieldName.substring(0, fieldName.length()-6).toLowerCase()+", ");
			sb.append("var="+fieldName+", ");
			sb.append("value="+((Second)obj).getDoubleValue2()+">");
			//System.out.println(sb);
			break;
		case "LongValue2":
			sb.append(fieldName.substring(0, fieldName.length()-6).toLowerCase()+", ");
			sb.append("var="+fieldName+", ");
			sb.append("value="+((Second)obj).getLongValue2()+">");
			//System.out.println(sb);
			break;
		case "ShortValue2":
			sb.append(fieldName.substring(0, fieldName.length()-6).toLowerCase()+", ");
			sb.append("var="+fieldName+", ");
			sb.append("value="+((Second)obj).getShortValue2()+">");
			//System.out.println(sb);
			break;

		default:
			break;
		}					
		return sb.toString();
	}

}
