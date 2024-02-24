/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package my.sfc;

import my.expression.*;
import java.io.File;
import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

/**
 *
 * @author Kay Jay O'Nail
 */
public class SFC
{
    private final List<Step> steps;
    private final List<Transition> transitions;

    public List<Step> getSteps()
    {
        return Collections.unmodifiableList(steps);
    }

    public List<Transition> getTransitions()
    {
        return Collections.unmodifiableList(transitions);
    }

    public SFC()
    {
        steps = new ArrayList<>();
        transitions = new ArrayList<>();
    }

    public void readFromXML(String fileName) throws Exception
    {
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(fileName));

        /* --- Read steps --- */
        
        /* Auxiliary maps. */
        Map<String, Step> stepIdentifiers = new HashMap<>();
        Map<Step, List<String>> stepInputs = new HashMap<>();

        /* Process each XML-node representing a step. */
        NodeList stepNodes = document.getElementsByTagName("step");
        for (int i = 0; i < stepNodes.getLength(); ++i)
        {
            /* Get the node to be processed. */
            Node node = stepNodes.item(i);

            /* Extract attributes. */
            NamedNodeMap attributes = node.getAttributes();
            String identifier = attributes.getNamedItem("localId").getNodeValue();
            String name = attributes.getNamedItem("name").getNodeValue();

            /* Create the step and add it to the list. */
            Step step = new Step(name);
            steps.add(step);

            /* Remember the identifier. */
            stepIdentifiers.put(identifier, step);

            /* Remember the backward links. */
            List<String> inputs = new ArrayList<>();
            Element element = (Element) node;
            NodeList connections = element.getElementsByTagName("connection");
            for (int j = 0; j < connections.getLength(); ++j)
            {
                Node connection = connections.item(j);
                String input = connection.getAttributes()
                        .getNamedItem("refLocalId").getNodeValue();
                inputs.add(input);
            }
            stepInputs.put(step, inputs);
        }

        /* --- Read transitions --- */
        
        /* Auxiliary maps. */
        Map<String, Transition> transitionIdentifiers = new HashMap<>();
        Map<Transition, List<String>> transitionInputs = new HashMap<>();

        /* Process each XML-node representing a step. */
        NodeList transitionNodes = document.getElementsByTagName("transition");
        for (int i = 0; i < transitionNodes.getLength(); ++i)
        {
            /* Get the node to be processed. */
            Node node = transitionNodes.item(i);
            Element element = (Element) node;

            /* Extract attributes. */
            NamedNodeMap attributes = node.getAttributes();
            String identifier = attributes.getNamedItem("localId").getNodeValue();

            NodeList pList = element.getElementsByTagName("xhtml:p");
            String expression = ((Element) pList.item(0)).getTextContent();
            
            /* Create the step and add it to the list. */
            Transition transition = new Transition(new Expression(expression));
            transitions.add(transition);

            /* Remember the identifier. */
            transitionIdentifiers.put(identifier, transition);

            /* Remember the backward links. */
            List<String> inputs = new ArrayList<>();
            NodeList connections = element.getElementsByTagName("connection");
            for (int j = 0; j < connections.getLength(); ++j)
            {
                Node connection = connections.item(j);
                String input = connection.getAttributes()
                        .getNamedItem("refLocalId").getNodeValue();
                inputs.add(input);
            }
            transitionInputs.put(transition, inputs);
        }

        /* --- Read divergences --- */
        
        NodeList divergenceNodes = document
                .getElementsByTagName("simultaneousDivergence");
        for (int i = 0; i < divergenceNodes.getLength(); ++i)
        {
            /* Get the node to be processed. */
            Node node = divergenceNodes.item(i);

            String identifier = node.getAttributes()
                    .getNamedItem("localId").getNodeValue();
            
            Element element = (Element) node;
            NodeList connections = element.getElementsByTagName("connection");
            String input = connections.item(0).getAttributes()
                    .getNamedItem("refLocalId").getNodeValue();
            
            /* For every step that had this divergence as input,
               replace the divergence's identifier with the transition's one. */
            for (var list : stepInputs.values())
            {
                if (list.contains(identifier))
                {
                    list.remove(identifier);
                    list.add(input);
                }
            }
        }
        
        /* --- Read convergences --- */
        
        NodeList convergenceNodes = document
                .getElementsByTagName("simultaneousConvergence");
        for (int i = 0; i < convergenceNodes.getLength(); ++i)
        {
            /* Get the node to be processed. */
            Node node = convergenceNodes.item(i);
            
            String identifier = node.getAttributes()
                    .getNamedItem("localId").getNodeValue();
            
            Element element = (Element) node;
            NodeList connections = element.getElementsByTagName("connection");
            
            List<String> inputs = new ArrayList<>();
            for (int j = 0; j < connections.getLength(); ++j)
            {
                String input = connections.item(j).getAttributes()
                        .getNamedItem("refLocalId").getNodeValue();
                inputs.add(input);
            }
            
            /* For the transition that had this convergence as input,
               replace the convergence's identifier with the steps' ones. */
            for (var list : transitionInputs.values())
            {
                if (list.contains(identifier))
                {
                    list.remove(identifier);
                    list.addAll(inputs);
                }
            }
        }
        
        /* Join that stuff! */
        for (var step : steps)
        {
            /* List of the identifiers of the  */
            List<String> precedingTransitions = stepInputs.get(step);
            for (var identifier : precedingTransitions)
            {
                Transition transition = transitionIdentifiers.get(identifier);
                step.addPrecedingTransition(transition);
                transition.addSucceedingStep(step);
            }
        }
        for (var transition : transitions)
        {
            List<String> precedingSteps = transitionInputs.get(transition);
            for (var identifier : precedingSteps)
            {
                Step step = stepIdentifiers.get(identifier);
                transition.addPrecedingStep(step);
                step.addSucceedingTransition(transition);
            }
        }
    }
    
    @Override
    public String toString()
    {
        StringBuilder description = new StringBuilder();
        
        description.append("Sequential Function Chart:\n\nSteps:");
        for (var step : steps)
        {
            description.append(" - ").append(step.getLabel()).append("\n\t");
            for (var input : step.getPrecedingTransitions())
            {
                description.append(input.toString()).append("; ");
            }
            description.append("\n\t");
            for (var output : step.getSucceedingTransitions())
            {
                description.append(output.toString()).append("; ");
            }
            description.append("\n");
        }
        for (var transition : transitions)
        {
            description.append(" - ").append(transition.toString()).append("\n\t");
            for (var input : transition.getPredecessors())
            {
                description.append(input.getLabel()).append("; ");
            }
            description.append("\n\t");
            for (var output : transition.getSuccessors())
            {
                description.append(output.getLabel()).append("; ");
            }
            description.append("\n");
        }
        
        return description.toString();
    }
    
    
}
