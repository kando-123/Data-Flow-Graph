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
//    public static void main(String[] args)
//    {
//        try
//        {
//            Expression expression = new Expression("");
//            System.out.println(expression.toString());
//        }
//        catch (Exception e)
//        {
//            System.err.println(e.getMessage());
//        }
//        
//    }
    
    private List<Term> terms;
    
    public Expression(String infixNotation) throws Exception
    {
        Converter converter = new Converter(infixNotation);
        converter.transform();
        
        String prefixNotation = converter.getPrefix();
        
        terms = new ArrayList<>();
        
        String[] pieces = prefixNotation.split(" ");
        
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
                throw new Exception("Expression.<init>: a term is null");
            }
            
            if (counter == 0)
            {
                throw new Exception("Expression.<init>: erroneous expression");
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
            throw new Exception("Expression.<init>: erroneous expression");
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
