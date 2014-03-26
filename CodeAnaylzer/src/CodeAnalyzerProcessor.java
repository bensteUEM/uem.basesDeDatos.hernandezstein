import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.SourceVersion;

// based on https://today.java.net/pub/a/today/2008/04/10/source-code-analysis-using-java-6-compiler-apis.html#accessing-the-abstract-syntax-tree-the-compiler-tree-api
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes("*")
public class CodeAnalyzerProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations,
            RoundEnvironment roundEnvironment) {
        for (Element e : roundEnvironment.getRootElements()) {
                System.out.println("Element is "+ e.getSimpleName());
                // Add code here to analyze each root element
        }
        return true;
    }
}