package genericSerDeser.reflection;

import genericSerDeser.util.Logger;
import genericSerDeser.util.Logger.DebugLevel;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author milan
 *
 */
public class CompareObjectsImpl implements CompareObjects{
	
	int firstCount,secondCount;
	int uniqueFirstCount, uniqueSecondCount;
	public List<Object> deserialize (List<String> inputList){
		GenericReflectCreator genericCreator = new GenericReflectCreator();
		GenericReflect genericReflet;
		List<First> firstList = new ArrayList<First>();
		List<Second> secondList = new ArrayList<Second>();
		List<Object> objectList = new ArrayList<Object>();
		//Set<First> firstSet = new HashSet<First>();
		//Set<Second> secondSet = new HashSet<Second>();
		for(int i=0; i<inputList.size(); i++){
			String input = inputList.get(i);
			String clsName="";
			boolean isFirst=false, isSecond=false;
			if(input.equals("<fqn:genericSerDeser.util.First>") ||
					input.equals("<fqn:genericSerDeser.util.Second>")){
				if(input.contains("First")){
					clsName = "genericSerDeser.reflection.First";
					isFirst = true;i++;
				}	
				else if(input.contains("Second")){
					clsName = "genericSerDeser.reflection.Second";
					isSecond = true;i++;
				}	
			    //i++;
				input = inputList.get(i);
				Class cls;
				try {
					cls = Class.forName(clsName);
					Object obj = cls.newInstance();				    
					Class[] signature = new Class[1];
					while(!input.contains("</fqn:genericSerDeser.util")){
						genericReflet = genericCreator.createGenericReflect(input);
						signature = genericReflet.getSignatureType();
						String methodName = "set" + genericReflet.getMethodName(); 
						Method meth = cls.getMethod(methodName, signature);
						Object[] params = genericReflet.getParamValue();
					    Object result = meth.invoke(obj, params);		
						i++;
						input = inputList.get(i);
					}
					boolean resultAdd = false;
					if(isFirst){
						First f = (First)obj;
						++firstCount;
						Logger.writeMessage("firstCount = "+firstCount, DebugLevel.NEWFOUND);
						objectList.add(f);
						resultAdd = firstList.add(f);
					}	
					if(isSecond){
						Second s = (Second)obj;
						++secondCount;
						Logger.writeMessage("firstCount = "+secondCount, DebugLevel.NEWFOUND);
						objectList.add(s);
						resultAdd = secondList.add(s);
					}
				}
				catch (Exception e) {
					System.err.println("incorrect  number at line "+(i+1));
					Logger.writeMessage("Error at line no: "+i+" ", DebugLevel.ERROR);
				}
				isFirst = false;
				isSecond = false;
			}			
		}
		return objectList;
	}
	@Override
	public List<String> serialize(List<Object> objectList) {
		List<String> outputList = new ArrayList<String>();
		SerializeStrategy strategyFirst = new SerializeFirst();
		SerializeStrategy strategySecond = new SerializeSecond();
		for(int i=0; i<objectList.size(); i++){
			Object obj = objectList.get(i);
			Class c = obj.getClass();
			//System.out.println("<fqn:genericSerDeser.util.First>");
			if(obj.toString().contains("First"))
				outputList.add("<fqn:genericSerDeser.util.First>");
			else
				outputList.add("<fqn:genericSerDeser.util.Second>");
			for(Field field : c.getDeclaredFields()){
				String outputString = strategyFirst.serialize(obj, field);
				outputList.add(outputString);
			}
			//System.out.println("</fqn:genericSerDeser.util.First>");
			if(obj.toString().contains("First"))
				outputList.add("</fqn:genericSerDeser.util.First>");
			else
				outputList.add("</fqn:genericSerDeser.util.Second>");
			Logger.writeMessage("Object Serialized ", DebugLevel.NEWSERIALIZED);
				
			
		}
		return outputList;
	}
	
	
}
