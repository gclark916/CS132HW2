package visitor;

import java.util.List;
import java.util.Map;

import syntaxtree.ArrayAssignmentStatement;
import syntaxtree.AssignmentStatement;
import syntaxtree.IfStatement;
import syntaxtree.PrintStatement;
import syntaxtree.WhileStatement;

public class StatementVisitor extends GJDepthFirst<Boolean, Integer> 
{
	String currentClass;
	Map<String, String> symbolTable;
	Map<String, MJClass> classes;
	ExpressionVisitor expressionVisitor;
	
	/**
	 * @param currentClass
	 * @param symbolTable
	 * @param classes
	 * @param parameters
	 * @param expressionVisitor
	 */
	public StatementVisitor(String currentClass,
			Map<String, String> symbolTable, 
			Map<String, MJClass> classes,
			List<Map<String, String>> parameters,
			ExpressionVisitor expressionVisitor) {
		super();
		this.currentClass = currentClass;
		this.symbolTable = symbolTable;
		this.classes = classes;
		this.expressionVisitor = new ExpressionVisitor(this.classes, this.symbolTable, this.currentClass);
	}

	/**
	* f0 -> Identifier()
	* f1 -> "="
	* f2 -> Expression()
	* f3 -> ";"
	*/
	public Boolean visit(AssignmentStatement n, Integer argu) 
	{
		Boolean _ret=null;
	    String identifier = n.f0.f0.tokenImage;
	    String e0 = n.f2.accept(expressionVisitor, argu);
	    
	    String type = symbolTable.get(identifier);
	    if (type != null && type.equals(e0))
	    {
	    	_ret = true;
	    }
	    return _ret;
	}

	/**
	* f0 -> Identifier()
	* f1 -> "["
	* f2 -> Expression()
	* f3 -> "]"
	* f4 -> "="
	* f5 -> Expression()
	* f6 -> ";"
	*/
	public Boolean visit(ArrayAssignmentStatement n, Integer argu) 
	{
		Boolean _ret=null;
		String identifier = n.f0.f0.tokenImage;
		
	    String e0 = n.f2.accept(expressionVisitor, argu);
	    String e1 = n.f5.accept(expressionVisitor, argu);
	    
	    String type = symbolTable.get(identifier);
	    if (type.equals("int[]") && e0.equals("int") && e1.equals("int"))
	    {
	    	_ret = true;
	    }
	    return _ret;
	}

	/**
	* f0 -> "if"
	* f1 -> "("
	* f2 -> Expression()
	* f3 -> ")"
	* f4 -> Statement()
	* f5 -> "else"
	* f6 -> Statement()
	*/
	public Boolean visit(IfStatement n, Integer argu) 
	{
		Boolean _ret=null;
	    String e0 = n.f2.accept(expressionVisitor, argu);
	    if (e0.equals("boolean") && n.f4.accept(this, argu) && 
	    		n.f6.accept(this, argu))
	    {
	    	_ret = true;
	    }
	    return _ret;
	}

	/**
	* f0 -> "while"
	* f1 -> "("
	* f2 -> Expression()
	* f3 -> ")"
	* f4 -> Statement()
	*/
	public Boolean visit(WhileStatement n, Integer argu) 
	{
		Boolean _ret=null;
	    String e0 = n.f2.accept(expressionVisitor, argu);
	    if (e0.equals("boolean") && n.f4.accept(this, argu))
	    {
	    	_ret = true;
	    }
	    return _ret;
	}

	/**
	* f0 -> "System.out.println"
	* f1 -> "("
	* f2 -> Expression()
	* f3 -> ")"
	* f4 -> ";"
	*/
	public Boolean visit(PrintStatement n, Integer argu) 
	{
		Boolean _ret=null;
	    String e0 = n.f2.accept(expressionVisitor, argu);
	    if (e0.equals("int"))
	    {
	    	_ret = true;
	    }
	    return _ret;
	}
}
