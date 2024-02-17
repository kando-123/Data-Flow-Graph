/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package my.expression;

import java.util.*;

/**
 *
 * @author Kay Jay O'Nail
 */
public class Expression
{
    private List<Term> terms;
    
    /**
     * Explanation by example:
     * 
     *  text: "not E1 and not E2"
     *  intermediate form (Polish notation): and, not, E1, not, E2
     *  final form:
     *      Expression.terms = List{Term("and"), Term("not"), Term("E1"),
     *      Term("not"), Term("E2")}.
     * 
     * @param text 
     */
    public Expression(String text)
    {
        terms = new ArrayList<>();
        
        /* TODO: Extracting the text into a queue of terms representing
           the Boolean expression in Polish notation. */
    }
}
