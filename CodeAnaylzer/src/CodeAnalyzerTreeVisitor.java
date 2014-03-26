// original code from https://today.java.net/pub/a/today/2008/04/10/source-code-analysis-using-java-6-compiler-apis.html#accessing-the-abstract-syntax-tree-the-compiler-tree-api
import com.sun.source.tree.ClassTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.VariableTree;
import com.sun.source.util.TreePath;
import com.sun.source.util.TreePathScanner
import com.;

import com.sun.source.util.*;

import com.sun.source.util.Trees;

public class CodeAnalyzerTreeVisitor extends TreePathScanner<Object, Trees>  {
    @Override
    public Object visitClass(ClassTree classTree, Trees trees) {
        //---- some code ----
        return super.visitClass(classTree, trees);
    }
    @Override
    public Object visitMethod(MethodTree methodTree, Trees trees) {
        //---- some code ----
        return super.visitMethod(methodTree, trees);
    }
} 