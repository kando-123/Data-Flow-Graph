/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package my.dfg;

import java.util.*;
import my.expression.*;
import my.sfc.*;

/**
 *
 * @author Kay Jay O'Nail
 */
public class Graph
{
    private final List<Node> nodes;
    private final List<Edge> edges;

    public Graph()
    {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
    }

    private void joinNodes(Node tail, Node head, EdgeAttribute attribute)
    {
        Edge edge = new Edge(attribute);
        edge.setTail(tail);
        edge.setHead(head);
        edges.add(edge);
    }

    private void joinNodes(Node tail, Node head)
    {
        joinNodes(tail, head, EdgeAttribute.SIMPLE);
    }

    private class StepStructure
    {
        private final Node settingNode;
        private final Node clearingNode;
        private final Node outputNode;

        private final String description;
        private final String stepLabel;

        public StepStructure(Step step)
        {
            stepLabel = step.getLabel();
            settingNode = new OperationNode(NodeOperation.DISJUNCTION);
            clearingNode = new OperationNode(NodeOperation.CONJUNCTION);
            outputNode = new OperationNode(NodeOperation.DISJUNCTION);
            Node internal = new OperationNode(NodeOperation.CONJUNCTION);
            Node getter = new ReadingNode(stepLabel);
            Node setter = new WritingNode(stepLabel);
            nodes.add(settingNode);
            nodes.add(internal);
            nodes.add(clearingNode);
            nodes.add(outputNode);
            nodes.add(getter);
            nodes.add(setter);
            joinNodes(settingNode, internal, EdgeAttribute.SIMPLE);
            joinNodes(internal, outputNode, EdgeAttribute.SIMPLE);
            joinNodes(clearingNode, outputNode, EdgeAttribute.SIMPLE);
            joinNodes(getter, internal, EdgeAttribute.NEGATION);
            joinNodes(getter, clearingNode, EdgeAttribute.SIMPLE);
            joinNodes(outputNode, setter, EdgeAttribute.SIMPLE);
            StringBuilder builder = new StringBuilder();
            builder.append("subgraph ")
                    .append(stepLabel)
                    .append("\n");
            builder.append("{\n");
            builder.append("node [style=\"filled\", color=\"")
                    .append(PastelColor.getNextColor().toString())
                    .append("\"];\n");
            builder.append(stepLabel)
                    .append("_Set ")
                    .append(settingNode.getDescription())
                    .append(";\n");
            builder.append(stepLabel)
                    .append("_Clear ")
                    .append(clearingNode.getDescription())
                    .append(";\n");
            builder.append(stepLabel)
                    .append("_Output ")
                    .append(outputNode.getDescription())
                    .append(";\n");
            builder.append(stepLabel)
                    .append("_Internal ")
                    .append(internal.getDescription())
                    .append(";\n");
            builder.append(stepLabel)
                    .append("_Read ")
                    .append(getter.getDescription())
                    .append(";\n");
            builder.append(stepLabel)
                    .append("_Write ")
                    .append(setter.getDescription())
                    .append(";\n");
            builder.append(stepLabel)
                    .append("_Set -> ")
                    .append(stepLabel)
                    .append("_Internal -> ")
                    .append(stepLabel)
                    .append("_Output;\n");
            builder.append(stepLabel)
                    .append("_Clear -> ")
                    .append(stepLabel)
                    .append("_Output;\n");
            builder.append(stepLabel)
                    .append("_Read -> ")
                    .append(stepLabel)
                    .append("_Clear;\n");
            builder.append(stepLabel)
                    .append("_Read -> ")
                    .append(stepLabel)
                    .append("_Internal [arrowhead=\"odot\";\n");
            builder.append(stepLabel)
                    .append("_Output -> ")
                    .append(stepLabel)
                    .append("_Write;\n");
            builder.append("}\n");
            description = builder.toString();
        }

        public Node getSettingNode()
        {
            return settingNode;
        }

        public Node getClearingNode()
        {
            return clearingNode;
        }

        public Node getOutputNode()
        {
            return outputNode;
        }

        public String getDescription()
        {
            return description;
        }
        
        public String getLabel()
        {
            return stepLabel;
        }
    }
    
    private class ExpressionStructure
    {
        private Node outputNode;
        
        private String description;

        private class Element
        {
            private final Node node;
            private int counter;

            public Element(Node node, int counter)
            {
                this.node = node;
                this.counter = counter;
            }

            public Node useNode()
            {
                --counter;
                return node;
            }

            public boolean isDone()
            {
                return counter == 0;
            }
        }

