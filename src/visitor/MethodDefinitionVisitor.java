package visitor;

import java.util.Map;
import java.util.Set;

import syntaxtree.MethodDeclaration;

public class MethodDefinitionVisitor extends GJDepthFirst<Map<String, MJMethod>, Integer> 
{
	Map<String, MJClass> classes;

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
		Map<String, MJMethod> _ret=null;
	    n.f0.accept(this, argu);
	    
	    Set<String> classNames = classes.keySet();
	    TypeVisitor typeVisitor = new TypeVisitor(classNames);
	    String declaredReturnType = n.f1.accept(typeVisitor, null);
	    
	    n.f2.accept(this, argu);
	    String methodName = n.f2.f0.tokenImage;
	    
	    n.f3.accept(this, argu);
	    
	    // Use different visitors for parameters and variables because variables' 
	    // type environment can override parameters' type environment
	    FormalParameterAndVarDeclarationVisitor formalParameterVisitor = new FormalParameterAndVarDeclarationVisitor(classNames);
	    Map<String, String> formalParameters = n.f4.accept(formalParameterVisitor, null);
	    
	    n.f5.accept(this, argu);
	    n.f6.accept(this, argu);
	    
	    // Uses the same visitor as before to ensure there is no overloading of the parameters
	    // TODO: check whether this distinction is necessary
	    FormalParameterAndVarDeclarationVisitor varDeclarationVisitor = new FormalParameterAndVarDeclarationVisitor(classNames);
	    Map<String, String> variables = n.f7.accept(varDeclarationVisitor, null);
	    
	    // Variables override parameters
	    formalParameters.putAll(variables);
	    
	    n.f8.accept(this, argu);
	    n.f9.accept(this, argu);
	    n.f10.accept(this, argu);
	    n.f11.accept(this, argu);
	    n.f12.accept(this, argu);
	    return _ret;
	}
}
