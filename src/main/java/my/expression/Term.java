/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package my.expression;

/**
 * @author Kay Jay O'Nail
 */
public abstract class Term {
    private final TermType type;

    protected Term(TermType type) {
        this.type = type;
    }

    public TermType getType() {
        return type;
    }

    private boolean isVariableLabel(String text) {
        /* Temporary! */
        return true;
    }
}
