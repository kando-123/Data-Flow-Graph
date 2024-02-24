/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package my.main;

import my.dfg.Graph;
import my.sfc.SFC;

/**
 *
 * @author Kay Jay O'Nail
 */
public class DataFlowGraph
{
    public static void main(String[] args)
    {
        SFC sfc = new SFC();
        try
        {
            sfc.readFromXML("plc.xml");
            Graph dfg = new Graph();
            dfg.constructGraph(sfc);
            System.out.println(dfg.toString());
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }
}
