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
    private List<Component> components;
    
    /*
     * Wszystkie kroki i inne komponenty są w components.
     * Do initialSteps trzeba tylko 'dorzucić' te kroki (referencje do już
     * istniejących), które mają być początkowo aktywne.
     */
    private List<Step> initialSteps;
    
    
    /* Tworząc kroki proponuję wywoływać konstruktor w następujący sposób:
       ... = new Step("step" + Integer.toString(steps.size()))
       w ten sposób będą ponumerowane: "step0", "step1", "step2", ...
     */
    
    public Step getStartStep() throws Exception
    {
        if (!initialSteps.isEmpty())
        {
            return initialSteps.get(0); 
        }
        else
        {
            throw new Exception("SFC.getFirstStep");
        }
    }
}
