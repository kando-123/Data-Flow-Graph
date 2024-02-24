/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package my.expression;

import java.util.regex.Pattern;

/**
 *
 * @author Kay Jay O'Nail
 */
abstract public class Term
{
    private TermType type;
    
    protected Term(TermType type)
    {
        this.type = type;
    }
    
    public TermType getType()
    {
        return type;
    }
    
    private static boolean isVariable(String text)
    {
        return Pattern.matches("[a-zA-Z][a-zA-Z0-9_]*", text);
    }
    
    public static Term makeTerm(String text)
    {
        Term term;
        switch (text)
        {
            case "not", "NOT" ->
                term = new OperationTerm(Operation.NEGATION);
            case "or", "OR" ->
                term = new OperationTerm(Operation.DISJUNCTION);
            case "and", "AND" ->
                term = new OperationTerm(Operation.CONJUNCTION);
            default ->
                term = isVariable(text) ? new VariableTerm(text) : null;
        }
        return term;
    }
}
