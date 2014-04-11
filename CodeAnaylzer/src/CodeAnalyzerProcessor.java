//this project now requires JRE 8 !
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.SourceVersion;
import com.sun.source.util.TreePath;

import com.sun.source.util.Trees;

// based on https://today.java.net/pub/a/today/2008/04/10/source-code-analysis-using-java-6-compiler-apis.html#accessing-the-abstract-syntax-tree-the-compiler-tree-api
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("*")
public class CodeAnalyzerProcessor extends AbstractProcessor {
	private Trees trees;
	CodeAnalyzerTreeVisitor visitor = new CodeAnalyzerTreeVisitor();

	@Override
	public boolean process(Set<? extends TypeElement> annotations,
			RoundEnvironment roundEnvironment) {
		for (Element e : roundEnvironment.getRootElements()) {
			System.out.println("Root element is " + e.getSimpleName());
			// part 2

			TreePath tp = trees.getPath(e);
			// invoke the scanner
			visitor.scan(tp, trees);
		}
		return true;
	}

	public void init(ProcessingEnvironment pe) {
		super.init(pe);
		trees = Trees.instance(pe);
	}

}
