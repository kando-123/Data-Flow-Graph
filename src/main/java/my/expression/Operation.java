/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package my.expression;

/**
 *
 * @author Kay Jay O'Nail
 */
public enum Operation
{
    NEGATION(1), // !, not, NOT
    DISJUNCTION(2), // |, ||, or, OR
    CONJUNCTION(2); // &, &&, and, AND
    
    private final int arguments;
    
    private Operation(int args)
    {
        arguments = args;
    }
    
    public int getArgumentsCount()
    {
        return arguments;
    }
}
