package visitor;

import syntaxtree.ArrayAssignmentStatement;
import syntaxtree.AssignmentStatement;
import syntaxtree.IfStatement;
import syntaxtree.PrintStatement;
import syntaxtree.WhileStatement;

public class StatementVisitor extends GJDepthFirst<Boolean, Integer> 
{
	/**
	* f0 -> Identifier()
	* f1 -> "="
	* f2 -> Expression()
	* f3 -> ";"
	*/
	public Boolean visit(AssignmentStatement n, Integer argu) 
	{
		Boolean _ret=null;
	    n.f0.accept(this, argu);
	    n.f1.accept(this, argu);
	    n.f2.accept(this, argu);
	    n.f3.accept(this, argu);
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
	    n.f0.accept(this, argu);
	    n.f1.accept(this, argu);
	    n.f2.accept(this, argu);
	    n.f3.accept(this, argu);
	    n.f4.accept(this, argu);
	    n.f5.accept(this, argu);
	    n.f6.accept(this, argu);
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
	    n.f0.accept(this, argu);
	    n.f1.accept(this, argu);
	    n.f2.accept(this, argu);
	    n.f3.accept(this, argu);
	    n.f4.accept(this, argu);
	    n.f5.accept(this, argu);
	    n.f6.accept(this, argu);
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
	    n.f0.accept(this, argu);
	    n.f1.accept(this, argu);
	    n.f2.accept(this, argu);
	    n.f3.accept(this, argu);
	    n.f4.accept(this, argu);
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
	    n.f0.accept(this, argu);
	    n.f1.accept(this, argu);
	    n.f2.accept(this, argu);
	    n.f3.accept(this, argu);
	    n.f4.accept(this, argu);
	    return _ret;
	}
}
