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
public enum PastelColor
{
    RED("#FF9999"),
    YELLOW("#FFFF99"),
    GREEN("#99FF99"),
    CYAN("#99FFFF"),
    BLUE("#9999FF"),
    MAGENTA("#FF99FF"),
    
    ORANGE("FFCC99"),
    LIME("#CCFF99"),
    TURQUOISE("#99FFCC"),
    AZURE("#99CCFF"),
    VIOLET("#CC99FF"),
    CRIMSON("#FF99CC"),
    
    OTHER("#404040");
    
    private String hex;
    
    private PastelColor(String hex)
    {
        this.hex = hex;
    }
    
    private static int index = 0;
    
    public static PastelColor getNextColor()
    {
        PastelColor color = null;
        switch (index)
        {
            case 0  -> color = RED;
            case 1  -> color = YELLOW;
            case 2  -> color = GREEN;
            case 3  -> color = CYAN;
            case 4  -> color = BLUE;
            case 5  -> color = MAGENTA;
            case 6  -> color = ORANGE;
            case 7  -> color = LIME;
            case 8  -> color = TURQUOISE;
            case 9  -> color = AZURE;
            case 10 -> color = VIOLET;
            case 11 -> color = CRIMSON;
            default -> color = OTHER;
        }
        ++index;
        if (index == 12)
        {
            index = 0;
        }
        return color;
    }
    
    @Override
    public String toString()
    {
        return hex;
    }
}
