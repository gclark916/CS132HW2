package visitor;
import java.util.List;
import java.util.Map;


public class MJMethod 
{
	List<Map<String, String>> parameters;
	String returnType;
	String name;
	
	/**
	 * @param parameterTypes
	 * @param returnType
	 * @param name
	 */
	public MJMethod(String name, List<Map<String, String>> parameters, String returnType)
	{
		super();
		this.parameters = parameters;
		this.returnType = returnType;
		this.name = name;
	}
}
