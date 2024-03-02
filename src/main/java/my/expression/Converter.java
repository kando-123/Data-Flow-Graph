package my.expression;

/*
 * Class responsible for converting infix to prefix
 */
public class Converter {
    /*
     * Infix expression. Example: NOT (A AND B) OR (NOT C AND D)
     */
    private String infix;
    /*
     * Prefix expression. Example: OR NOT AND A B AND NOT C D -> need to be implemented to this form
     */
    private String prefix;

    /*
     * Constructor
     */
    public Converter(String infix) {
        this.infix = infix.toUpperCase();
    }

    /*
     * Get the infix expression
     */
    public String getInfix() {
        return infix;
    }

    public void transform() {
        //reverseInfixExpression();
        ShuntingYard shuntingYard = new ShuntingYard(infix);
        String[] tokens = shuntingYard.transform();
        prefix = String.join(" ", tokens);
        reversePrefixExpression();
    }

    private void reversePrefixExpression() {
// split by space
        String[] tokens = prefix.split(" ");
        StringBuilder sb = new StringBuilder();
        for (int i = tokens.length - 1; i >= 0; i--) {
            sb.append(tokens[i]);
            sb.append(" ");
        }
        prefix = sb.toString();
    }

    private void reverseInfixExpression() {
        StringBuilder sb = new StringBuilder(infix);
        for (int i = 0; i < sb.length(); i++) {
            if (sb.charAt(i) == '(') {
                sb.setCharAt(i, ')');
            } else if (sb.charAt(i) == ')') {
                sb.setCharAt(i, '(');
            }
        }
        //infix = sb.reverse().toString();
        //infix = infix.replace("DNA", "AND");
        //infix = infix.replace("RO", "OR");
        //infix = infix.replace("TON", "NOT");
    }

    /*
     * Get the prefix expression
     */
    public String getPrefix() {
        return prefix;
    }
}
