
package com.github.uscexp.splshell.parser;

import com.github.uscexp.grappa.extension.interpreter.type.Primitive;

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
        Primitive doubleNumber = getPrimitive(value);
        processStore.getTierStack().push(doubleNumber);
    }

}
