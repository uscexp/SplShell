/*
 * Copyright (C) 2014 - 2026 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import com.github.uscexp.parboiled.extension.interpreter.type.Primitive;

/**
 * Command implementation for the <code>SplParser</code> rule: charLiteral.
 * 
 */
public class AstCharLiteralTreeNode<V >
    extends AstBaseCommandTreeNode<V>
{


    public AstCharLiteralTreeNode(String rule, String value) {
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
        Primitive character = Primitive.createValue(convertStringToChar(this.value));
        processStore.getTierStack().push(character);
    }

    private char convertStringToChar(String value) {
        if (value.charAt(1) == '\\' && value.length() > 2) {
            return handleEscapeSequence(value.trim().substring(1));
        } else {
            return value.trim().charAt(1);

        }
    }

    private char handleEscapeSequence(String value) {
        char result = 0;
        if (value.length() > 1) {
            char escapeChar = value.charAt(1);
            switch (escapeChar) {
                case 'n':
                    result = '\n';
                    break;
                case 't':
                    result = '\t';
                    break;
                case 'r':
                    result = '\r';
                    break;
                case 'b':
                    result = '\b';
                    break;
                case 'f':
                    result = '\f';
                    break;
                case '\'':
                    result = '\'';
                    break;
                case '"':
                    result = '"';
                    break;
                case '\\':
                    result = '\\';
                    break;
                default:
                    throw new IllegalArgumentException("Invalid escape sequence: \\" + escapeChar);
            }
        }
        return result;
    }
}
