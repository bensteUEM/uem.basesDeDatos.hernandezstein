// original code from https://today.java.net/pub/a/today/2008/04/10/source-code-analysis-using-java-6-compiler-apis.html#accessing-the-abstract-syntax-tree-the-compiler-tree-api
// requires JRE JAR file on compile
// javac -cp /usr/lib/jvm/NAME OF YOUR JRE/lib/tools.jar Test.java

import java.util.List;

import javax.lang.model.element.Element;
//http://docs.oracle.com/javase/8/docs/jdk/api/javac/tree/com/sun/source/tree/MethodTree.html
import com.sun.source.tree.ModifiersTree;
import com.sun.source.tree.Scope;
import com.sun.source.tree.ClassTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.Tree;
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
		System.out.println("\n== This is a visited class");

		DataInformation d = new DataInformation();
		TreePath path = getCurrentPath();
		Scope scope = trees.getScope(path);

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
		System.out.println("\n== This is a visited method");

		DataInformation d = new DataInformation();
		TreePath path = getCurrentPath();
		Scope scope = trees.getScope(path);

		// preset for Datatype
		d.setDatatype(methodTree.getKind().toString());
		ModifiersTree m = methodTree.getModifiers();
		// Special Methods called Class Methods
		if (m.toString().contains("static")) {
			d.setDatatype("CLASS " + d.getDatatype() + " (Static Method)");
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
		if (methodTree.getName().toString().equals("<init>")) {
			d.setName(scope.getEnclosingClass().getSimpleName().toString());
			d.setDatatype("CONSTRUCTOR " + d.getDatatype());
		} else {
			d.setName(methodTree.getName().toString());
		}

		d.setModifiers(methodTree.getModifiers().toString());
		d.setParameters(methodTree.getParameters().toString());
		if (methodTree.getReturnType() != null) {
			d.setReturnType(methodTree.getReturnType().toString());
		} else {
			d.setReturnType("");
		}

		// DEBUG printout of element
		System.out.println(d.toString("\t"));

		return super.visitMethod(methodTree, trees);
	}

	@Override
	public Object visitVariable(VariableTree variableTree, Trees trees) {
		System.out.println("\n== This is a visited variable");

		DataInformation d = new DataInformation();
		TreePath path = getCurrentPath();
		Scope scope = trees.getScope(path);

		/*
		 * EXPERIMENT SCOPE
		 */

		System.out.println("=========BEGIN TEST ==========");
		path = getCurrentPath();

		System.out.println("Suspected Parent is called: " + getParentName());

		System.out.println("=========END TEST ==========");
		/*
		 * EXPERIMENT END
		 */

		// preset for Datatype
		d.setDatatype(variableTree.getKind().toString());
		ModifiersTree m = variableTree.getModifiers();

		// some debugging
		System.out.println(">>>DEBUG modifier : " + m.toString());
		System.out.println("public \t\t" + m.toString().contains("public"));
		System.out.println("private \t" + m.toString().contains("private"));
		System.out.println("protected \t" + m.toString().contains("protected"));

		// Local Variables = do always have an enclosing method scope and no
		// other arguments, furthermore their scope is limited to their function
		if ((scope.getEnclosingMethod() != null) && (m.toString().isEmpty())) {
			d.setDatatype("LOCAL " + d.getDatatype());
			d.setScope(getParentName());
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
			d.setDatatype("INSTANCE " + d.getDatatype() + " (Non-Static Field)");
		}
		// Class Variable (Static Field) = has no enclosing method only a class,
		// furthermore MUST HAVE static modfier
		else if (m.toString().contains("static")) {
			// && (scope.getEnclosingMethod() == null) {
			d.setDatatype("CLASS " + d.getDatatype() + " (Static Field)");
		}
		// Parameter = parent Method contains this items name as parameter
		if (getParentName() != null) { // TODO this needs to be checked ones
											// the arguments are having a parent
			
			// TODO why is the PARENT not null, but the ParentMethod is null even though the parent should be a method ... or is it the class ???
			// PARENT seems to be a class thats why ..
			System.out.println("IS PART OF METHOD? "+getParentMethod().getParameters().contains(
					variableTree.getName()));
			if (getParentMethod().getParameters().contains(
					variableTree.getName())) {
				d.setDatatype("PARAMETER");
			}

		}
		/*
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

	/*
	 * =====================================================================
	 * HELPER METHODS
	 * =====================================================================
	 */

	/**
	 * Simplified method to get the parent's readable name
	 * 
	 * @return name of method or class
	 * @author benste
	 */
	public String getParentName() {
		if (getParentMethod() != null) {
			return getParentMethod().getName().toString();
		} else if (getParentClass() != null) {
			return getParentClass().getSimpleName().toString();
		} else {
			return null;
		}
	}

	/**
	 * Helper method to safely get a MethodTree casted parent Element of the
	 * current one
	 * 
	 * @return null (if parent is not a Method) || MethodTree which is the
	 *         parent
	 * @author benste
	 */
	public MethodTree getParentMethod() {
		// System.out.println("EXPERIMENTAL getParentMETHOD");
		try {
			MethodTree test = (MethodTree) getCurrentPath().getParentPath()
					.getParentPath().getLeaf();
			return test;
		} catch (java.lang.ClassCastException e1) {
			/*
			 * System.out .println("Class Cast Exception (not MethodTree) " +
			 * e1);
			 */
		} catch (Exception e1) {
			/*
			 * System.out.println("Other issue when trying to cast to Method " +
			 * e1);
			 */
		}
		return null;
	}

	/**
	 * Helper method to safely get a ClassTree casted parent Element of the
	 * current one
	 * 
	 * @return null (if parent is not a Class) || ClassTree which is the parent
	 * @author benste
	 */
	public ClassTree getParentClass() {
		// System.out.println("EXPERIMENTAL getParentClass");
		try {
			ClassTree test = (ClassTree) getCurrentPath().getParentPath()
					.getParentPath().getLeaf();
			return test;
		} catch (java.lang.ClassCastException e1) {
			/*
			 * System.out .println("Class Cast Exception (not ClassTree)\n " +
			 * e1);
			 * System.out.println(getCurrentPath().getParentPath().getParentPath
			 * () .getLeaf().getClass());
			 */
		} catch (Exception e1) {
			/*
			 * System.out.println("Other issue when trying to cast to Method " +
			 * e1);
			 */
		}
		return null;
	}
}