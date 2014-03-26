// original code from https://today.java.net/pub/a/today/2008/04/10/source-code-analysis-using-java-6-compiler-apis.html#accessing-the-abstract-syntax-tree-the-compiler-tree-api
// requires JRE JAR file on compile
// javac -cp /usr/lib/jvm/NAME OF YOUR JRE/lib/tools.jar Test.java
import java.util.Set;

import javax.lang.model.element.Modifier;

import com.sun.source.tree.ClassTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.ModifiersTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.VariableTree;
import com.sun.source.util.TreePath;
//import com.sun.source.tree.VariableTree;
//import com.sun.source.util.TreePath;
import com.sun.source.util.TreePathScanner;
import com.sun.source.util.Trees;

public class CodeAnalyzerTreeVisitor extends TreePathScanner<Object, Trees> {
	@Override
	public Object visitClass(ClassTree classTree, Trees trees) {
		// ---- some code ----
		System.out.println("\n== This is a visited class");
		TreePath path = getCurrentPath();
		
		//trees.ge
		
		DataInformation d = new DataInformation();
		d.setName(classTree.getSimpleName().toString());
		d.setDatatype(classTree.getKind().toString());
		d.setModifiers(classTree.getModifiers().toString());
		// Scope
		// ModifiersTree mods = genericTree.getModifiers();
		// if (((String) mods).contains(Modifier.PRIVATE)) {
		// scope = (".");
		// }
		// genericTree.getPath();
		
		d.setScope("*");
		// (mods.contains(Modifier.PUBLIC) ||
		// mods.contains(Modifier.PROTECTED))

		// others
		d.setParameters(classTree.getModifiers().toString());

		System.out.println(d.toString(""));

		return super.visitClass(classTree, trees);
	}

	@Override
	public Object visitMethod(MethodTree methodTree, Trees trees) {
		System.out.println("\n== This is a visited method");
		DataInformation d = new DataInformation();
		d.setName(methodTree.getName().toString());
		d.setDatatype(methodTree.getKind().toString());
		d.setModifiers(methodTree.getModifiers().toString());
		d.setParameters(methodTree.getParameters().toString());
		if (methodTree.getReturnType() != null){
			d.setReturnType(methodTree.getReturnType().toString());
		}
		else d.setReturnType("");		
		System.out.println(d.toString("\t"));
		
		//System.out.println("Body: "+ methodTree.getBody());
		
		return super.visitMethod(methodTree, trees);
	}
	@Override
	public Object visitVariable(VariableTree variableTree, Trees trees) {
		System.out.println("\n== This is a visited variable");
		
		DataInformation d = new DataInformation();
		d.setName(variableTree.getName().toString());
		d.setDatatype(variableTree.getKind().toString());
		d.setModifiers(variableTree.getModifiers().toString());
		System.out.println(d.toString("\t\t"));
		
		return super.visitVariable(variableTree, trees);		
	}
}