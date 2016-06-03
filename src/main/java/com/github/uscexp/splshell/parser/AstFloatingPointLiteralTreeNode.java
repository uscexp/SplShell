
package com.github.uscexp.splshell.parser;

/**
 * Command implementation for the <code>SplParser</code> rule: floatingPointLiteral.
 * 
 */
public class AstFloatingPointLiteralTreeNode<V >
    extends AstBaseCommandTreeNode<V>
{


    public AstFloatingPointLiteralTreeNode(String rule, String value) {
        super(rule, value);
    }

    @Override
    protected void interpretAfterChilds(Long id)
        throws Exception
    {
        super.interpretAfterChilds(id);
    }

    @Override
    protected void interpretBeforeChilds(Long id)
        throws Exception
    {
        super.interpretBeforeChilds(id);
        Double doubleNumber = new Double(value);
        processStore.getTierStack().push(doubleNumber);
    }

}
