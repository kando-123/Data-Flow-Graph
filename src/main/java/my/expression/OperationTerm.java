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


    public static OperationTerm makeTerm(String text) throws Exception {
        OperationTerm term = null;

        switch (text) {
            case "!", "not", "NOT" -> {
                term = new OperationTerm(Operation.NEGATION);
            }
            case "|", "||", "or", "OR" -> {
                term = new OperationTerm(Operation.DISJUNCTION);
            }
            case "&", "&&", "and", "AND" -> {
                term = new OperationTerm(Operation.CONJUNCTION);
            }
            default -> {
                throw new Exception("Term.makeTerm");
            }
        }

        return term;
    }

    @Override
    public String toString()
    {
        return operation.toString();
    }
}
