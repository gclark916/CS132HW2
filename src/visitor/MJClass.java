package visitor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MJClass {
	public Map<String, MJMethod> methods;	// Map of method name-> Method. Assumes no overloading of method names.
	public Map<String, String> fields;	// Map of field name -> Type name
	public String name;
	public MJClass parentClass;
	
	/**
	 * @param methods
	 * @param fields
	 * @param name
	 * @param parentClass
	 */
	public MJClass(Map<String, MJMethod> methods, Map<String, String> fields,
			String name, MJClass parentClass) {
		super();
		this.methods = methods;
		this.fields = fields;
		this.name = name;
		this.parentClass = parentClass;
	}

	public Map<String, MJMethod> getMethods() 
	{
		Map<String, MJMethod> completeMethods = new HashMap<String, MJMethod>();
		
		if (parentClass != null)
		{
			completeMethods.putAll(parentClass.methods);
			completeMethods.putAll(methods);
		}
		else
		{
			completeMethods.putAll(methods);
		}
		return completeMethods;
	}
	
	public Map<String, String> getFields() 
	{
		Map<String, String> completeFields = new HashMap<String, String>();
		
		if (parentClass != null)
		{
			completeFields.putAll(parentClass.fields);
			completeFields.putAll(fields);
		}
		else
		{
			completeFields.putAll(fields);
		}
		return completeFields;
	}

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
