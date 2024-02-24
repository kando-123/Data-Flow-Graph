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
    /* List of all nodes that belong to the graph. */
    private final List<Node> nodes;
    
    /* List of all edges that belong to the graph. */
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

    /* Auxiliary structure. It plays a role during construction of the graph. */
    private class StepStructure
    {
        private final Node settingNode;
        private final Node clearingNode;
        private final Node outputNode;

        private final String stepLabel;
        
        private final String description;

        public StepStructure(Step step)
        {
            /* Get the label. */
            stepLabel = step.getLabel();
            
            /* Create the nodes. */
            settingNode = new OperationNode(NodeOperation.DISJUNCTION);
            clearingNode = new OperationNode(NodeOperation.CONJUNCTION);
            outputNode = new OperationNode(NodeOperation.DISJUNCTION);
            Node internal = new OperationNode(NodeOperation.CONJUNCTION);
            Node getter = new ReadingNode(stepLabel);
            Node setter = new WritingNode(stepLabel);
            
            /* Add the nodes to the graph. */
            nodes.add(settingNode);
            nodes.add(internal);
            nodes.add(clearingNode);
            nodes.add(outputNode);
            nodes.add(getter);
            nodes.add(setter);
            
            /* Join the nodes. */
            joinNodes(settingNode, internal, EdgeAttribute.SIMPLE);
            joinNodes(internal, outputNode, EdgeAttribute.SIMPLE);
            joinNodes(clearingNode, outputNode, EdgeAttribute.SIMPLE);
            joinNodes(getter, internal, EdgeAttribute.NEGATION);
            joinNodes(getter, clearingNode, EdgeAttribute.SIMPLE);
            joinNodes(outputNode, setter, EdgeAttribute.SIMPLE);
            
            /* Create the description. */
            StringBuilder builder = new StringBuilder();
            
            // Description header.
            builder.append("subgraph ")
                    .append(stepLabel)
                    .append("\n");
            
            // Opening brace.
            builder.append("{\n");
            
            // Style of all nodes.
            builder.append("node [style=\"filled\", color=\"")
                    .append(PastelColor.getNextColor().toString())
                    .append("\"];\n");
            
            // Definitions of the nodes.
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
            
            // Connections of the nodes.
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
                    .append("_Internal [arrowhead=\"odot\"];\n");
            builder.append(stepLabel)
                    .append("_Output -> ")
                    .append(stepLabel)
                    .append("_Write;\n");
            
            // Closing brace.
            builder.append("}\n");
            
            // Remember the description.
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
    
    /* Auxiliary structure. It plays a role during construction of the graph. */
    private class ExpressionStructure
    {
        private Node outputNode;
        
        /* Label of the whole subgraph, and prefix of the nodes. */
        private String expressionLabel;
        
        private String description;

        /* Auxiliary structure. It plays a role during construction of the subgraph. */
        private class Element
        {
            private final Node node;
            private int counter;
            
            /* The identifier the node has in the final graph description. */
            private String identifier;

            public Element(Node node, int counter, String identifier)
            {
                this.node = node;
                this.counter = counter;
                this.identifier = identifier;
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
            
            public String getIdentifier()
            {
                return identifier;
            }
        }

        public ExpressionStructure(Expression expression, int index)
        {
            outputNode = null;
            Term term;
            EdgeAttribute attribute = EdgeAttribute.SIMPLE;
            Stack<Element> stack = new Stack<>();
            expressionLabel = String.format("C%d", index);
            
            /* Create the description. */
            StringBuilder builder = new StringBuilder();
            
            // Description header.
            builder.append("subgraph ")
                    .append(expressionLabel)
                    .append("\n");
            
            // Opening brace.
            builder.append("{\n");
            
            // Counter for enumerating the nodes.
            int nodeCounter = 0;
            
            /* Recreating the expression as a graph. */
            for (int i = 0; i < expression.termsCount(); ++i)
            {
                term = expression.getTerm(i);
                
                /* Create a node to represent the term
                   and incorporate it into the graph and into the description. */
                Node node;
                if (term.getType() == TermType.OPERATION)
                {
                    Operation operation = ((OperationTerm) term).getOperation();
                    if (operation == Operation.NEGATION)
                    {
                        /* Just remember to negate the next time. */
                        attribute = EdgeAttribute.NEGATION;
                        continue;
                    }
                    else
                    {
                        // Create the node.
                        node = new OperationNode(operation == Operation.DISJUNCTION
                                ? NodeOperation.DISJUNCTION : NodeOperation.CONJUNCTION);
                        
                        // Prepare the identifier: expression label _ node label.
                        String identifier = String.format("%s_N%d", expressionLabel, nodeCounter);
                        ++nodeCounter;
                        
                        // Define the node in the description.
                        builder.append(identifier)
                                .append(" ")
                                .append(node.getDescription())
                                .append(";\n");
                        
                        // Join to the latest node awaiting for resources, if any.
                        if (!stack.isEmpty())
                        {
                            // The node awaits on the peek of the stack.
                            joinNodes(node, stack.peek().useNode(), attribute);
                            
                            // Define the connection in the description.
                            builder.append(identifier)
                                    .append(" -> ")
                                    .append(stack.peek().getIdentifier());
                            
                            // Modify the arrow head in case of negation.
                            if (attribute == EdgeAttribute.NEGATION)
                            {
                                builder.append(" [arrowhead=\"odot\"]");
                            }
                            builder.append(";\n");
                            
                            // Pop the peek node if it has received all resources it needs.
                            if (stack.peek().isDone())
                            {
                                stack.pop();
                            }
                            
                            // Reset the attribute.
                            attribute = EdgeAttribute.SIMPLE;
                        }
                        
                        // Push the node onto the stack to await for resources.
                        stack.push(new Element(node, 2, identifier));
                    }
                }
                else
                {
                    // Create the node.
                    String variable = ((VariableTerm) term).getLabel();
                    node = new ReadingNode(variable);
                    
                    // Prepare the identifier.
                    String identifier = String.format("%s_N%d", expressionLabel, nodeCounter);
                    ++nodeCounter;
                    
                    // Define the node in the description.
                    builder.append(identifier)
                            .append(" ")
                            .append(node.getDescription())
                            .append(";\n");
                    
                    // Join to the latest node awaiting for resources, if any.
                    if (!stack.isEmpty())
                    {
                        // The node awaits on the peek of the stack.
                        joinNodes(node, stack.peek().useNode(), attribute);
                        
                        // Define the connection in the description.
                        builder.append(identifier)
                                .append(" -> ")
                                .append(stack.peek().getIdentifier());
                        
                        // Modify the arrow head in case of negation.
                        if (attribute == EdgeAttribute.NEGATION)
                        {
                            builder.append(" [arrowhead=\"odot\"]");
                        }
                        builder.append(";\n");
                        
                        // Pop the peek node if it has received all resources it needs.
                        if (stack.peek().isDone())
                        {
                            stack.pop();
                        }
                        
                        // Reset the attribute.
                        attribute = EdgeAttribute.SIMPLE;
                    }
                }
                
                /* Add the node to the graph. */
                nodes.add(node);
                
                /* Remember the output node. */
                if (outputNode == null)
                {
                    outputNode = node;
                }
            }
            
            // Closing brace.
            builder.append("}\n");
            
            // Remember the description.
            description = builder.toString();
        }

        public Node getOutputNode()
        {
            return outputNode;
        }
        
        public String getOutputIdentifier()
        {
            return String.format("%s_N0", expressionLabel);
        }
        
        public String getDescription()
        {
            return description;
        }
    }

    /* Auxiliary structure. It plays a role during construction of the graph. */
    private class TransitionStructure
    {
        private final Node bridgeNode;
        
        private String description;
        private String transitionLabel;

        public TransitionStructure(Transition transition, int index)
        {
            /* Prepare the label. */
            transitionLabel = String.format("T%d", index);
            
            /* Construct the bridge node. */
            bridgeNode = new OperationNode(NodeOperation.CONJUNCTION);
            
            /* Construct the expression structure and join it to the bridge node. */
            Expression condition = transition.getCondition();
            var conditionStr = new ExpressionStructure(condition, index);
            joinNodes(conditionStr.getOutputNode(), bridgeNode);
            
            /* Create desription. */
            StringBuilder builder = new StringBuilder();
            
            // Description header.
            builder.append("subgraph ")
                    .append(transitionLabel)
                    .append("\n");
            builder.append("{\n");
            
            // Style of the nodes.
            builder.append("node [style=\"filled\" color=\"lightgrey\"];\n");
            
            // Define the bridge node.
            builder.append(transitionLabel)
                    .append("_Bridge ")
                    .append(bridgeNode.getDescription())
                    .append(";\n");
            
            // Paste the whole description of the transition.
            builder.append(conditionStr.getDescription())
                    .append("\n");
            
            // Define the connection between the condition and the bridge node.
            builder.append(conditionStr.getOutputIdentifier())
                    .append(" -> ")
                    .append(transitionLabel)
                    .append("_Bridge;\n")
                    .append("}\n");
            
            // Remember the description.
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
        
        public String getLabel()
        {
            return transitionLabel;
        }
    }

    private String graphVizDescription;

    public void constructGraph(SFC sfc)
    {
        /* Create the description during creation. */
        StringBuilder builder = new StringBuilder();
        
        // Header.
        builder.append("digraph DFG\n");
        
        // Opening brace.
        builder.append("{\n");
        
        /* Create the structures representing steps. */
        Map<Step, StepStructure> stepStructures = new HashMap<>();
        List<Step> steps = sfc.getSteps();
        builder.append("/* Steps */\n");
        for (var step : steps)
        {
            StepStructure structure = new StepStructure(step);
            stepStructures.put(step, structure);
            
            // Append the description of the step structure.
            builder.append(structure.getDescription()).append("\n");
        }

        /* Create the structures representing the transitions. */
        Map<Transition, TransitionStructure> transitionStructures = new HashMap<>();
        List<Transition> transitions = sfc.getTransitions();
        int index = 0;
        builder.append("/* Transitions */\n");
        for (var transition : transitions)
        {
            TransitionStructure structure = new TransitionStructure(transition, index++);
            transitionStructures.put(transition, structure);
            
            // Append the structure of the transition.
            builder.append(structure.getDescription());
        }

        /* Join! */
        builder.append("/* Connections */\n");
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
                
                // Define those connections in the description.
                builder.append(stepStr.getLabel())
                        .append("_Output -> ")
                        .append(transitionStr.getLabel())
                        .append("_Bridge;\n");
                builder.append(transitionStr.getLabel())
                        .append("_Bridge -> ")
                        .append(stepStr.getLabel())
                        .append("_Clear;\n");
            }

            /* Forward links. */
            List<Step> succeedingSteps = transition.getSuccessors();
            for (var step : succeedingSteps)
            {
                var stepStr = stepStructures.get(step);
                joinNodes(transitionStr.getBridgeNode(), stepStr.getSettingNode());
                
                // Define that connection in the description.
                builder.append(transitionStr.getLabel())
                        .append("_Bridge -> ")
                        .append(stepStr.getLabel())
                        .append("_Set;\n");
            }
        }
        
        builder.append("}\n");
        graphVizDescription = builder.toString();
    }

    @Override
    public String toString()
    {
        return graphVizDescription;
    }
}
