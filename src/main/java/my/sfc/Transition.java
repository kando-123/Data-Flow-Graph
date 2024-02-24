/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package my.sfc;

import my.expression.Expression;
import java.util.*;

/**
 *
 * @author Kay Jay O'Nail
 */
public class Transition
{
    private List<Step> precedingSteps;
    private List<Step> succeedingSteps;
    private Expression condition;

    public Transition(Expression condition)
    {
        this.condition = condition;
        precedingSteps = new ArrayList<>();
        succeedingSteps = new ArrayList<>();
    }

    public void addPrecedingStep(Step step)
    {
        precedingSteps.add(step);
    }

    public void addSucceedingStep(Step step)
    {
        succeedingSteps.add(step);
    }

    public Expression getCondition()
    {
        return condition;
    }

    public List<Step> getPredecessors()
    {
        return Collections.unmodifiableList(precedingSteps);
    }

    public List<Step> getSuccessors()
    {
        return Collections.unmodifiableList(succeedingSteps);
    }
    
    public String toString()
    {
        return condition.toString();
    }
}
