/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package my.main;

import javax.swing.*;
import my.dfg.*;
import my.sfc.*;
import my.view.*;

/**
 *
 * @author Kay Jay O'Nail
 */
public class DataFlowGraph
{
    public static void main(String[] args)
    {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("DOT description for DFG constructed from SFC read from XML");
        
        GUIPanel panel = new GUIPanel();
        window.add(panel);
        window.pack();
        
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
