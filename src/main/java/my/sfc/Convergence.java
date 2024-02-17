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
public class Convergence extends Component
{
    private List<Step> preceedingSteps;
    private Transition succeedingTransition;
    
    public Convergence()
    {
        super(ComponentType.CONVERGENCE);
        preceedingSteps = new ArrayList<>();
    }
    
    public void addPrecedingStep(Step step)
    {
        preceedingSteps.add(step);
    }
    
    public void setSucceedingTransition(Transition transition)
    {
        succeedingTransition = transition;
        transition.setPrecedingConvergence(this);
    }
}
