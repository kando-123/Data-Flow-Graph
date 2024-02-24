/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package my.expression;

import java.util.*;

/**
 * 
 * @author Kay Jay O'Nail
 */
public class Expression
{
    private List<Term> terms;
    
    public Expression(String text) throws Exception
    {
        terms = new ArrayList<>();
        
        String[] pieces = text.split(" ");
        
        int counter = 1;
        for (var piece : pieces)
        {
            Term term = Term.makeTerm(piece);
            if (term != null)
            {
                terms.add(term);
            }
            else
            {
                throw new Exception("Expression.<init>");
            }
            
            if (counter == 0)
            {
                throw new Exception("Expression.<init>");
            }
            switch (term.getType())
            {
                case VARIABLE ->
                {
                    --counter;
                }
                case OPERATION ->
                {
                    OperationTerm operator = (OperationTerm) term;
                    if (operator.getOperation() != Operation.NEGATION)
                    {
                        ++counter;
                    }
                }
            }
        }
        if (counter > 0)
        {
            throw new Exception("Expression.<init>");
        }
    }
    
    public int termsCount()
    {
        return terms.size();
    }
    
    public Term getTerm(int index)
    {
        return index >= 0 && index < terms.size() ? terms.get(index) : null;
    }
    
    @Override
    public String toString()
    {
        StringBuilder result = new StringBuilder();
        if (terms.isEmpty())
        {
            result.append("<empty>");
        }
        else
        {
            for (var term : terms)
            {
                result.append(term.toString()).append(" ");
            }
        }
        return result.toString();
    }
}
