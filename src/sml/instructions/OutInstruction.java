package sml.instructions;

import sml.Instruction;
import sml.Machine;

public class OutInstruction extends Instruction {
    private int register;
    /**
     * @param lab
     * @param op
     */
    public OutInstruction(String lab, String op) {
        super(lab, op);
    }

    /**
     * Initialise the instruction
     *
     * @param lab of the instruction
     * @param reg to work with
     */
    public OutInstruction(String lab, int reg) {
        super(lab, "out");
        this.register = reg;
    }

    /**
     * Execute the instruction in the context of the Machine
     *
     * @param m the machine
     */
    @Override
    public void execute(Machine m) {
        System.out.println("Value is " + m.getRegisters().getRegister(register));
    }

    /**
     * String representation of the instruction
     *
     * @return incorporating the value
     */
    @Override
    public String toString() {
        return super.toString() + " output register " + register;
    }
}
