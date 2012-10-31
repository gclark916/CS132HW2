import java.io.FileInputStream;
import java.io.FileNotFoundException;

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
}
