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
public class Step
{
    private String name;

    private final List<Transition> precedingTransitions;
    private final List<Transition> succeedingTransitions;

    public Step(String name)
    {
        this.name = name;
        precedingTransitions = new ArrayList<>();
        succeedingTransitions = new ArrayList<>();
    }

    public void addPrecedingTransition(Transition transition)
    {
        precedingTransitions.add(transition);
    }

    public void addSucceedingTransition(Transition transition)
    {
        succeedingTransitions.add(transition);
    }

    public String getLabel()
    {
        return name;
    }

    public List<Transition> getPrecedingTransitions()
    {
        return Collections.unmodifiableList(precedingTransitions);
    }

    public List<Transition> getSucceedingTransitions()
    {
        return Collections.unmodifiableList(succeedingTransitions);
    }
}
