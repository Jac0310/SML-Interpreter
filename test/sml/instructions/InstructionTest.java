package sml.instructions;

import org.junit.Before;
import org.junit.Test;
import sml.Instruction;
import sml.Labels;
import sml.Machine;
import sml.Registers;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by jthoma24 on 15/03/2019.
 */
public class InstructionTest {

    public Machine m = new Machine();
    public Instruction i;
    @Before
    public void initialize()
    {
        m = new Machine();
        m.setRegisters(new Registers());
    }

    @Test
    public void mulTest()
    {
        i = new MulInstruction("f0", 1, 2, 3);
        m.getRegisters().setRegister(2, 4);
        m.getRegisters().setRegister(3, 6);

        i.execute(m);
        assert m.getRegisters().getRegister(1) == 24;
    }

    @Test
    public void linTest()
    {
        i = new LinInstruction("f0", 2, 3);
        m.setRegisters(new Registers());
        m.getRegisters().setRegister(3, 6);

        i.execute(m);
        assert m.getRegisters().getRegister(2) == 3;
    }


    @Test
    public void divTest()
    {
        i = new DivInstruction("f0", 1, 2, 3);
        m.getRegisters().setRegister(2, 6);
        m.getRegisters().setRegister(3, 4);
        int result = 6 /4;
        i.execute(m);
        assert m.getRegisters().getRegister(1) == result;
    }

    @Test (expected = ArithmeticException.class)
    public void divTest1()
    {
        i = new DivInstruction("f0", 1, 2, 3);
        i.execute(m);
    }

    @Test
    public void addTest()
    {
        i = new AddInstruction("f0", 1, 2, 3);
        m.getRegisters().setRegister(2, 4);
        m.getRegisters().setRegister(3, 6);

        i.execute(m);
        assert m.getRegisters().getRegister(1) == 10;
    }

    @Test
    public void subTest()
    {
        i = new SubInstruction("f0", 1, 2, 3);
        m.getRegisters().setRegister(2, 4);
        m.getRegisters().setRegister(3, 6);

        i.execute(m);
        assert m.getRegisters().getRegister(1) == -2;
    }

    @Test
    public void bnzTest()
    {
        Labels labels = new Labels();
        labels.addLabel("l0");
        labels.addLabel("l1");
        labels.addLabel("l2");
        labels.addLabel("f0");
        labels.addLabel("f1");
        labels.addLabel("f2");

        m.setLabels(labels);

        Instruction load1 = new LinInstruction("l0", 2, 3);
        Instruction load2 = new LinInstruction("l1", 3, 1);
        Instruction load3 = new LinInstruction("l2", 4, 1);

        i = new SubInstruction("f0", 2, 2, 3);
        Instruction i1 = new BnzInstruction("f1", 2, "f0");
        Instruction i2 = new AddInstruction("f2", 5, 4, 4);
        List<Instruction> ins = new ArrayList<>();

        ins.add(load1);
        ins.add(load2);
        ins.add(load3);

        ins.add(i);
        ins.add(i1);
        ins.add(i2);

        m.setProg(ins);
        //i1.execute(m);
        m.execute();

        assert m.getRegisters().getRegister(1) == 0;

        assert m.getPc() == 6;

        assert m.getRegisters().getRegister(5) == 2;
    }

    @Test
    public void runProgram1()
    {
        //Check output visually!
        String[] args1 = {"resources/test1.sml"};
        String[] args2 = {"resources/test2.sml"};
        String[] args3 = {"resources/test3.sml"};
        Machine.main(args1);
        Machine.main(args2);
        Machine.main(args3);
    }

}