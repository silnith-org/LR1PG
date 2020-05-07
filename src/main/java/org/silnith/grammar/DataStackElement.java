package org.silnith.grammar;

// TODO: parameterize this type
public class DataStackElement {
    
    private final Object abstractSyntaxTree;
    
    public DataStackElement(final Object abstractSyntaxTree) {
        super();
        this.abstractSyntaxTree = abstractSyntaxTree;
    }
    
    public Object getAbstractSyntaxTreeElement() {
        return abstractSyntaxTree;
    }
    
    @Override
    public String toString() {
        return "[" + abstractSyntaxTree + "]";
    }
    
}
