// original code from https://today.java.net/pub/a/today/2008/04/10/source-code-analysis-using-java-6-compiler-apis.html#accessing-the-abstract-syntax-tree-the-compiler-tree-api
// requires JRE JAR file on compile
// javac -cp /usr/lib/jvm/NAME OF YOUR JRE/lib/tools.jar Test.java

import javax.lang.model.element.Element;

import com.sun.source.tree.Scope;
import com.sun.source.tree.ClassTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.VariableTree;
import com.sun.source.util.TreePath;
import com.sun.source.util.TreePathScanner;
import com.sun.source.util.Trees;

/**
 * Methods inherited from class com.sun.source.util.TreeScanner
 * 
 * @Used visitMethod visitVariable visitClass
 * 
 * @Unused reduce, scan, visitAnnotatedType, visitAnnotation, visitArrayAccess,
 *         visitArrayType, visitAssert, visitAssignment, visitBinary,
 *         visitBlock, visitBreak, visitCase, visitCatch, visitCompilationUnit,
 *         visitCompoundAssignment, visitConditionalExpression, visitContinue,
 *         visitDoWhileLoop, visitEmptyStatement, visitEnhancedForLoop,
 *         visitErroneous, visitExpressionStatement, visitForLoop,
 *         visitIdentifier, visitIf, visitImport, visitInstanceOf,
 *         visitIntersectionType, visitLabeledStatement, visitLambdaExpression,
 *         visitLiteral, visitMemberReference, visitMemberSelect,
 *         visitMethodInvocation, visitModifiers, visitNewArray, visitNewClass,
 *         visitOther, visitParameterizedType, visitParenthesized,
 *         visitPrimitiveType, visitReturn, visitSwitch, visitSynchronized,
 *         visitThrow, visitTry, visitTypeCast, visitTypeParameter, visitUnary,
 *         visitUnionType, visitWhileLoop, visitWildcard
 * 
 * @author benste
 * 
 */
public class CodeAnalyzerTreeVisitor extends TreePathScanner<Object, Trees> {

	TreePath path;
	DataInformation d = new DataInformation();

	@Override
	public Object visitClass(ClassTree classTree, Trees trees) {
		System.out.println("\n== This is a visited class");

		// classTree.ge

		path = getCurrentPath();
		System.out.println("path is: " + path);
		Scope scope = trees.getScope(path);
		System.out.println("Scope All: " + scope.toString());
		System.out.println("Scope Enclosing Class: "
				+ scope.getEnclosingClass().toString());
		// System.out.println("Scope enclosing method: "+scope.getEnclosingMethod().toString());
		System.out.println("Scope enclosing"
				+ scope.getEnclosingScope().toString());
		// System.out.println("Scope local elements"+scope.getLocalElements().toString());

		d.setScope(scope.getEnclosingScope().toString());
		System.out.println("finished scope is: " + d.getScope());

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

		path = getCurrentPath();
		System.out.println("path is: " + path);
		Scope scope = trees.getScope(path);
		System.out.println("Scope All: " + scope.toString());
		System.out.println("Scope Enclosing Class: "
				+ scope.getEnclosingClass().toString());
		System.out.println("Scope enclosing method: "
				+ scope.getEnclosingMethod().toString());
		System.out.println("Scope enclosing"
				+ scope.getEnclosingScope().toString());
		// System.out.println("Scope local elements"+scope.getLocalElements().toString());

		d.setName(methodTree.getName().toString());
		d.setDatatype(methodTree.getKind().toString());
		d.setModifiers(methodTree.getModifiers().toString());
		d.setParameters(methodTree.getParameters().toString());
		if (methodTree.getReturnType() != null) {
			d.setReturnType(methodTree.getReturnType().toString());
		} else
			d.setReturnType("");
		System.out.println(d.toString("\t"));

		// System.out.println("Body: "+ methodTree.getBody());

		return super.visitMethod(methodTree, trees);
	}

	@Override
	public Object visitVariable(VariableTree variableTree, Trees trees) {
		System.out.println("\n== This is a visited variable");

		
		
		path = getCurrentPath();
		System.out.println("path is: " + path);
		Scope scope = trees.getScope(path);
		System.out.println("Scope All: " + scope.toString());
		System.out.println("Scope Enclosing Class: "
				+ scope.getEnclosingClass().toString());
		// System.out.println("Scope enclosing method: "+scope.getEnclosingMethod().toString());
		System.out.println("Scope enclosing"
				+ scope.getEnclosingScope().toString());
		// System.out.println("Scope local elements"+scope.getLocalElements().toString());

		
		
		d.setName(variableTree.getName().toString());
		d.setDatatype(variableTree.getKind().toString());
		d.setModifiers(variableTree.getModifiers().toString());
		System.out.println(d.toString("\t\t"));

		return super.visitVariable(variableTree, trees);
	}
}