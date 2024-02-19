/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package my.dfg;

import java.util.*;

/**
 *
 * @author Kay Jay O'Nail
 */
public class Node
{
    private NodeType type;
    
    private List<Edge> inputEdges;
    private List<Edge> outputEdges;
    
    protected Node(NodeType type)
    {
        this.type = type;
        inputEdges = new ArrayList<>();
        outputEdges = new ArrayList<>();
    }
    
    public NodeType getType()
    {
        return type;
    }
    
    public void addInputEdge(Edge edge)
    {
        inputEdges.add(edge);
    }
    
    public void addOutputEdge(Edge edge)
    {
        outputEdges.add(edge);
    }
}
