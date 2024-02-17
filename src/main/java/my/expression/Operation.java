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
    NEGATION(1), // !, not
    DISJUNCTION(2), // |, ||, or
    CONJUNCTION(2); // &, &&, and
    
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
