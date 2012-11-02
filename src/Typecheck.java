import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import syntaxtree.*;
import visitor.*;

public class Typecheck {
	
	public static void main(String [] args) 
	{
		try {
			FileInputStream fileInput = new FileInputStream("test/Factorial.java");
			System.setIn(fileInput);
			Node root = new MiniJavaParser(System.in).Goal();
			System.out.println("Program parsed successfully");
			
			// First pass discover class names and extends relations
			FirstPassVisitor firstPassVisitor= new FirstPassVisitor();
			Map<String, String> classNameToParent = root.accept(firstPassVisitor, null);
			if (classNameToParent != null)
			{
				if (!typeCheckClassNamesAndParents(classNameToParent))
				{
					System.out.println("Type error");
					return;
				}
			}
			else
			{
				System.out.println("Type error");
				return;
			}
			
			// Second pass check method names and types and member types
			// derived classes should not have member/methods of different types than parent
			// edit: maybe they can
			//TODO: check if subclasses overriding parent classes is okay
			
			// Third pass check all method bodies(parameters, variables, statements, expressions, return type)
			FormalParameterAndVarDeclarationVisitor visitor = new FormalParameterAndVarDeclarationVisitor(null);
			root.accept(visitor, null);
		}
		catch (ParseException e) 
		{
			System.out.println(e.toString());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static boolean typeCheckClassNamesAndParents(Map<String, String> classNameToParent)
	{
		boolean _ret = true;
		
		Set<String> classNames = classNameToParent.keySet();
		Iterator<String> classIterator = classNames.iterator();
		
		// Make sure every class has a terminating ancestry
		while (classIterator.hasNext() && _ret == true)
		{
			String currentClass = classIterator.next();
			Set<String> childClasses = new HashSet<String>();
			childClasses.add(currentClass);
			String parent;
			
			// Iterate until the extends loops to an already-visited class or no parent
			while((parent = classNameToParent.get(currentClass)) != null)
			{
				if (childClasses.contains(parent))
				{
					_ret = false;
					break;
				}
				else
				{
					currentClass = parent; 
				}
			}
		}
		
		return _ret;
	}
	
}
