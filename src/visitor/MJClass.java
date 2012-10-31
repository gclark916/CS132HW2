package visitor;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MJClass {
	Map<String, MJMethod> methods;	// Map of method name-> Method. Assumes no overloading of method names.
	Map<String, String> members;	// Map of member name -> Type name
	String name;
	MJClass parentClass;
	
	static Set<String> getClassNames(Set<MJClass> classes)
	{
		Set<String> classNames = new HashSet<String>();
		Iterator<MJClass> classIterator = classes.iterator();
		while (classIterator.hasNext())
		{
			MJClass mjclass = classIterator.next();
			classNames.add(mjclass.name);
		}
		return classNames;
	}
}
