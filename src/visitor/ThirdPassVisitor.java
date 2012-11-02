package visitor;

import java.util.Map;
import java.util.Set;

import syntaxtree.ClassDeclaration;
import syntaxtree.ClassExtendsDeclaration;
import syntaxtree.Goal;
import syntaxtree.MainClass;

public class ThirdPassVisitor extends GJDepthFirst<Boolean, Integer> 
{
	Map<String, MJClass> classes;
	
	/**
	 * @param classes
	 */
	public ThirdPassVisitor(Map<String, MJClass> classes) {
		super();
		this.classes = classes;
	}

	/**
	* f0 -> MainClass()
	* f1 -> ( TypeDeclaration() )*
	* f2 -> <EOF>
	*/
	public Boolean visit(Goal n, Integer argu) 
	{
		Boolean _ret = false;
	    if (n.f0.accept(this, argu) && n.f1.accept(this, argu))
	    {
	    	_ret = true;
	    }
	    return _ret;
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
	public Boolean visit(MainClass n, Integer argu) 
	{
		Boolean _ret = null;
	    String name = n.f1.f0.tokenImage;
	    
	    Set<String> classNames = classes.keySet();
	    VarDeclarationVisitor varDeclarationVistor = new VarDeclarationVisitor(classNames);
	    Map<String, String> symbolTable = varDeclarationVistor.visit(n.f14, argu);
	    
	    if (symbolTable != null)
	    {
	    	StatementVisitor statementVisitor = new StatementVisitor(name, symbolTable, classes);
	    	_ret = statementVisitor.visit(n.f15, argu);
	    }
	    
	    return _ret;
	}

	/**
	* f0 -> "class"
	* f1 -> Identifier()
	* f2 -> "{"
	* f3 -> ( VarDeclaration() )*
	* f4 -> ( MethodDeclaration() )*
	* f5 -> "}"
	*/
	public Boolean visit(ClassDeclaration n, Integer argu) 
	{
		Boolean _ret = null;
		
		String name = n.f1.f0.tokenImage;
		
		Set<String> classNames = classes.keySet();
	    VarDeclarationVisitor varDeclarationVistor = new VarDeclarationVisitor(classNames);
	    Map<String, String> symbolTable = varDeclarationVistor.visit(n.f3, argu);
	    
	    if (symbolTable != null)
	    {
	    	MethodDefinitionVisitor methodDefinitionVisitor = new MethodDefinitionVisitor(classes, symbolTable, name);
	    	_ret = methodDefinitionVisitor.visit(n.f4, argu);
	    }
	    
	    return _ret;
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
	* @return a Map of class name -> class object. parent is not resolved.
	*/
	public Boolean visit(ClassExtendsDeclaration n, Integer argu) 
	{
		Boolean _ret = null;
	    String name = n.f1.f0.tokenImage;
	    
	    String parent = n.f3.f0.tokenImage;
	    
	    Map<String, String> symbolTable=null;
	    if (name != null && classes.get(name) != null)
	    {
	    	symbolTable = classes.get(name).getFields();
	    }
	    
		//Set<String> classNames = classes.keySet();
	   // VarDeclarationVisitor varDeclarationVistor = new VarDeclarationVisitor(classNames);
	    //Map<String, String> symbolTable = varDeclarationVistor.visit(n.f3, argu);
	    
	    if (symbolTable != null)
	    {
	    	MethodDefinitionVisitor methodDefinitionVisitor = new MethodDefinitionVisitor(classes, symbolTable, name);
	    	_ret = methodDefinitionVisitor.visit(n.f4, argu);
	    }
	    
	    return _ret;
	}
}
