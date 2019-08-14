package sml.instructions;

import sml.Instruction;
import sml.Machine;

/**
 * This class represents the Add instruction from the language.
 *
 * @author ...
 */
public final class BnzInstruction extends Instruction {
    private int val;
    private String label;
    private String newLabel;


    /**
     * @param label of the instruction
     * @param op    the operands
     */
    public BnzInstruction(String label, String op) {
        super(label, op);
    }

    /**
     * @param label     of the instruction
     * @param register      of the computation
     * @param newLabel the next label
     */
    public BnzInstruction(String label, int register, String newLabel) {
        this(label, "bnz");
        this.val = register;
        this.label = label;
        this.newLabel = newLabel;
    }

    /**
     * Execute the instruction, probably modifying the registers.
     *
     * @param m the machine under which the instruction executes
     */
    @Override
    public void execute(Machine m) {
//        if reg is not 0 change label to new label and
//        change pc to that of new label
        if (m.getRegisters().getRegister(val) != 0)
        {
            m.setPc(m.getLabels().indexOf(newLabel));
        }
    }

    /**
     * String representation of the instruction
     *
     * @return representation of the operands and result
     */
    @Override
    public String toString() {
        return super.toString() + " test " + val + " if not zero go to " + newLabel;
    }


}
