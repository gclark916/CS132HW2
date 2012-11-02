package visitor;

import java.util.List;
import java.util.Map;
import java.util.Set;

import syntaxtree.MethodDeclaration;

public class MethodDefinitionVisitor extends GJDepthFirst<Boolean, Integer> 
{
	Map<String, MJClass> classes;
	Map<String, String> symbolTable;	// Originally contains just the Class's fields
	String currentClass;

	/**
	 * @param classes
	 * @param symbolTable
	 * @param currentClass
	 */
	public MethodDefinitionVisitor(Map<String, MJClass> classes,
			Map<String, String> symbolTable, String currentClass) {
		super();
		this.classes = classes;
		this.symbolTable = symbolTable;
		this.currentClass = currentClass;
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
	public Boolean visit(MethodDeclaration n, Integer argu) 
	{
		Boolean _ret = null;
	    
	    Set<String> classNames = classes.keySet();
	    TypeVisitor typeVisitor = new TypeVisitor(classNames);
	    String returnType = n.f1.accept(typeVisitor, null);
	    
	    // Use different visitors for parameters and variables because variables' 
	    // type environment can override parameters' type environment
	    FormalParameterVisitor formalParameterVisitor = new FormalParameterVisitor(classNames);
	    List<Map<String, String>> formalParameters = formalParameterVisitor.visit(n.f4, argu);
	    
	    VarDeclarationVisitor varDeclarationVisitor = new VarDeclarationVisitor(classNames);
	    Map<String, String> variables = varDeclarationVisitor.visit(n.f7, argu);
	    
	    // parameters overshadow Class fields
	    int parameterCount = formalParameters.size();
	    for (int parameterIndex = 0; parameterIndex < parameterCount; parameterIndex++)
	    {
	    	Map<String, String> parameter = formalParameters.get(parameterIndex);
	    	symbolTable.putAll(parameter);
	    }
	    
	    // local variables overshadow parameters
	    symbolTable.putAll(variables);
	    
	    StatementVisitor statementVisitor = new StatementVisitor(currentClass, symbolTable, classes);
	    if (statementVisitor.visit(n.f8, argu))
	    {
	    	ExpressionVisitor expressionVisitor = new ExpressionVisitor(classes, symbolTable, currentClass);
	    	String e0 = expressionVisitor.visit(n.f10, argu);
	    	if (e0.equals(returnType))
	    	{
	    		_ret = true;
	    	}
	    }
	    return _ret;
	}
}
