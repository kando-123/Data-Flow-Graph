/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package my.dfg;

/**
 *
 * @author Kay Jay O'Nail
 */
public class OperationNode extends Node
{
    NodeOperation operation;

    public OperationNode(NodeOperation operation)
    {
        super(NodeType.OPERATION);
    }

    @Override
    public String getDescription()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("[label=\"")
                .append(operation == NodeOperation.DISJUNCTION ? "OR" : "AND")
                .append("\" shape=\"oval\"];");
        return builder.toString();
    }
}
