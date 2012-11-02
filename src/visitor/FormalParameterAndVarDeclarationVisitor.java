package visitor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import syntaxtree.FormalParameter;
import syntaxtree.FormalParameterList;
import syntaxtree.FormalParameterRest;
import syntaxtree.Identifier;
import syntaxtree.Type;
import syntaxtree.VarDeclaration;

public class FormalParameterAndVarDeclarationVisitor extends GJDepthFirst<Map<String, String>, Map<String, String>> {
	
	/**
	 * @param classSet Set of names of declared Classes
	 */
	public FormalParameterAndVarDeclarationVisitor(Set<String> classSet) {
		super();
		this.symbolTable = new HashMap<String, String>();
		this.classSet = classSet;
	}

	private Map<String, String> symbolTable;
	private Set<String> classSet;
	
	/**
	* f0 -> FormalParameter()
	* f1 -> ( FormalParameterRest() )*
	*/
	public Map<String, String> visit(FormalParameterList n, Map<String, String> argu) 
	{
	    n.f0.accept(this, argu);
	    n.f1.accept(this, argu);
	    return symbolTable;
	}

	/**
	* f0 -> Type()
	* f1 -> Identifier()
	*/
	public Map<String, String> visit(FormalParameter n, Map<String, String> argu) 
	{
		Map<String, String> _ret=null;
		
	    n.f0.accept(this, argu);
	    n.f1.accept(this, argu);
	    
	    _ret = typeCheckTypeAndIdentifier(n.f0, n.f1);
	    
	    return _ret;
	}

	/**
	* f0 -> ","
	* f1 -> FormalParameter()
	*/
	public Map<String, String> visit(FormalParameterRest n, Map<String, String> argu)
	{
	    n.f0.accept(this, argu);
	    n.f1.accept(this, argu);
	    return symbolTable;
	}
	
	/**
	 * f0 -> Type()
	 * f1 -> Identifier()
	 * f2 -> ";"
	 * @param argu Map of identifier -> type for any previous VarDeclarations
	 * @return a Map of identifier -> type of the VarDeclaration list so far. null if type error.
	 */	
	public Map<String, String> visit(VarDeclaration n, Map<String, String> argu) 
	{
		Map<String, String> _ret = null;
		
		n.f0.accept(this, argu);
		n.f1.accept(this, argu);
		n.f2.accept(this, argu);
		
		_ret = typeCheckTypeAndIdentifier(n.f0, n.f1);
		
		return _ret;
	}
	
	/***
	 * Makes sure the given type, identifier pair type checks and that the identifier has not been used yet.
	 * @param typeNode node for the type
	 * @param identifierNode node for the identifier
	 * @return the symbolTable class member. null if type error
	 */
	private Map<String, String> typeCheckTypeAndIdentifier(Type typeNode, Identifier identifierNode)
	{
		String type = null;
		switch (typeNode.f0.which)
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
			Identifier classNameNode = (Identifier) typeNode.f0.choice;
			type = classNameNode.f0.tokenImage;
			if (!classSet.contains(type))
			{
				System.err.println("Class not found in the given set of class names.");
				type = null;
			}
			break;
			
		default:
			System.err.println("FormalParameterAndVarDeclarationVisitor - Shouldn't get here if parsed tree.");
		}
		
		String identifier = identifierNode.f0.tokenImage;
		
		// Make sure the type is defined and the identifier has not appeared in the symbol table yet
		if (type != null && symbolTable != null && !symbolTable.containsKey(identifier))
		{
			symbolTable.put(identifier, type);
		}
		else
		{
			symbolTable = null;
		}
		
		return symbolTable;
	}
}
