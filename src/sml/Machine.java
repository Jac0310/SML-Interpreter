package sml;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents the machine, the context in which programs run.
 * <p>
 * An instance contains 32 registers and methods to access and change them.
 *
 * @author ...
 */
public final class Machine {
    // The labels in the SML program, in the order in which
    // they appear (are defined) in the program

    private Labels labels;

    // The SML program, consisting of prog.size() instructions, each
    // of class Instruction (or one of its subclasses)
    private List<Instruction> program;

    // The registers of the SML machine
    private Registers registers;

    // The program counter; it contains the index (in prog) of
    // the next instruction to be executed.

    private int pc;

    {
        labels = new Labels();
        program = new ArrayList<>();
        pc = 0;
    }

    /**
     * Initialises the system and executes the program.
     *
     * @param args name of the file containing the program text.
     */
    public static void main(String[] args) {

        if (args.length != 1) {
            System.err.println("Incorrect number of arguments - Machine <file> - required");
            System.exit(-1);
        }

        Machine m = new Machine();
        Translator t = new Translator(args[0]);
        t.readAndTranslate(m.getLabels(), m.getProg());

        System.out.println("Here is the program; it has " + m.getProg().size() + " instructions.");
        System.out.println(m);

        System.out.println("Beginning program execution.");
        m.execute();
        System.out.println("Ending program execution.");

        System.out.println("Values of registers at program termination:" + m.getRegisters() + ".");
    }

    /**
     * String representation of the program under execution.
     *
     * @return pretty formatted version of the code.
     */
    @Override
    public String toString() {
        StringBuffer s = new StringBuffer();
        for (int i = 0; i != getProg().size(); i++) {
            s.append(getProg().get(i)).append("\n");
        }
        return s.toString();
    }

    /**
     * Execute the program in prog, beginning at instruction 0.
     * Precondition: the program and its labels have been store properly.
     */
    public void execute() {
        setPc(0);
        setRegisters(new Registers());
        while (getPc() < getProg().size()) {
            Instruction ins = getProg().get(getPc()); //fetch instruction
            setPc(getPc() + 1); // increment pc
            ins.execute(this); // execute instruction
        }
    }

    public Labels getLabels() {
        return this.labels;
    }

    public void setLabels(Labels labs) {
        this.labels = labs;
    }

    public List<Instruction> getProg() {
        return this.program;
    }

    public void setProg(List<Instruction> prg) {
        this.program = prg;
    }

    public Registers getRegisters() {
        return this.registers;
    }

    public void setRegisters(Registers regs) {
        this.registers = regs;
    }

    public int getPc() {
        return this.pc;
    }

    public void setPc(int programCounter) {
        this.pc = programCounter;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Machine)) {
            return false;
        }
        final Machine other = (Machine) o;
        if (!other.canEqual((Object) this)) {
            return false;
        }
        final Object thislabels = this.labels;
        final Object otherlabels = other.labels;
        if (!Objects.equals(thislabels, otherlabels)) {
            return false;
        }
        final Object thisprog = this.program;
        final Object otherprog = other.program;
        if (!Objects.equals(thisprog, otherprog)) {
            return false;
        }
        final Object thisregisters = this.registers;
        final Object otherregisters = other.registers;
        if (!Objects.equals(thisregisters, otherregisters)) {
            return false;
        }
        if (this.pc != other.pc) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object labs = this.labels;
        result = result * PRIME + (labs == null ? 0 : labs.hashCode());
        final Object prg = this.program;
        result = result * PRIME + (prg == null ? 0 : prg.hashCode());
        final Object regs = this.registers;
        result = result * PRIME + (regs == null ? 0 : regs.hashCode());
        result = result * PRIME + this.pc;
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof Machine;
    }
}
