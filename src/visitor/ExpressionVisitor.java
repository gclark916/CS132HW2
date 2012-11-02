package visitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import syntaxtree.AllocationExpression;
import syntaxtree.AndExpression;
import syntaxtree.ArrayAllocationExpression;
import syntaxtree.ArrayLength;
import syntaxtree.ArrayLookup;
import syntaxtree.BracketExpression;
import syntaxtree.CompareExpression;
import syntaxtree.ExpressionList;
import syntaxtree.ExpressionRest;
import syntaxtree.FalseLiteral;
import syntaxtree.Identifier;
import syntaxtree.IntegerLiteral;
import syntaxtree.MessageSend;
import syntaxtree.MinusExpression;
import syntaxtree.NotExpression;
import syntaxtree.PlusExpression;
import syntaxtree.PrimaryExpression;
import syntaxtree.ThisExpression;
import syntaxtree.TimesExpression;
import syntaxtree.TrueLiteral;

public class ExpressionVisitor extends GJDepthFirst<String, Object> 
{
	String currentClass;
	Map<String, String> symbolTable;
	Map<String, MJClass> classes;
	List<Map<String, String>> parameters;
	
	/**
	 * @param classes
	 * @param parameters
	 */
	public ExpressionVisitor(Map<String, MJClass> classes, Map<String, String> symbolTable, String currentClass) 
	{
		super();
		this.classes = classes;
		this.symbolTable = symbolTable;
		this.currentClass = currentClass;
		this.parameters = new ArrayList<Map<String, String>>();
	}

	/**
	* f0 -> PrimaryExpression()
	* f1 -> "&&"
	* f2 -> PrimaryExpression()
	*/
	public String visit(AndExpression n, Object argu) 
	{
		String _ret=null;
	    String e0 = n.f0.accept(this, argu);
	    String e1 = n.f2.accept(this, argu);
	    
	    if (e0.equals("boolean") && e1.equals("boolean"))
	    {
	    	_ret = "boolean";
	    }
	    return _ret;
	}

	/**
	* f0 -> PrimaryExpression()
	* f1 -> "<"
	* f2 -> PrimaryExpression()
	*/
	public String visit(CompareExpression n, Object argu) 
	{
		String _ret=null;
	    String e0 = n.f0.accept(this, argu);
	    String e1 = n.f2.accept(this, argu);
	    
	    if (e0.equals("boolean") && e1.equals("boolean"))
	    {
	    	_ret = "boolean";
	    }
	    return _ret;
	}

	/**
	* f0 -> PrimaryExpression()
	* f1 -> "+"
	* f2 -> PrimaryExpression()
	*/
	public String visit(PlusExpression n, Object argu) 
	{
		String _ret=null;
	    String e0 = n.f0.accept(this, argu);
	    String e1 = n.f2.accept(this, argu);
	    
	    if (e0.equals("int") && e1.equals("int"))
	    {
	    	_ret = "int";
	    }
	    return _ret;
	}

	/**
	* f0 -> PrimaryExpression()
	* f1 -> "-"
	* f2 -> PrimaryExpression()
	*/
	public String visit(MinusExpression n, Object argu) 
	{
		String _ret=null;
	    String e0 = n.f0.accept(this, argu);
	    String e1 = n.f2.accept(this, argu);
	    
	    if (e0.equals("int") && e1.equals("int"))
	    {
	    	_ret = "int";
	    }
	    return _ret;
	}

	/**
	* f0 -> PrimaryExpression()
	* f1 -> "*"
	* f2 -> PrimaryExpression()
	*/
	public String visit(TimesExpression n, Object argu) 
	{
		String _ret=null;
	    String e0 = n.f0.accept(this, argu);
	    String e1 = n.f2.accept(this, argu);
	    
	    if (e0.equals("int") && e1.equals("int"))
	    {
	    	_ret = "int";
	    }
	    return _ret;
	}

	/**
	* f0 -> PrimaryExpression()
	* f1 -> "["
	* f2 -> PrimaryExpression()
	* f3 -> "]"
	*/
	public String visit(ArrayLookup n, Object argu) 
	{
		String _ret=null;
	    String e0 = n.f0.accept(this, argu);
	    String e1 = n.f2.accept(this, argu);
	    
	    if (e0.equals("int[]") && e1.equals("int"))
	    {
	    	_ret = "int";
	    }
	    return _ret;
	}

	/**
	* f0 -> PrimaryExpression()
	* f1 -> "."
	* f2 -> "length"
	*/
	public String visit(ArrayLength n, Object argu) 
	{
		String _ret=null;
	    String e0 = n.f0.accept(this, argu);
	    
	    if (e0.equals("int[]"))
	    {
	    	_ret = "int";
	    }
	    return _ret;
	}

