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
    
    protected String getLabel()
    {
        return label;
    }
}
