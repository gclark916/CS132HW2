package visitor;

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
