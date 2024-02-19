/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package my.expression;

/**
 *
 * @author Kay Jay O'Nail
 */
public class OperationTerm extends Term
{
    private Operation operation;
    
    protected OperationTerm(Operation operation)
    {
        super(TermType.OPERATION);
        this.operation = operation;
    }
    
    public Operation getOperation()
    {
        return operation;
    }
}
