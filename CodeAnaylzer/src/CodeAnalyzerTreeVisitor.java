// inspiration of code from https://today.java.net/pub/a/today/2008/04/10/source-code-analysis-using-java-6-compiler-apis.html#accessing-the-abstract-syntax-tree-the-compiler-tree-api
// requires JRE JAR file on compile
// javac -cp /usr/lib/jvm/NAME OF YOUR JRE/lib/tools.jar Test.java

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.lang.model.element.Element;
import com.sun.source.tree.BlockTree;
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
import com.sun.tools.javac.tree.JCTree.JCClassDecl;
import com.sun.tools.javac.tree.JCTree.JCCompilationUnit;

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

	public CodeAnalyzerTreeVisitor() {
		super();
		LOG.setLevel(Level.ALL);
		try {
			FileHandler fh = new FileHandler("Logs" + File.separator
					+ "CodeAnalyzerTreeVisitor.log");
			LOG.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
		LOG.fine("Text Splitter LOG initialized created");
	}

	TreePath path;
	private final static Logger LOG = Logger
			.getLogger(CodeAnalyzerTreeVisitor.class.getName());

	@Override
	public Object visitClass(ClassTree classTree, Trees trees) {
		LOG.entering("CodeAnalyzerTreeVisitor", "visitClass");
		System.out.println("\n== This is a visited class");

		DataInformation d = new DataInformation();

		// preset for Datatype
		d.setDatatype(classTree.getKind().toString());
		ModifiersTree m = classTree.getModifiers();

		// Specials
		/*
		 * if (classTree.toString().contains("final")) { }
		 */
		d.setName(classTree.getSimpleName().toString());
		d.setModifiers(classTree.getModifiers().toString());
		d.setReturnType("n/a");
		d.setParameters("n/a");

		/*
		 * SCOPE ...
		 */
		d.setScope(generateTextScope(m, d));

		// DEBUG printout of element
		DataInformationFile.saveToStorage(d);
		System.out.println(d.toString(""));

		return super.visitClass(classTree, trees);
	}

	@Override
	public Object visitMethod(MethodTree methodTree, Trees trees) {
		LOG.entering("CodeAnalyzerTreeVisitor", "visitMethod");
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
		// Specials
		/*
		 * else if (methodTree.toString().contains("final")) { }
		 */

		/*
		 * SCOPE ...
		 */
		d.setScope(generateTextScope(m, d));

		// Check if its a Constructor
		if (isConstructor(methodTree)) {
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

		// Saving the text for all valid symbols in curent scope to allow
		// finding correct parent from storage
		// TOFO eorking here ...

		// DEBUG printout of element
		DataInformationFile.saveToStorage(d);
		System.out.println(d.toString("\t"));

		return super.visitMethod(methodTree, trees);
	}

	private boolean isConstructor(MethodTree methodTree) {
		return methodTree.getName().toString().equals("<init>");
		// TODO #26 in Github
	}

	@Override
	public Object visitVariable(VariableTree variableTree, Trees trees) {
		LOG.entering("CodeAnalyzerTreeVisitor", "visitVariable");
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
				if (leaf.getKind().toString().equals("METHOD")) {
					MethodTree element = getMethod(leaf);
					if (element.getParameters().toString()
							.contains(variableTree.getName().toString())) {
						d.setDatatype("PARAMETER of Method "
								+ element.getName().toString());
						d.setScope(element.getName().toString());
						System.out.println("VAR IS PARAMETER of method "
								+ element.getName().toString());
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
		DataInformationFile.saveToStorage(d);

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
		LOG.entering("CodeAnalyzerTreeVisitor", "getParentName");
		Tree parent = getParentTree(getCurrentPath());
		LOG.finest("Parent Type is: " + parent.getKind().toString());
		return getTreeItemName(parent);
	}

	public String getTreeItemName(Tree element) {
		String elementKind = element.getKind().toString();
		LOG.finest("Type is: " + elementKind);
		if (elementKind.equals("METHOD")) {
			return ((MethodTree) element).getName().toString();
		} else if (elementKind.equals("CLASS")) {
			return ((ClassTree) element).getSimpleName().toString();
		} else if (elementKind.equals("BLOCK")) {
			// Get the parent of the block assuming the block is the
			// CurrentPath() parent
			TreePath pathtoWrapper = getCurrentPath().getParentPath();
			LOG.finer("Found a Block, trying to use parent of this Block");
			return getTreeItemName(getParentTree(pathtoWrapper));
		} else if (elementKind.equals("COMPILATION_UNIT")) {
			return "";
		} else {
			LOG.warning("Element Type is not Implemented: "
					+ elementKind.toString());
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
		if (anyTree.getKind().toString().equals("METHOD")) {
			LOG.finest("getMethod - Success");
			return (MethodTree) anyTree;
		} else {
			LOG.info("Parent is not a Method but: " + anyTree.getKind());
			return null;
		}
	}

	/**
	 * This method is a quick access to the current items parent
	 * 
	 * @return parent as Tree
	 * 
	 *         Elements can be JCCompilationUnit, JCClassDecl , ClassTree,
	 *         MethodTree,
	 */
	public Tree getParentTree(TreePath currentPath) {
		//TODO #17 @Github (special case Parent of a Parameter of a Method)

		LOG.entering("CodeAnalyzerTreeVisitor", "getParentTree");
		Iterator<Tree> it = currentPath.iterator();
		it.next(); // current element in tree
		Tree t = it.next(); // parent element in tree
		LOG.fine("ParentTree is type:<" + t.getKind() + ">");
		return t;
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
		LOG.entering("CodeAnalyzerTreeVisitor", "getParentMethod");
		try {
			MethodTree m = getMethod(getParentTree(getCurrentPath()));
			LOG.finest("Current Element has following parent M.: "
					+ m.getName());
			return m;
		} catch (Exception e) {
			LOG.finest("Current Element has no Parent Method");
			return null;
		}
	}

	/**
	 * Helper method to safely get a ClassTree casted parent Element of the
	 * current one
	 * 
	 * @return null (if parent is not a Class) || ClassTree which is the parent
	 * @author benste
	 */
	public ClassTree getParentClass() {
		LOG.entering("CodeAnalyzerTreeVisitor", "getParentClass");

		Tree tree = getParentTree(getCurrentPath());

		if (tree.getKind().toString().equals("CLASS")) {
			ClassTree c = (ClassTree) tree;
			LOG.finest("Current Element has following parent C.: "
					+ c.getSimpleName());
			return c;
		}
		LOG.warning("Current Elements Parent is not a CLASS");
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
		LOG.entering("CodeAnalyzerTreeVisitor", "generateTextScope");
		String parentScope = "?";
		String localScope = "?";
		String parentName = getParentName();
		if (parentName.equals("")) {
			LOG.info("No Parent of - " + d.getName() + " - assuming TopLevel");
			parentScope = "FileSystem";
			localScope = "";
		} else {
			// Check the Constructor Case
			Tree parent = getParentTree(getCurrentPath());
			boolean inConstructor = false;
			if (parent.getKind().toString().equals("METHOD")) {
				inConstructor = isConstructor((MethodTree) parent);
				LOG.finer("current variable is part of a constructor? : "
						+ inConstructor);
			}
			DataInformation parentobj = DataInformationFile.getParentElement(parentName,
					inConstructor);

			if (parentobj != null) {
				LOG.fine("Parent object Found and relative scope text defined");
				parentScope = parentobj.getScope() + "." + parentobj.getName();
				localScope = parentobj.getName();
			} else {
				LOG.warning("No parent object found in the DataInformationStorage ");
			}
		}
		LOG.fine("Relative Scopes defined");

		// init value of result
		String result = "UNKNOWN SCOPE CASE";

		// PUBLIC => use parent scope
		if (m.toString().contains("public")) {
			result = parentScope;
			LOG.finest("PUBLIC - Parent Scope applied");
		}
		// PRIVATE => use local scope (method or class)
		else if (m.toString().contains("private")) {
			result = localScope;
			LOG.finest("PRIVATE - Local Scope applied");
		}
		// PROTECTED => use local scope + package
		else if (m.toString().contains("protected")) {
			result = localScope + " + Package";
			LOG.finest("PROTECTED - Mixed Scope applied");
		}
		// if not any of those three modifier and neither a Parameter or Local
		// Variable
		// => use local scope + package + subclass
		else if (!(d.getDatatype().startsWith("PARAMETER"))
				&& !(d.getDatatype().startsWith("LOCAL"))) {
			result = localScope + " + Package + SubClass";
			LOG.finest("NONE - Mixed Scope applied");
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
				System.out
						.println("PARAMETER + Parent in Scope helper method is: <"
								+ getParentName() + ">");
			} else {
				System.out.println("PARAMETER var without ParentName");
			}
		}
		LOG.finest("Following Scope has been determined: " + result);
		return result;
	}
}