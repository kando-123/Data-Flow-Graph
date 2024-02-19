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
public class SFC
{
    private List<Step> steps;
    private List<Step> initialSteps;
    
    private List<Transition> transitions;
    
    public SFC()
    {
        steps = new ArrayList<>();
        initialSteps = new ArrayList<>();
        transitions = new ArrayList<>();
    }
    
    public void readFromXML(String fileName) throws Exception
    {
        throw new Exception("Not yet implemented, dude!");
    }
    
    public List<Step> getSteps()
    {
        return Collections.unmodifiableList(steps);
    }
}
