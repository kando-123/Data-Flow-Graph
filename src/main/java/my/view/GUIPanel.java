/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package my.view;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.*;
import javax.swing.*;
import javax.swing.border.*;
import my.dfg.*;
import my.sfc.*;

interface Controller
{
    public void report(String arg);
}

class InputPanel extends JPanel implements ActionListener
{
    private Controller parent;
    private JTextField path;

    public InputPanel(Controller controller)
    {
        super(new GridBagLayout());
        parent = controller;

        // Title
        TitledBorder title = BorderFactory.createTitledBorder("Input");
        setBorder(title);

        // Text input
        path = new JTextField(20);
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.weighty = 1;
        c.gridx = 0;
        c.gridy = 0;
        add(path, c);

        // Button
        JButton button = new JButton("Generate DOT");
        button.setActionCommand("generate");
        button.addActionListener(this);
        c = new GridBagConstraints();
        c.weightx = 1;
        c.weighty = 1;
        c.gridx = 1;
        c.gridy = 0;
        add(button, c);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        parent.report(path.getText());
    }
}

class OutputPanel extends JPanel
{
    private JLabel response;

    public OutputPanel()
    {
        super(new GridBagLayout());
        TitledBorder title = BorderFactory.createTitledBorder("Output");
        setBorder(title);

        response = new JLabel("(Type the path in the input field and press the button.)");
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        add(response, c);
    }

    public void setResponse(String newResponse)
    {
        response.setText(newResponse);
    }
}

/**
 *
 * @author Kay Jay O'Nail
 */
public class GUIPanel extends JPanel implements Controller
{
    private InputPanel inputPanel;
    private OutputPanel outputPanel;

    public GUIPanel()
    {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        inputPanel = new InputPanel(this);
        add(inputPanel);

        outputPanel = new OutputPanel();
        add(outputPanel);
    }

    @Override
    public void report(String path)
    {
        final int XML_LENGTH = ".xml".length();
        try
        {
            Path inputPath = Paths.get(path);
            if (Files.exists(inputPath))
            {
                String inputString = inputPath.toString();
                String extension = inputString.substring(inputString.length() - XML_LENGTH);
                if (extension.equals(".xml"))
                {
                    Path parentDirectory = inputPath.getParent();
                    String fileName = inputPath.getFileName().toString();
                    String newName = fileName.substring(0, fileName.length() - XML_LENGTH).concat(".dot");
                    Path outputPath = parentDirectory != null
                            ? parentDirectory.resolve(newName) : Paths.get(newName);

                    SFC sfc = new SFC();
                    sfc.readFromXML(path);
                    Graph dfg = new Graph();
                    dfg.constructGraph(sfc);

                    FileWriter writer = new FileWriter(newName);
                    BufferedWriter bufferedWriter = new BufferedWriter(writer);
                    bufferedWriter.write(dfg.toString());
                    bufferedWriter.close();
                    
                    outputPanel.setResponse(String.format("The description was saved in: %s",
                            outputPath.toString()));
                }
                else
                {
                    outputPanel.setResponse("XML file was expected.");
                }
            }
            else
            {
                outputPanel.setResponse("The file does not exist.");
            }
        }
        catch (Exception e)
        {
            outputPanel.setResponse(e.getMessage());
        }
    }
}
