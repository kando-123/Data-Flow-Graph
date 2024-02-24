/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package my.dfg;

/**
 *
 * @author Kay Jay O'Nail
 */
public class WritingNode extends Node
{
    String label;
    
    public WritingNode(String label)
    {
        super(NodeType.WRITE);
        this.label = label;
    }

    @Override
    public String getDescription()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("[label=\"")
                .append(label)
                .append("\" shape=\"house\"]");
        return builder.toString();
    }
}
