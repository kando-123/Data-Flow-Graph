/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package my.dfg;

/**
 *
 * @author Kay Jay O'Nail
 */
public class Edge
{
    private final EdgeAttribute attribute;
    private Node tail;
    private Node head;
    
    public Edge(EdgeAttribute attribute)
    {
        this.attribute = attribute;
    }
    
    public void setTail(Node node)
    {
        tail = node;
        tail.addOutputEdge(this);
    }
    
    public void setHead(Node node)
    {
        head = node;
        head.addInputEdge(this);
    }
    
    public EdgeAttribute getAttribute()
    {
        return attribute;
    }
}
