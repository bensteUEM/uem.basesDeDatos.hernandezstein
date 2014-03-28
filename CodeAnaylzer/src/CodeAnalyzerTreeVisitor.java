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
		d.setName(classTree.getSimpleName().toString());
		d.setModifiers(classTree.getModifiers().toString());
		d.setReturnType("n/a");
		d.setParameters("n/a");
		
		/*
		 * SCOPE ...
		 */
		d.setScope(generateTextScope(m, d));

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
		 * SCOPE ...
		 */
		d.setScope(generateTextScope(m, d));

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
		ModifiersTree m = variableTree.getModifiers();

		/*
		 * DATA Types
		 */

		// preset for Datatype
		d.setDatatype(variableTree.getKind().toString());

		// Local Variables = do always have an enclosing method scope and no
		// other arguments, furthermore their scope is limited to their function
		if ((scope.getEnclosingMethod() != null) && (m.toString().isEmpty())) {
			d.setDatatype("LOCAL " + d.getDatatype());
			d.setScope(getParentName());
		} // end local var

		// Constants always have the "final" keyword and no enclosing method
		else if (m.toString().contains("final")) {
			// && (scope.getEnclosingMethod() == null) {
			d.setDatatype("CONSTANT " + d.getDatatype());
		}// / end Constant

		// Instance Variable (Non-Static Field) = has no enclosing method only a
		// class, furthermore no static modfier
		else if (!(m.toString().contains("static"))) {
			// && (scope.getEnclosingMethod() == null) {
			d.setDatatype("INSTANCE " + d.getDatatype() + " (Non-Static Field)");
		} // end Var non Static

		// Class Variable (Static Field) = has no enclosing method only a class,
		// furthermore MUST HAVE static modfier
		else if (m.toString().contains("static")) {
			// && (scope.getEnclosingMethod() == null) {
			d.setDatatype("CLASS " + d.getDatatype() + " (Static Field)");
		} // end class static

		// Parameter = parent Method contains this items name as parameter
		if (getParentClass() != null) { // check safe to have a parent class
										// available
			for (Tree leaf : getParentClass().getMembers()) { // get all members
																// of the
																// enclosing
																// class
				if (getMethod(leaf) != null) { // only for leafs which are
												// Methods
					MethodTree parent = getMethod(leaf);
					if (parent.getParameters().toString()
							.contains(variableTree.getName().toString())) {
						d.setDatatype("PARAMETER of Method "
								+ parent.getName().toString());
						d.setScope(parent.getName().toString());
						// TODO this will only set the last method, if multiple
						// functions use the var there needs to be a var which
						// counts the objects
						// TODO get full name of function incl. path
						System.out.println("VAR IS PARAMETER of method "
								+ parent.getName().toString());
					}
				}
			}
		} // end Parameter check part

		/*
		 * SCOPE ...
		 */
		
		d.setScope(generateTextScope(m, d));
		
		/*
		 * Other Fields
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
		// TODO this is not correct because method parameters seem to have
		// their class as parent
		// System.out.println("WARNING - TODO - if Parameter parent might be wrong => scope too wide too");
		
		if (getParentMethod() != null) {
			return getParentMethod().getName().toString();
		} else if (getParentClass() != null) {
			return getParentClass().getSimpleName().toString();
		} else {
			return "";
		}
	}

	/**
	 * Helper method to safely get a MethodTree casted from the passed object
	 * 
	 * @return null (if parent is not a Method) || MethodTree object
	 * @author benste
	 */
	public MethodTree getMethod(Tree anyTree) {
		// System.out.println("EXPERIMENTAL getParentMETHOD");
		try {
			MethodTree test = (MethodTree) anyTree;
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
	 * Helper method to safely get a MethodTree casted parent Element of the
	 * current one
	 * 
	 * @return null (if parent is not a Method) || MethodTree which is the
	 *         parent
	 * @author benste
	 */
	public MethodTree getParentMethod() {
		return getMethod(getCurrentPath().getParentPath().getParentPath()
				.getLeaf());
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

	/**
	 * Custom function to determine the scope modification caused by modifiers
	 * 
	 * @param m
	 *            all Modifiers which exist at this stage
	 * @param d
	 *            all the information collected about the Object up to now
	 * @return Scope Description
	 * @author benste
	 */
	public String generateTextScope(ModifiersTree m, DataInformation d) {
		/*
		 * SCOPE ...
		 */
		String parentScope = "?PARENT?"; // TODO
		String localScope = "?LOCAL?"; // TODO
		String result = "UNKNOWN SCOPE CASE";

		// PUBLIC => use parent scope
		if (m.toString().contains("public")) {
			result = parentScope;
		}
		// PRIVATE => use local scope (method or class)
		else if (m.toString().contains("private")) {
			result = localScope;
		}
		// PROTECTED => use local scope + package
		else if (m.toString().contains("protected")) {
			result = localScope + " + Package";
		}
		// if not any of those three modifier and neither a Parameter or Local
		// Variable
		// => use local scope + package + subclass
		else if (!(d.getDatatype().startsWith("PARAMETER"))
				&& !(d.getDatatype().startsWith("LOCAL"))) {
			result = localScope + " + Package + SubClass";
		}
		// Variables which have no modifiers - Local = have their parent method
		// / class as scope
		else if (d.getDatatype().startsWith("LOCAL")) {
			if (!(getParentName().equals(""))) {
				result = getParentName();
			} else {
				System.out.println("LOCAL var without ParentName");
			}
		}
		// Variables which have no modifiers can be Local or Parameter. => both
		// have their parent method / class as scope
		else if (d.getDatatype().startsWith("PARAMETER")) {
			if (!(getParentName().equals(""))) {
				result = getParentName();
				System.out.println("PARAMETER + Parent in Scope helper method is: <"
						+ getParentName() + ">");
			} else {
				System.out.println("PARAMETER var without ParentName");
			}
		}

		return result;
	}
}