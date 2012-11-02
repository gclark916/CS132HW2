package visitor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import syntaxtree.ClassDeclaration;
import syntaxtree.ClassExtendsDeclaration;
import syntaxtree.Goal;
import syntaxtree.MainClass;

public class SecondPassVisitor extends GJDepthFirst<Map<String, MJClass>, Integer> 
{
	Map<String, MJClass> nameToClass;
	Set<String> classNames;
	
	/**
	 * @param nameToClass
	 * @param classNames
	 */
	public SecondPassVisitor(Set<String> classNames) {
		super();
		this.nameToClass = new HashMap<String, MJClass>();
		this.classNames = classNames;
	}

	/**
	* f0 -> MainClass()
	* f1 -> ( TypeDeclaration() )*
	* f2 -> <EOF>
	*/
	public Map<String, MJClass> visit(Goal n, Integer argu) 
	{
	    n.f0.accept(this, argu);
	    n.f1.accept(this, argu);
	    n.f2.accept(this, argu);
	    return nameToClass;
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
	public Map<String, MJClass> visit(MainClass n, Integer argu) 
	{
	    String name = n.f1.f0.tokenImage;
	    MJClass mainClass = new MJClass(null, null, name, null);
	    nameToClass.put(name, mainClass);
	    
	    return nameToClass;
	}

	/**
	* f0 -> "class"
	* f1 -> Identifier()
	* f2 -> "{"
	* f3 -> ( VarDeclaration() )*
	* f4 -> ( MethodDeclaration() )*
	* f5 -> "}"
	*/
	public Map<String, MJClass> visit(ClassDeclaration n, Integer argu) 
	{
		String name = n.f1.f0.tokenImage;
	    
	    FormalParameterAndVarDeclarationVisitor fieldsVisitor = new FormalParameterAndVarDeclarationVisitor(classNames);
	    Map<String, String> fields = fieldsVisitor.visit(n.f3, null);

	    MethodDeclarationVisitor methodsVisitor = new MethodDeclarationVisitor(classNames);
	    Map<String, MJMethod> methods = methodsVisitor.visit(n.f4, null);

	    if (nameToClass.containsKey(name))
	    {
	    	nameToClass = null;
	    }
	    else
	    {
	    	MJClass mjclass = new MJClass(methods, fields, name, null);
	    	nameToClass.put(name, mjclass);
	    }
	    	
	    return nameToClass;
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
	public Map<String, MJClass> visit(ClassExtendsDeclaration n, Integer argu) 
	{
	    String name = n.f1.f0.tokenImage;
	    
	    // TODO: parent is not added
	    //String parent = n.f3.f0.tokenImage;
	    
	    FormalParameterAndVarDeclarationVisitor fieldsVisitor = new FormalParameterAndVarDeclarationVisitor(classNames);
	    Map<String, String> fields = fieldsVisitor.visit(n.f5, null);

	    MethodDeclarationVisitor methodsVisitor = new MethodDeclarationVisitor(classNames);
	    Map<String, MJMethod> methods = methodsVisitor.visit(n.f6, null);
	    
	    if (nameToClass.containsKey(name))
	    {
	    	nameToClass = null;
	    }
	    else
	    {
	    	MJClass mjclass = new MJClass(methods, fields, name, null);
	    	nameToClass.put(name, mjclass);
	    }
	    	
	    return nameToClass;
	}
}
