package framework;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ClassDetailVO {
	
	String srcFileName;
	String testFileName;

	Map<String, ArrayList<String>> methodList = new HashMap<String, ArrayList<String>>();
	
	public Map<String, ArrayList<String>> getMethodList() {
		return methodList;
	}
	public void setMethodList(Map<String, ArrayList<String>> methodList) {
		this.methodList = methodList;
	}
	public String getSrcFileName() {
		return srcFileName;
	}
	public void setSrcFileName(String srcFileName) {
		this.srcFileName = srcFileName;
	}
	public String getTestFileName() {
		return testFileName;
	}
	public void setTestFileName(String testFileName) {
		this.testFileName = testFileName;
	}
	
	
	
	
	 @Override
	    public String toString()
	    {
	        return "ClassDetailVO [srcFileName=" + srcFileName + ", testFileName=" + testFileName
	                + ", methodList=" + methodList + "]";
	    }
	
}
