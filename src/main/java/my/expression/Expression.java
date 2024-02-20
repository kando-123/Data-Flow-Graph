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
         */

        String[] tokens = text.split(" ");
        Queue<String> queue = new LinkedList<>(Arrays.asList(tokens));

//        checking if each token is a valid term
        while (!queue.isEmpty())
        {
            String token = queue.poll();
            Term termToAdd;
            try {
                termToAdd = OperationTerm.makeTerm(token);
                terms.add(termToAdd);
            } catch (Exception e) {
                try {
                    termToAdd = VariableTerm.makeTerm(token);
                    terms.add(termToAdd);
                } catch (Exception ex) {
                    System.out.println("Term: " + token + " is not an operation or a variable");
                }
            }
        }
    }

    public int size()
    {
        return terms.size();
    }

    public Term get(int index)
    {
        return (index >= 0 && index < terms.size()) ? terms.get(index) : null;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        for (Term term : terms)
        {
            sb.append(term.toString());
            sb.append(" ");
        }

        return sb.toString();
    }
}
