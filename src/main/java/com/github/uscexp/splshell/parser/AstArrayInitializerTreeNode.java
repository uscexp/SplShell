/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import java.util.ArrayList;

import com.github.uscexp.grappa.extension.interpreter.type.Primitive;


/**
 * Command implementation for the <code>SplParser</code> rule: arrayInitializer.
 * 
 */
public class AstArrayInitializerTreeNode<V >
    extends AstBaseCommandTreeNode<V>
{


    public AstArrayInitializerTreeNode(String rule, String value) {
		super(rule, value);
    }

    @Override
    protected void interpretAfterChilds(Long id)
        throws Exception
    {
		super.interpretAfterChilds(id);
        int n = 0;
        ArrayList<Object> list = null;
        
        if( n > 0)
        {
          for( int i = 0; i < n; ++i)
          {
            if( processStore.getTierStack().peek() instanceof ArrayList)
            {
              list = new ArrayList<>();
              list.add( processStore.getTierStack().pop());
            }
            else if( list instanceof ArrayList)
            {
              list.add( new Primitive( processStore.getTierStack().pop()));
            }
            else
            {
              list = new ArrayList<>();
              list.add( new Primitive( processStore.getTierStack().pop()));
            }
          }
        }
        
        processStore.getTierStack().push( list);
    }

}
