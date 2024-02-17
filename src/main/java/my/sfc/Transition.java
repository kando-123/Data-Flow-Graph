/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package my.sfc;

import my.expression.Expression;

/**
 *
 * @author Kay Jay O'Nail
 */
public class Transition extends Component
{
    private Component precedingComponent;
    private Component succeedingComponent;
    private Expression condition;
    
    public Transition(Expression expression)
    {
        super(ComponentType.TRANSITION);
        condition = expression;
    }
    
    public void setPrecedingStep(Step step)
    {
        precedingComponent = step;
    }
    
    public void setPrecedingConvergence(Convergence convergence)
    {
        precedingComponent = convergence;
    }
    
    public void setSucceedingStep(Step step)
    {
        succeedingComponent = step;
    }
    
    public void setSucceedingDivergence(Divergence divergence)
    {
        succeedingComponent = divergence;
    }
}
