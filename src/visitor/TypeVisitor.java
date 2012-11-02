package visitor;

import java.util.Set;

import syntaxtree.Identifier;
import syntaxtree.Type;

public class TypeVisitor extends GJDepthFirst<String, Integer> 
{
	private Set<String> classSet;
	
	/**
	 * @param classSet
	 */
	public TypeVisitor(Set<String> classSet) {
		super();
		this.classSet = classSet;
	}
	
	/**
	* f0 -> ArrayType()
	*       | BooleanType()
	*       | IntegerType()
	*       | Identifier()
	* @return a String representing the type or class name
	*/
	public String visit(Type n, Integer argu) 
	{
		String _ret=null;
	    n.f0.accept(this, argu);
	    
	    String type = null;
		switch (n.f0.which)
		{
		// ArrayType
		case 0:
			type = "int[]";
			break;
			
		// BooleanType	
		case 1:
			type = "boolean";
			break;
			
		// IntegerType	
		case 2:
			type = "int";
			break;
			
		// Class	
		case 3:
			Identifier classNameNode = (Identifier) n.f0.choice;
			type = classNameNode.f0.tokenImage;
			if (!classSet.contains(type))
			{
				System.err.println("Class not found in the given set of class names.");
				type = null;
			}
			break;
			
		default:
			System.err.println("TypeVisitor - Shouldn't get here if parsed tree.");
		}
		
	    return _ret;
	}
}
