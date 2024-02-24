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
public class Transition extends Component
{
    private final Integer id;
    private List<Integer> precedingSteps = new ArrayList<>();
    private List<Integer> succeedingSteps = new ArrayList<>();
    private Expression condition;
    
    public Transition(Integer id, Expression expression)
    {
        super(ComponentType.TRANSITION);
        condition = expression;
        this.id = id;
    }

    public Transition(int source) {
        super(ComponentType.TRANSITION);
        id = source;
        precedingSteps = new ArrayList<>();
        succeedingSteps = new ArrayList<>();
    }

    public void addPrecedingStep(Integer step)
    {
        precedingSteps.add(step);
    }

    public void addSucceedingStep(Integer step)
    {
        succeedingSteps.add(step);
    }
    
    public Expression getCondition()
    {
        return condition;
    }
    
    public List<Integer> getPredecessors()
    {
        return Collections.unmodifiableList(precedingSteps);
    }
    
    public List<Integer> getSuccessors()
    {
        return Collections.unmodifiableList(succeedingSteps);
    }

    @Override
    public String toString() {
        return "Transition{" +
                "id=" + id +
                ", condition=" + condition +
                ", precedingSteps=" + precedingSteps +
                ", succeedingSteps=" + succeedingSteps +
                '}';
    }
}
