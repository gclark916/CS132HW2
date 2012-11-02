package visitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import syntaxtree.FormalParameter;
import syntaxtree.FormalParameterList;
import syntaxtree.FormalParameterRest;

public class FormalParameterVisitor extends GJDepthFirst<List<Map<String, String>>, Integer> 
{
	private List<Map<String, String>> parameterList;
	private Set<String> classSet;
	private TypeVisitor typeVisitor;
	
	/**
	 * @param classSet Set of names of declared Classes
	 */
	public FormalParameterVisitor(Set<String> classSet) 
	{
		super();
		this.parameterList = new ArrayList<Map<String, String>>();
		this.classSet = classSet;
		this.typeVisitor = new TypeVisitor(this.classSet);
	}
	
	/**
	* f0 -> FormalParameter()
	* f1 -> ( FormalParameterRest() )*
	*/
	public List<Map<String, String>> visit(FormalParameterList n, Integer argu) 
	{
	    n.f0.accept(this, argu);
	    n.f1.accept(this, argu);
	    return parameterList;
	}

	/**
	* f0 -> Type()
	* f1 -> Identifier()
	*/
	public List<Map<String, String>> visit(FormalParameter n, Integer argu) 
	{
		List<Map<String, String>> _ret=null;
		
	    String type = typeVisitor.visit(n.f0, null);
		
		String identifier = n.f1.f0.tokenImage;
		
		// Make sure the type is defined and the identifier has not appeared in the symbol table yet
		if (type != null && parameterList != null && !parameterListContainsIdentifier(identifier))
		{
			Map<String, String> tempMap = new HashMap<String, String>();
			tempMap.put(identifier, type);
			parameterList.add(parameterList.size(), tempMap);
		}
		else
		{
			parameterList = null;
		}
	    
	    return _ret;
	}

	/**
	* f0 -> ","
	* f1 -> FormalParameter()
	*/
	public List<Map<String, String>> visit(FormalParameterRest n, Integer argu)
	{
	    n.f0.accept(this, argu);
	    n.f1.accept(this, argu);
	    return parameterList;
	}
	
	private boolean parameterListContainsIdentifier(String identifier)
	{
		boolean _ret = false;
		Iterator<Map<String, String>> iterator = parameterList.iterator();
		while (iterator.hasNext())
		{
			Map<String, String> parameter = iterator.next();
			if (parameter.containsKey(identifier))
			{
				_ret = true;
				break;
			}
		}
		
		return _ret;
	}
}