        public ExpressionStructure(Expression expression, int index)
        {
            outputNode = null;
            Term term;
            EdgeAttribute attribute = EdgeAttribute.SIMPLE;
            Stack<Element> stack = new Stack<>();
            StringBuilder builder = new StringBuilder();
            builder.append("subgraph C")
                    .append(index)
                    .append("\n");
            builder.append("{\n");
            for (int i = 0; i < expression.size(); ++i)
            {
                term = expression.get(i);
                Node node;
                if (term.getType() == TermType.OPERATION)
                {
                    Operation operation = ((OperationTerm) term).getOperation();
                    if (operation == Operation.NEGATION)
                    {
                        attribute = EdgeAttribute.NEGATION;
                        continue;
                    }
                    else
                    {
                        node = new OperationNode(operation == Operation.DISJUNCTION
                                ? NodeOperation.DISJUNCTION : NodeOperation.CONJUNCTION);
                        
                        if (!stack.isEmpty())
                        {
                            joinNodes(node, stack.peek().useNode(), attribute);
                            if (stack.peek().isDone())
                            {
                                stack.pop();
                            }
                            attribute = EdgeAttribute.SIMPLE;
                        }
                        stack.push(new Element(node, 2));
                    }
                }
                else
                {
                    String label = ((VariableTerm) term).getLabel();
                    node = new ReadingNode(label);
                    if (!stack.isEmpty())
                    {
                        joinNodes(node, stack.peek().useNode(), attribute);
                        if (stack.peek().isDone())
                        {
                            stack.pop();
                        }
                        attribute = EdgeAttribute.SIMPLE;
                    }
                }
                nodes.add(node);
                if (outputNode == null)
                {
                    outputNode = node;
                }
            }
            builder.append("}\n");
            description = builder.toString();
        }

        public Node getOutputNode()
        {
            return outputNode;
        }
        
        public String getDescription()
        {
            return description;
        }
    }

    private class TransitionStructure
    {
        private final Node bridgeNode;
        
        private String description;
        private String transitionLabel;

        public TransitionStructure(Transition transition, int index)
        {
            transitionLabel = String.format("T%d", index);
            
            bridgeNode = new OperationNode(NodeOperation.CONJUNCTION);
            Expression condition = transition.getCondition();
            var transitionCondition = new ExpressionStructure(condition, index);
            joinNodes(transitionCondition.getOutputNode(), bridgeNode);
            
            StringBuilder builder = new StringBuilder();
            builder.append("subgraph ")
                    .append(transitionLabel)
                    .append("\n");
            builder.append("{\n");
            builder.append("node [style=\"filled\" color=\"lightgrey\"];\n");
            builder.append(transitionLabel)
                    .append("_Bridge ")
                    .append(bridgeNode.getDescription())
                    .append(";\n");
            builder.append(transitionCondition.getDescription())
                    .append("\n");
            builder.append("C")
                    .append(index)
                    .append("_N")
                    .append(index)
                    .append(" -> ")
                    .append(transitionLabel)
                    .append("_Bridge;\n")
                    .append("}\n");
            description = builder.toString();
        }

        public Node getBridgeNode()
        {
            return bridgeNode;
        }
        
        public String getDescription()
        {
            return description;
        }
    }

    private String graphVizDescription;

    public void constructGraph(SFC sfc)
    {
        /* Create the structures representing steps. */
        Map<Step, StepStructure> stepStructures = new HashMap<>();
        List<Step> steps = sfc.getSteps();
        for (var step : steps)
        {
            stepStructures.put(step, new StepStructure(step));
        }

        /* Create the structures representing the transitions. */
        Map<Transition, TransitionStructure> transitionStructures = new HashMap<>();
        List<Transition> transitions = sfc.getTransitions();
        int index = 0;
        for (var transition : transitions)
        {
            transitionStructures.put(transition,
                    new TransitionStructure(transition, index++));
        }

        /* Join! */
        for (var transition : transitions)
        {
            var transitionStr = transitionStructures.get(transition);

            /* Backward links. */
            List<Step> precedingSteps = transition.getPredecessors();
            for (var step : precedingSteps)
            {
                var stepStr = stepStructures.get(step);
                joinNodes(stepStr.getOutputNode(), transitionStr.getBridgeNode());
                joinNodes(transitionStr.getBridgeNode(), stepStr.getClearingNode());
            }

            /* Forward links. */
            List<Step> succeedingSteps = transition.getSuccessors();
            for (var step : succeedingSteps)
            {
                var stepStr = stepStructures.get(step);
                joinNodes(transitionStr.getBridgeNode(), stepStr.getSettingNode());
            }
        }
    }

    public String getDescription()
    {
        return graphVizDescription;
    }
}
