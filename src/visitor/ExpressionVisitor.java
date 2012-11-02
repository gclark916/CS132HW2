package visitor;

import syntaxtree.AndExpression;
import syntaxtree.ArrayLength;
import syntaxtree.ArrayLookup;
import syntaxtree.CompareExpression;
import syntaxtree.MessageSend;
import syntaxtree.MinusExpression;
import syntaxtree.PlusExpression;
import syntaxtree.TimesExpression;

public class ExpressionVisitor extends GJDepthFirst<String, Integer> 
{
	/**
	* f0 -> PrimaryExpression()
	* f1 -> "&&"
	* f2 -> PrimaryExpression()
	*/
	public String visit(AndExpression n, Integer argu) 
	{
		String _ret=null;
	    n.f0.accept(this, argu);
	    n.f1.accept(this, argu);
	    n.f2.accept(this, argu);
	    return _ret;
	}

	/**
	* f0 -> PrimaryExpression()
	* f1 -> "<"
	* f2 -> PrimaryExpression()
	*/
	public String visit(CompareExpression n, Integer argu) 
	{
		String _ret=null;
	    n.f0.accept(this, argu);
	    n.f1.accept(this, argu);
	    n.f2.accept(this, argu);
	    return _ret;
	}

	/**
	* f0 -> PrimaryExpression()
	* f1 -> "+"
	* f2 -> PrimaryExpression()
	*/
	public String visit(PlusExpression n, Integer argu) 
	{
		String _ret=null;
	    n.f0.accept(this, argu);
	    n.f1.accept(this, argu);
	    n.f2.accept(this, argu);
	    return _ret;
	}

	/**
	* f0 -> PrimaryExpression()
	* f1 -> "-"
	* f2 -> PrimaryExpression()
	*/
	public String visit(MinusExpression n, Integer argu) 
	{
		String _ret=null;
	    n.f0.accept(this, argu);
	    n.f1.accept(this, argu);
	    n.f2.accept(this, argu);
	    return _ret;
	}

	/**
	* f0 -> PrimaryExpression()
	* f1 -> "*"
	* f2 -> PrimaryExpression()
	*/
	public String visit(TimesExpression n, Integer argu) 
	{
		String _ret=null;
	    n.f0.accept(this, argu);
	    n.f1.accept(this, argu);
	    n.f2.accept(this, argu);
	    return _ret;
	}

	/**
	* f0 -> PrimaryExpression()
	* f1 -> "["
	* f2 -> PrimaryExpression()
	* f3 -> "]"
	*/
	public String visit(ArrayLookup n, Integer argu) 
	{
		String _ret=null;
	    n.f0.accept(this, argu);
	    n.f1.accept(this, argu);
	    n.f2.accept(this, argu);
	    n.f3.accept(this, argu);
	    return _ret;
	}

	/**
	* f0 -> PrimaryExpression()
	* f1 -> "."
	* f2 -> "length"
	*/
	public String visit(ArrayLength n, Integer argu) 
	{
		String _ret=null;
	    n.f0.accept(this, argu);
	    n.f1.accept(this, argu);
	    n.f2.accept(this, argu);
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
	public String visit(MessageSend n, Integer argu) 
	{
		String _ret=null;
	    n.f0.accept(this, argu);
	    n.f1.accept(this, argu);
	    n.f2.accept(this, argu);
	    n.f3.accept(this, argu);
	    n.f4.accept(this, argu);
	    n.f5.accept(this, argu);
	    return _ret;
	}
}
