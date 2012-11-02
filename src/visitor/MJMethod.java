package visitor;
import java.util.Map;


public class MJMethod 
{
	Map<String, String> parameterTypes;
	String returnType;
	String name;
	
	/**
	 * @param parameterTypes
	 * @param returnType
	 * @param name
	 */
	public MJMethod(String name, Map<String, String> parameterTypes, String returnType)
	{
		super();
		this.parameterTypes = parameterTypes;
		this.returnType = returnType;
		this.name = name;
	}
}
