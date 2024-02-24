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
    private String name;

    private final List<Integer> precedingTransitions = new ArrayList<>();
    private final List<Integer> succeedingTransitions = new ArrayList<>();
    
    /**
     * Constructor.
     * @param id
     * @param name - label of the step
     */
    public Step(int id, String name)
    {
        super(ComponentType.STEP);
        this.id = id;
        this.name = name;
    }

    public void addPrecedingTransition(Integer transition)
    {
        precedingTransitions.add(transition);
    }

    public void addSucceedingTransition(Integer transition) throws Exception
    {
        succeedingTransitions.add(transition);
    }
    
    public String getLabel()
    {
        return name;
    }
    
    public List<Integer> getPrecedingTransitions()
    {
        return Collections.unmodifiableList(precedingTransitions);
    }
    
    public List<Integer> getSucceedingTransitions()
    {
        return Collections.unmodifiableList(succeedingTransitions);
    }
    
    @Override
    public String toString() {
        return "Step{" +
                "id=" + id +
                ", label='" + name + '\'' +
                ", precedingTransitions=" + precedingTransitions +
                ", succeedingTransitions=" + succeedingTransitions +
                '}';
    }
}
