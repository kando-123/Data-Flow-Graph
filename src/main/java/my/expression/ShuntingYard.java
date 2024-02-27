package my.expression;

import java.util.Stack;

public class ShuntingYard {
    private final String input;
    private String output = "";
    private String[] tokens;

    public ShuntingYard(String input)
    {
        this.input = input;
    }

    public String[] transform()
    {
        tokensFromInput();
//        for (String token : tokens)
//        {
//            System.out.println(token);
//        }
        return shuntingYard();
    }

    private boolean isOperand(String token)
    {
        return token.equals("AND") || token.equals("OR") || token.equals("NOT");
    }

    private int precedence(String token)
    {
        return switch (token) {
            case "AND" -> 2;
            case "OR" -> 1;
            case "NOT" -> 3;
            default -> 0;
        };
    }

    private boolean isLeftAssociative(String token)
    {
        return token.equals("AND") || token.equals("OR");
    }

    private boolean isRightAssociative(String token)
    {
        return token.equals("NOT");
    }

    private void tokensFromInput()
    {
//        LP -> left parenthesis, RP -> right parenthesis
        tokens = input.replaceAll("\\(", "_LP ").replaceAll("\\)", " _RP").split(" ");
    }

    private String[] shuntingYard()
    {
        Stack<String> stack = new Stack<>();
        for (String token : tokens)
        {
            if (isOperand(token))
            {
                while (!stack.isEmpty() && isOperand(stack.peek()))
                {
                    if ((isLeftAssociative(token) && precedence(token) <= precedence(stack.peek())) || (isRightAssociative(token) && precedence(token) < precedence(stack.peek())))
                    {
                        output += stack.pop() + " ";
                    }
                    else
                    {
                        break;
                    }
                }
                stack.push(token);
            }
            else if (token.equals("_LP"))
            {
                stack.push(token);
            }
            else if (token.equals("_RP"))
            {
                while (!stack.isEmpty() && !stack.peek().equals("_LP"))
                {
                    output += stack.pop() + " ";
                }
                stack.pop();
            }
            else
            {
                output += token + " ";
            }
        }
        while (!stack.isEmpty())
        {
            output += stack.pop() + " ";
        }
        return output.split(" ");
    }
}
