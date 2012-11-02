package visitor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import syntaxtree.MethodDeclaration;

public class MethodDeclarationVisitor extends GJDepthFirst<Map<String, MJMethod>, Integer> {
	Set<String> classNames;
	Map<String, MJMethod> methods;

	/**
	 * @param classNames
	 * @param methods
	 */
	public MethodDeclarationVisitor(Set<String> classNames) 
	{
		super();
		this.classNames = classNames;
		this.methods = new HashMap<String, MJMethod>();
	}

	/**
	* f0 -> "public"
	* f1 -> Type()
	* f2 -> Identifier()
	* f3 -> "("
	* f4 -> ( FormalParameterList() )?
	* f5 -> ")"
	* f6 -> "{"
	* f7 -> ( VarDeclaration() )*
	* f8 -> ( Statement() )*
	* f9 -> "return"
	* f10 -> Expression()
	* f11 -> ";"
	* f12 -> "}"
	*/
	public Map<String, MJMethod> visit(MethodDeclaration n, Integer argu) 
	{	    
	    TypeVisitor typeVisitor = new TypeVisitor(classNames);
	    String returnType = n.f1.accept(typeVisitor, null);
	    
	    String methodName = n.f2.f0.tokenImage;
	    
	    FormalParameterAndVarDeclarationVisitor formalParameterVisitor = new FormalParameterAndVarDeclarationVisitor(classNames);
	    Map<String, String> formalParameters = n.f4.accept(formalParameterVisitor, null);
	    
	    // Ensure a method with the same name hasn't already been added
	    if (methods != null && methods.containsKey(methodName))
	    {
	    	methods = null;
	    }
	    else
	    {
	    	MJMethod method = new MJMethod(methodName, formalParameters, returnType);
	    	methods.put(methodName, method);
	    }

	    return methods;
	}
}
