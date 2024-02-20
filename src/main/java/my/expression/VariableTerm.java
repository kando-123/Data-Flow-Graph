/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package my.expression;

/**
 *
 * @author Kay Jay O'Nail
 */
public class VariableTerm extends Term
{
    String label;
    
    protected VariableTerm(String label)
    {
        super(TermType.VARIABLE);
        this.label = label;
    }
    
    public String getLabel()
    {
        return label;
    }

    public static Term makeTerm(String text) throws Exception
    {
        Term term = null;

        //letter + [letter/digit/underscore]{0, 1 or many}
        if (text.matches("[a-zA-Z][a-zA-Z0-9_]*"))
        {
            term = new VariableTerm(text);
        }
        else
        {
            throw new Exception("Term.makeTerm");
        }

        return term;
    }

    @Override
    public String toString()
    {
        return label;
    }
}
