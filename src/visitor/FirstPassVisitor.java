package visitor;

import java.util.HashMap;
import java.util.Map;

import syntaxtree.ClassDeclaration;
import syntaxtree.ClassExtendsDeclaration;
import syntaxtree.Goal;
import syntaxtree.MainClass;

public class FirstPassVisitor extends GJDepthFirst<Map<String, String>, Integer> 
{
	Map<String, String> classNameToParentName;
	
	/**
	 * @param classNameToParentName
	 */
	public FirstPassVisitor() {
		super();
		this.classNameToParentName = new HashMap<String, String>();
	}

	/**
	* f0 -> MainClass()
	* f1 -> ( TypeDeclaration() )*
	* f2 -> <EOF>
	*/
	public Map<String, String> visit(Goal n, Integer argu) 
	{
	    n.f0.accept(this, argu);
	    n.f1.accept(this, argu);
	    n.f2.accept(this, argu);
	    return classNameToParentName;
	}
	   
	/**
	* f0 -> "class"
	* f1 -> Identifier()
	* f2 -> "{"
	* f3 -> "public"
	* f4 -> "static"
	* f5 -> "void"
	* f6 -> "main"
	* f7 -> "("
	* f8 -> "String"
	* f9 -> "["
	* f10 -> "]"
	* f11 -> Identifier()
	* f12 -> ")"
	* f13 -> "{"
	* f14 -> ( VarDeclaration() )*
	* f15 -> ( Statement() )*
	* f16 -> "}"
	* f17 -> "}"
	*/
	public Map<String, String> visit(MainClass n, Integer argu) 
	{
	    n.f0.accept(this, argu);
	    n.f1.accept(this, argu);
	    n.f2.accept(this, argu);
	    n.f3.accept(this, argu);
	    n.f4.accept(this, argu);
	    n.f5.accept(this, argu);
	    n.f6.accept(this, argu);
	    n.f7.accept(this, argu);
	    n.f8.accept(this, argu);
	    n.f9.accept(this, argu);
	    n.f10.accept(this, argu);
	    n.f11.accept(this, argu);
	    n.f12.accept(this, argu);
	    n.f13.accept(this, argu);
	    n.f14.accept(this, argu);
	    n.f15.accept(this, argu);
	    n.f16.accept(this, argu);
	    n.f17.accept(this, argu);
	    
	    String name = n.f1.f0.tokenImage;
	    if (classNameToParentName != null && !classNameToParentName.containsKey(name))
	    {
	    	classNameToParentName.put(name, null);
	    }
	    else
	    {
	    	classNameToParentName = null;
	    }
	    	
	    return classNameToParentName;
	}

	/**
	* f0 -> "class"
	* f1 -> Identifier()
	* f2 -> "{"
	* f3 -> ( VarDeclaration() )*
	* f4 -> ( MethodDeclaration() )*
	* f5 -> "}"
	*/
	public Map<String, String> visit(ClassDeclaration n, Integer argu) 
	{
	    n.f0.accept(this, argu);
	    n.f1.accept(this, argu);
	    n.f2.accept(this, argu);
	    n.f3.accept(this, argu);
	    n.f4.accept(this, argu);
	    n.f5.accept(this, argu);
	    
	    String name = n.f1.f0.tokenImage;
	    if (classNameToParentName != null && !classNameToParentName.containsKey(name))
	    {
	    	classNameToParentName.put(name, null);
	    }
	    else
	    {
	    	classNameToParentName = null;
	    }
	    	
	    return classNameToParentName;
	}

	/**
	* f0 -> "class"
	* f1 -> Identifier()
	* f2 -> "extends"
	* f3 -> Identifier()
	* f4 -> "{"
	* f5 -> ( VarDeclaration() )*
	* f6 -> ( MethodDeclaration() )*
	* f7 -> "}"
	*/
	public Map<String, String> visit(ClassExtendsDeclaration n, Integer argu) 
	{
	    n.f0.accept(this, argu);
	    n.f1.accept(this, argu);
	    n.f2.accept(this, argu);
	    n.f3.accept(this, argu);
	    n.f4.accept(this, argu);
	    n.f5.accept(this, argu);
	    n.f6.accept(this, argu);
	    n.f7.accept(this, argu);
	    
	    String name = n.f1.f0.tokenImage;
	    String parent = n.f3.f0.tokenImage;
	    if (classNameToParentName != null && !classNameToParentName.containsKey(name))
	    {
	    	classNameToParentName.put(name, parent);
	    }
	    else
	    {
	    	classNameToParentName = null;
	    }
	    	
	    return classNameToParentName;
	}
}
