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

// This is an experimental code following the tutorial of
// https://today.java.net/pub/a/today/2008/04/10/source-code-analysis-using-java-6-compiler-apis.html#accessing-the-abstract-syntax-tree-the-compiler-tree-api

@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes("*")
public class CodeAnalyzerProcessor extends AbstractProcessor {
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
	 * Root Level get Symbols
	 * 
	 * @param elements
	 * @return
	 */
	private ArrayList<DataInformation> getSymbols(
			Set<? extends Element> elements) {
		ArrayList<DataInformation> result = new ArrayList<DataInformation>(0);

		for (Element e : elements) {
			System.out.println("Element is " + e.getSimpleName());
			// Add code here to analyze each root element
			System.out.println(e.getModifiers());
			System.out.println(e.getClass());
			System.out.println();
			if (e.getEnclosedElements() != null) {
				getSymbols(e.getEnclosedElements());
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
			List<? extends Element> elements) {
		ArrayList<DataInformation> result = new ArrayList<DataInformation>(0);
		if (elements.size() > 0) {
			for (Element e : elements) {
				System.out.println("Element is " + e.getSimpleName());
				// Add code here to analyze each root element
				System.out.println(e.getModifiers());
				System.out.println(e.getClass());
				System.out.println();
				if (e.getEnclosedElements() != null) {
					getSymbols(e.getEnclosedElements());
				}
			}
		}
		return result;
	}
}