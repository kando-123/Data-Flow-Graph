/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package my.sfc;

import my.expression.Expression;

import java.util.List;

/**
 *
 * @author Kay Jay O'Nail
 */
public class Transition extends Component
{
    private final Integer id;
    private List<Step> precedingSteps;
    private List<Step> succeedingSteps;
    private Expression condition;
    
    public Transition(Integer _id, Expression expression)
    {
        super(ComponentType.TRANSITION);
        condition = expression;
        id = _id;
    }

    public Transition(int source) {
        super(ComponentType.TRANSITION);
        id = source;
    }

    public void addPrecedingStep(Step step)
    {
        precedingSteps.add(step);
    }

    public void addSucceedingStep(Step step)
    {
        succeedingSteps.add(step);
    }

    @Override
    public String toString() {
        return "Transition{" +
                "id=" + id +
                ", condition=" + condition +
                '}';
    }
}
