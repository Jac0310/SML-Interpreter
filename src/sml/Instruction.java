package sml;


import java.util.function.Function;

/**
 * This class represents an abstract instruction
 *
 * @author ...
 */
public abstract class Instruction {
    private String label;
    private String opcode;

    /**
     * Constructor: an instruction with label l and opcode op
     * (op must be an operation of the language)
     *
     * @param l  label
     * @param op operator
     */
    public Instruction(String l, String op) {
        setLabel(l);
        setOpcode(op);
    }

    protected final String getLabel() {
        return label;
    }

    protected final void setLabel(String lab) {
        this.label = lab;
    }

    protected final String getOpcode() {
        return opcode;
    }

    protected final void setOpcode(String op) {
        this.opcode = op;
    }

    // =

    /**
     * the representation "label: opcode" of this Instruction
     *
     * @return "label: opcode" of this Instruction
     */
    @Override
    public String toString() {
        return getLabel() + ": " + getOpcode();
    }



    /**
     * Execute this instruction on machine m.
     *
     * @param m the machine in which to execute the instruction.
     */
    public abstract void execute(Machine m);// visitor pattern
}
