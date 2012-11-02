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
			Set<String> classNames = classNameToParent.keySet();
			SecondPassVisitor secondPassVisitor = new SecondPassVisitor(classNames);
			Map<String, MJClass> classes = root.accept(secondPassVisitor, null);
			if (classes != null)
			{
				classes = correctClassParents(classes, classNameToParent);
			}
			else
			{
				System.out.println("Type error");
				return;
			}
			
			// check method overloading. Member overshadowing is fine
			if (noMethodOverloading(classes))
			{
				
			}
			else
			{
				System.out.println("Type error");
				return;
			}
			
			// Third pass check all method bodies(parameters, variables, statements, expressions, return type)
			VarDeclarationVisitor visitor = new VarDeclarationVisitor(null);
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
	
	/**
	 * Corrects the parent class for every class.
	 * @param inputClasses
	 * @param classToParent
	 * @return
	 */
	
	private static Map<String, MJClass> correctClassParents(Map<String, MJClass> inputClasses, Map<String, String> classToParent)
	{
		Map<String, MJClass> classes = inputClasses;
		Set<String> classNames = classes.keySet();
		Iterator<String> classIterator = classNames.iterator();
		
		while(classIterator.hasNext())
		{
			String className = classIterator.next();
			String parentName = classToParent.get(className);
			
			if (parentName != null)
			{
				MJClass mjclass = classes.get(className);
				MJClass parentClass = classes.get(parentName);
				mjclass.parentClass = parentClass;
			}
		}
		
		return classes;
	}
	
	private static boolean noMethodOverloading(Map<String, MJClass> classes)
	{
		boolean _ret = true;
		Set<String> classNames = classes.keySet();
		Iterator<String> classIterator = classNames.iterator();
		
		while(classIterator.hasNext() && _ret == true)
		{
			String className = classIterator.next();
			MJClass currentClass = classes.get(className);
			Set<String> methodNames = currentClass.methods.keySet();
			
			while (currentClass.parentClass != null)
			{
				int currentNumberDistinctNames = methodNames.size();
				Set<String> parentMethodNames = currentClass.parentClass.methods.keySet();
				int numberDistinctParentMethodNames = parentMethodNames.size();
				
				methodNames.addAll(parentMethodNames);
				if (methodNames.size() < currentNumberDistinctNames + numberDistinctParentMethodNames)
				{
					_ret = false;
					break;
				}
				
				currentClass = currentClass.parentClass;
			}
		}
		
		return _ret;
	}
}
