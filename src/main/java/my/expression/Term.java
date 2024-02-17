/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package my.expression;

/**
 *
 * @author Kay Jay O'Nail
 */
public abstract class Term
{
    private TermType type;
    
    protected Term(TermType type)
    {
        this.type = type;
    }
    
    private boolean isVariableLabel(String text)
    {
        /* Temporary! */
        return true;
    }
    
    /**
     * Produces a new instance of Term, depending on the input text.
     * 
     * @param text
     * @return
     * @throws Exception 
     */
    public Term makeTerm(String text) throws Exception
    {
        Term term = null;
        
        if (isVariableLabel(text))
        {
            term = new VariableTerm(text);
        }
        else
        {
            switch (text)
            {
                case "!", "not", "NOT" ->
                {
                    term = new OperationTerm(Operation.NEGATION);
                }
                case "|", "||", "or", "OR" ->
                {
                    term = new OperationTerm(Operation.DISJUNCTION);
                }
                case "&", "&&", "and", "AND" ->
                {
                    term = new OperationTerm(Operation.CONJUNCTION);
                }
                default ->
                {
                    throw new Exception("Term.makeTerm");
                }
            }
        }
        
        return term;
    }
}
