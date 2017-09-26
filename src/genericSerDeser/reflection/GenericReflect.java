package genericSerDeser.reflection;

public class GenericReflect {

	public Class []signatureType;
	public String methodName;
	public Object[] paramValue;
	public Class[] getSignatureType() {
		return signatureType;
	}
	public void setSignatureType(Class []signatureType) {
		this.signatureType = signatureType;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public Object[] getParamValue() {
		return paramValue;
	}
	public void setParamValue(Object []paramValue) {
		this.paramValue = paramValue;
	}
	
}
