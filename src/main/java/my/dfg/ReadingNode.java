/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package my.dfg;

/**
 *
 * @author Kay Jay O'Nail
 */
public class ReadingNode extends Node
{
    private final String label;
    
    public ReadingNode(String label)
    {
        super(NodeType.READ);
        this.label = label;
    }
    
}