	/**
	* f0 -> PrimaryExpression()
	* f1 -> "."
	* f2 -> Identifier()
	* f3 -> "("
	* f4 -> ( ExpressionList() )?
	* f5 -> ")"
	*/
	public String visit(MessageSend n, Object argu) 
	{
		String _ret=null;
	    String e0 = n.f0.accept(this, argu);
	    n.f2.accept(this, argu);
	    String methodName = n.f2.f0.tokenImage;
	    
	    if (e0 != null)
	    {
	    	MJClass mjclass = classes.get(e0);
	    	if (mjclass != null)
	    	{
		    	MJMethod method = mjclass.methods.get(methodName);
		    	
		    	if (method != null)
		    	{
			    	this.parameters.addAll(0, method.parameters);
			    	if (n.f4.accept(this, argu) != null)
			    	{
			    		_ret = method.returnType;
			    	}
		    	}
	    	}
	    }
	    return _ret;
	}

	/**
	* f0 -> Expression()
	* f1 -> ( ExpressionRest() )*
	*/
	public String visit(ExpressionList n, Object argu) 
	{
		String _ret=null;
		Map<String, String> parameter = parameters.get(0);
		
		
		String e0 = n.f0.accept(this, argu);
		if (parameter.containsValue(e0) 
				&& ((parameters.size() <= 1) 
				|| n.f1.accept(this, parameters.subList(1, parameters.size()-1)) != null))
		{
			_ret = e0;
		}
		
	    return _ret;
	}

	/**
	* f0 -> ","
	* f1 -> Expression()
	*/
	public String visit(ExpressionRest n, Object argu) 
	{
		String _ret=null;
		Map<String, String> parameter = parameters.get(0);
		
		
		String e0 = n.f1.accept(this, argu);
		if (parameter.containsValue(e0) 
				&& ((parameters.size() <= 1) 
				|| n.f1.accept(this, parameters.subList(1, parameters.size()-1)) != null))
		{
			_ret = e0;
		}
	    return _ret;
	}
	
	/**
	* f0 -> IntegerLiteral()
	*       | TrueLiteral()
	*       | FalseLiteral()
	*       | Identifier()
	*       | ThisExpression()
	*       | ArrayAllocationExpression()
	*       | AllocationExpression()
	*       | NotExpression()
	*       | BracketExpression()
	*/
	public String visit(PrimaryExpression n, Object argu) 
	{
		String _ret=null;
	    n.f0.accept(this, argu);
	    return _ret;
	}

	/**
	* f0 -> <INTEGER_LITERAL>
	*/
	public String visit(IntegerLiteral n, Object argu) 
	{
		String _ret="int";
	    return _ret;
	}

	/**
	* f0 -> "true"
	*/
	public String visit(TrueLiteral n, Object argu) 
	{
		String _ret = "boolean";
	    return _ret;
	}

	/**
	* f0 -> "false"
	*/
	public String visit(FalseLiteral n, Object argu) 
	{
		String _ret = "boolean";
	    return _ret;
	 }

	/**
	* f0 -> <IDENTIFIER>
	*/
	public String visit(Identifier n, Object argu) 
	{
		String _ret=null;
		String identifier = n.f0.tokenImage;
		_ret = symbolTable.get(identifier);
	    return _ret;
	}

	/**
	* f0 -> "this"
	*/
	public String visit(ThisExpression n, Object argu) 
	{
		String _ret = currentClass;
	    return _ret;
	}

	/**
	* f0 -> "new"
	* f1 -> "int"
	* f2 -> "["
	* f3 -> Expression()
	* f4 -> "]"
	*/
	public String visit(ArrayAllocationExpression n, Object argu) 
	{
		String _ret=null;
		
	    if (n.f3.accept(this, argu).equals("int"))
	    {
	    	_ret = "int[]";
	    }

	    return _ret;
	}

	/**
	* f0 -> "new"
	* f1 -> Identifier()
	* f2 -> "("
	* f3 -> ")"
	*/
	public String visit(AllocationExpression n, Object argu) 
	{
		String _ret=null;
	    n.f1.accept(this, argu);
	    String identifier = n.f1.f0.tokenImage;
	    _ret = symbolTable.get(identifier);
	    return _ret;
	}

	/**
	* f0 -> "!"
	* f1 -> Expression()
	*/
	public String visit(NotExpression n, Object argu) 
	{
		String _ret=null;
	    if (n.f1.accept(this, argu).equals("boolean"))
	    	_ret = "boolean";
	    return _ret;
	}

	/**
	* f0 -> "("
	* f1 -> Expression()
	* f2 -> ")"
	*/
	public String visit(BracketExpression n, Object argu) 
	{
		String _ret=null;
	    _ret = n.f1.accept(this, argu);
	    return _ret;
	}

}