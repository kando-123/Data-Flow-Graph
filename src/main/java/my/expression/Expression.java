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
     * Constructor.
     *
     * Explanation by example:
     *
     * text: "not E1 and not E2" intermediate form (Polish notation): and, not,
     * E1, not, E2 final form: Expression.terms = List{Term("and"), Term("not"),
     * Term("E1"), Term("not"), Term("E2")}. Use makeTerm to instantiate the
     * terms.
     *
     * @param text
     * @throws Exception if text does not represent a valid expression
     */
    public Expression(String text) throws Exception
    {
        terms = new ArrayList<>();

        /* TODO: Extracting the text into a queue of terms representing
           the Boolean expression in Polish notation.
           The input may contain:
            + operations:
              - negation: !, not, NOT;
              - disjunction: |, ||, or, OR;
              - conjunction: &, &&, and, AND;
            + variables - format:
                letter + [letter/digit/underscore]{0, 1 or many}
         */
    }
    
    public int size()
    {
        return terms.size();
    }

    public Term get(int index)
    {
        return (index >= 0 && index < terms.size()) ? terms.get(index) : null;
    }
}
