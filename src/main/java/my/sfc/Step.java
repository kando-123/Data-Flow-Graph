/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package my.sfc;

import java.util.*;

/**
 *
 * @author Kay Jay O'Nail
 */
public class Step extends Component
{
    private Integer id;
    private String label;

    private final List<Transition> precedingTransitions;
    private final List<Transition> succeedingTransitions;
    
    /**
     * Constructor.
     * @param label - label of the step
     */
    public Step(int _id, String label)
    {
        super(ComponentType.STEP);
        precedingTransitions = new ArrayList<>();
        succeedingTransitions = new ArrayList<>();
        id = _id;
        this.label = label;
    }

    public void addPrecedingTransition(Transition transition)
    {
        precedingTransitions.add(transition);
    }

    public void addSucceedingTransition(Transition transition) throws Exception
    {
        succeedingTransitions.add(transition);
    }
    
    public String getLabel()
    {
        return label;
    }

    @Override
    public String toString() {
        return "Step{" +
                "id=" + id +
                ", label='" + label + '\'' +
                '}';
    }
}
