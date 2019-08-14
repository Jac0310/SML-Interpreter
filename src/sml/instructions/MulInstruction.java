package sml.instructions;

import sml.Instruction;
import sml.Machine;

/**
 * Created by jthoma24 on 15/03/2019.
 */
public class MulInstruction extends Instruction {
    private int result;
    private int op1;
    private int op2;


    /**
     * @param label of the instruction
     * @param op    the operands
     */
    public MulInstruction(String label, String op) {
        super(label, op);
    }

    /**
     * @param label     of the instruction
     * @param res       of the computation
     * @param operand1 the first operand - corresponds to a register
     * @param operand2 the second operand - corresponds to a register
     */
    public MulInstruction(String label, int res, int operand1, int operand2) {
        this(label, "mul");
        this.result = res;
        this.op1 = operand1;
        this.op2 = operand2;
    }

    /**
     * Execute the instruction, probably modifying the registers.
     *
     * @param m the machine under which the instruction executes
     */
    @Override
    public void execute(Machine m) {
        int value1 = m.getRegisters().getRegister(op1);
        int value2 = m.getRegisters().getRegister(op2);
        m.getRegisters().setRegister(result, value1 * value2);

    }

    /**
     * String representation of the instruction
     *
     * @return representation of the operands and result
     */
    @Override
    public String toString() {
        return super.toString() + " " + op1 + " * " + op2 + " to " + result;
    }
}
