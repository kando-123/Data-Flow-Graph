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
    private String label;
    
    protected VariableTerm(String label)
    {
        super(TermType.VARIABLE);
        this.label = label;
    }
    
    public String getLabel()
    {
        return label;
    }
    
    @Override
    public String toString()
    {
        return String.format("[%s]", label);
    }
}
