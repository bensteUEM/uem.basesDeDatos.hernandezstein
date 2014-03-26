import javax.lang.model.element.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.swing.tree.TreePath;

import com.sun.source.tree.ClassTree;
import com.sun.source.tree.Trees;

// This is an experimental code following the tutorial of
// https://today.java.net/pub/a/today/2008/04/10/source-code-analysis-using-java-6-compiler-apis.html#accessing-the-abstract-syntax-tree-the-compiler-tree-api

@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes("*")
public class CodeAnalyzerProcessorBENSTE extends AbstractProcessor {
	@Override
	public boolean process(Set<? extends TypeElement> annotations,
			RoundEnvironment roundEnvironment) {
		getSymbols(roundEnvironment.getRootElements());
		/*
		 * for (Element e : roundEnvironment.getRootElements()) {
		 * System.out.println("Element is " + e.getSimpleName()); // Add code
		 * here to analyze each root element
		 * System.out.println(e.getModifiers());
		 * System.out.println(e.getClass());
		 * System.out.println(e.getEnclosedElements());
		 * 
		 * }
		 */
		return true;
	}

	/**
	 * Root Level get Symbols = different datatype header but function is the
	 * same as the other get Symbols
	 * 
	 * @param elements
	 * @return
	 */
	private ArrayList<DataInformation> getSymbols(
			Set<? extends Element> elements) {
		Integer level = 0;
		String scope = "*";
		String parentName = "File";
		ArrayList<DataInformation> result = new ArrayList<DataInformation>(0);

		String tabs = "";
		for (int i = level; i > 0; i--) {
			tabs += "\t";
		}

		if (elements.size() > 0) {
			for (Element e : elements) {
				DataInformation d = new DataInformation();
				d.setName(e.getSimpleName().toString());
				d.setDatatype(e.getKind().toString());
				// Scope
				Set<Modifier> mods = e.getModifiers();
				if (mods.contains(Modifier.PRIVATE)) {
					scope = (".");
				}
				d.setScope(scope);
				// (mods.contains(Modifier.PUBLIC) ||
				// mods.contains(Modifier.PROTECTED))

				// others
				d.setParameters(e.getModifiers().toString());

				// Printout Debug information
				System.out.println(d.toString(""));
				System.out.println(tabs + "it encloses: " + e.getEnclosedElements());
				result.add(d);
				if (e.getEnclosedElements() != null) {
					getSymbols(e.getEnclosedElements(), level + 1, scope);
				}
			}
		}
		return result;
	}

	/**
	 * Level 1+ for all optional elements
	 * 
	 * @param elements
	 * @return
	 */
	private ArrayList<DataInformation> getSymbols(
			List<? extends Element> elements, Integer level, String scope) {
		ArrayList<DataInformation> result = new ArrayList<DataInformation>(0);
		if (elements.size() > 0) {
			String tabs = "";
			for (int i = level; i > 0; i--) {
				tabs += "\t";
			}

			for (Element e : elements) {
				result.add(createInformation(e, scope, tabs));

				if (e.getEnclosedElements() != null) {
					getSymbols(e.getEnclosedElements(), level + 1, scope);
				}
			}
		}
		return result;
	}

	@Override
	public Object visitClass(ClassTree classTree, Trees trees) {
	         //Storing the details of the visiting class into a model
	         DataInformation clazzInfo = new DataInformation();

	        // Get the current path of the node     
	        TreePath path = getCurrentPath();

	        //Get the type element corresponding to the class
	        TypeElement e = (TypeElement) trees.getElement(path);

	        //Set qualified class name into model
	        clazzInfo.setName(e.getQualifiedName().toString());

	        //Set extending class info
	        clazzInfo.setNameOfSuperClass(e.getSuperclass().toString());

	        //Set implementing interface details
	        for (TypeMirror mirror : e.getInterfaces()) {
	                clazzInfo.addNameOfInterface(mirror.toString());
	        }
	        return super.visitClass(classTree, trees);
	  }
	
	public DataInformation createInformation(Element e, String scope,
			String tabs) {
		DataInformation d = new DataInformation();
		d.setName(e.getSimpleName().toString());
		d.setDatatype(e.getKind().toString());
		// Scope
		Set<Modifier> mods = e.getModifiers();
		if (mods.contains(Modifier.PRIVATE)) {
			scope = ("." + e.getEnclosingElement().getSimpleName());
		}
		d.setScope(scope);
		// (mods.contains(Modifier.PUBLIC) ||
		// mods.contains(Modifier.PROTECTED))

		// others
		d.setParameters(e.getModifiers().toString());

		// Printout Debug information
		System.out.println(d.toString(tabs));
		System.out.println("it encloses: " + e.getEnclosedElements());
		return d;
	}
}