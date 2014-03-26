// original code from https://today.java.net/pub/a/today/2008/04/10/source-code-analysis-using-java-6-compiler-apis.html#accessing-the-abstract-syntax-tree-the-compiler-tree-api
// requires JRE JAR file on compile
// javac -cp /usr/lib/jvm/NAME OF YOUR JRE/lib/tools.jar Test.java

import javax.lang.model.element.Element;

import com.sun.source.tree.ModifiersTree;
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

	@Override
	public Object visitClass(ClassTree classTree, Trees trees) {
		DataInformation d = new DataInformation();
		System.out.println("\n== This is a visited class");

		// preset for Datatype
		d.setDatatype(classTree.getKind().toString());
		ModifiersTree m = classTree.getModifiers();

		// Special Methods called Class Methods
		if (classTree.toString().contains("final")) {
			// TODO not included in this version
		}
		/*
		 * Scope of classes
		 * 
		 * local scope = enclosing class w/o own name (split by .)
		 * 
		 * if * PUBLIC = parent scope = OR if no parent = File else if PRIVATE ?
		 * scope = local scope else if n/a scope = local scope + packages else
		 * if PROTECTED scope = local scope + package + subclass)
		 * 
		 * 
		 * 
		 * SCOPE Setting Part
		 * 
		 * //d.setScope(scope.getEnclosingScope().toString());
		 * //System.out.println("finished scope is: " + d.getScope());
		 */

		d.setName(classTree.getSimpleName().toString());
		d.setModifiers(classTree.getModifiers().toString());
		d.setReturnType("n/a");
		d.setParameters("n/a");

		// DEBUG printout of element
		System.out.println(d.toString(""));

		return super.visitClass(classTree, trees);
	}

	@Override
	public Object visitMethod(MethodTree methodTree, Trees trees) {
		DataInformation d = new DataInformation();
		System.out.println("\n== This is a visited method");

		// preset for Datatype
		d.setDatatype(methodTree.getKind().toString());
		ModifiersTree m = methodTree.getModifiers();
		// Special Methods called Class Methods
		if (m.toString().contains("static")) {
			d.setDatatype("CLASS " + d.getDatatype() + "(Static Method)");
		}
		// Special Methods having final can not be overwritten
		else if (methodTree.toString().contains("final")) {
			// TODO not included in this version
		}

		/*
		 * Scope of methods
		 * 
		 * local scope = if exists enclosing method otherwise enclosing class
		 * 
		 * if * PUBLIC = parent scope else if PRIVATE ? scope = local scope else
		 * if n/a scope = local scope + packages else if PROTECTED scope = local
		 * scope + package + subclass)
		 */

		// Check if its a Constructor
		if(methodTree.getName().toString().equals("<init>")){
			d.setName(methodTree.getEnclosingClass())
		}else {
			d.setName(methodTree.getName().toString());
		}
		
		d.setModifiers(methodTree.getModifiers().toString());
		d.setParameters(methodTree.getParameters().toString());
		if (methodTree.getReturnType() != null) {
			d.setReturnType(methodTree.getReturnType().toString());
		} else {
			d.setReturnType("Special Element");
		}

		// DEBUG printout of element
		System.out.println(d.toString("\t"));

		return super.visitMethod(methodTree, trees);
	}

	@Override
	public Object visitVariable(VariableTree variableTree, Trees trees) {
		DataInformation d = new DataInformation();
		System.out.println("\n== This is a visited variable");

		path = getCurrentPath();
		// System.out.println("path is: " + path);
		Scope scope = trees.getScope(path);
		System.out.println(path);
		System.out.println(path.iterator());

		// preset for Datatype
		d.setDatatype(variableTree.getKind().toString());
		ModifiersTree m = variableTree.getModifiers();

		// some debugging
		System.out.println(">>>DEBUG modifier : " + m.toString());
		System.out.println("public" + m.toString().contains("public"));
		System.out.println("private" + m.toString().contains("private"));
		System.out.println("protected" + m.toString().contains("protected"));

		// Local Variables = do always have an enclosing method scope
		if (scope.getEnclosingMethod() != null) {
			d.setDatatype("LOCAL " + d.getDatatype());
		}
		// Constants always have the "final" keyword and no enclosing method
		else if (m.toString().contains("final")) {
			// && (scope.getEnclosingMethod() == null) {
			d.setDatatype("CONSTANT " + d.getDatatype());
		}
		// Instance Variable (Non-Static Field) = has no enclosing method only a
		// class, furthermore no static modfier
		else if (!(m.toString().contains("static"))) {
			// && (scope.getEnclosingMethod() == null) {
			d.setDatatype("INSTANCE " + d.getDatatype() + "(Non-Static Field)");
		}
		// Class Variable (Static Field) = has no enclosing method only a class,
		// furthermore MUST HAVE static modfier
		else if (m.toString().contains("static")) {
			// && (scope.getEnclosingMethod() == null) {
			d.setDatatype("CLASS " + d.getDatatype() + "(Static Field)");
		}

		/*
		 * //TODO ==Parameters parent DataInformation has
		 * .getParameters().contains(varname) * {
		 * 
		 * 
		 * SCOPE ...
		 * 
		 * //TODO } / * + d.getDatatype() if PUBLIC = parent scope else if
		 * PRIVATE ? scope = enclosing class as scope else if n/a scope =
		 * enclosing class as scope + package else if PROTECTED scope =
		 * enclosing class as scope + package + subclass)
		 */

		d.setName(variableTree.getName().toString());
		d.setModifiers(variableTree.getModifiers().toString());
		d.setReturnType("n/a");
		d.setParameters("n/a");

		// DEBUG printout of element
		System.out.println(d.toString("\t"));

		return super.visitVariable(variableTree, trees);
	}
}