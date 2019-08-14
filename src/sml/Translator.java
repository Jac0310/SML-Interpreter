package sml;

import sml.instructions.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * This class ....
 * <p>
 * The translator of a <b>S</b><b>M</b>al<b>L</b> program.
 *
 * @author ...
 */
public final class Translator {

    private static final String PATH = "";
    private HashMap<String, Class<?>> insToClass = new HashMap<String, Class<?>>();
    private HashMap<String, String> insToClassName = new HashMap<>();

    // word + line is the part of the current line that's not yet processed
    // word has no whitespace
    // If word and line are not empty, line begins with whitespace

    private String line = "";
    private String fileName; // source file of SML code

    public Translator(String file) {
        this.fileName = PATH + file;
        populateClassMap();
    }

    // translate the small program in the file into lab (the labels) and
    // prog (the program)
    // return "no errors were detected"

    private void populateClassMap()
    {
        insToClassName.put("add", "AddInstruction");
        insToClassName.put("sub", "SubInstruction");
        insToClassName.put("lin", "LinInstruction");
        insToClassName.put("bnz", "BnzInstruction");
        insToClassName.put("mul", "MulInstruction");
        insToClassName.put("div", "DivInstruction");
        insToClassName.put("out", "OutInstruction");
    }

    public boolean readAndTranslate(Labels labs, List<Instruction> prog) {
        try (Scanner sc = new Scanner(new File(fileName))) {
            // Scanner attached to the file chosen by the user
            // The labels of the program being translated
            labs.reset();
            // The program to be created
            prog.clear();

            try {
                line = sc.nextLine();
            } catch (NoSuchElementException ioE) {
                return false;
            }

            // Each iteration processes line and reads the next line into line
            while (line != null) {
                // Store the label in label
                String label = scan();

                if (label.length() > 0) {
                    Instruction ins = getInstruction(label);
                    if (ins != null) {
                        labs.addLabel(label);
                        prog.add(ins);
                    }
                }
                try {
                    line = sc.nextLine();
                } catch (NoSuchElementException ioE) {
                    return false;
                }
            }
        } catch (IOException ioE) {
            System.err.println("File: IO error " + ioE);
            System.exit(-1);
            return false;
        }
        return true;
    }

    // line should consist of an ML instruction, with its label already
    // removed. Translate line into an instruction with label
    // and return the instruction
    public Instruction getInstruction(String label) {

        if (line.equals("")) {
            return null;
        }

        String ins = scan();

        Object[] params = getParams(label);

        String className = insToClassName.get(ins);
        return InstructionFactory.getInstance().getInstruction(className, params);
    }

    private Object[] getParams(String label)
    {
        ArrayList<Object> params = new ArrayList<>();
        params.add(label);
        Integer max = Integer.MAX_VALUE;
        Object param;
        while (line.length() > 0)
        {
            String previous = line;
            param = scanInt();
            if (param.equals(max))
            {
                line = previous;
                param = scan();
            }
            params.add(param);
        }
        return params.toArray();
    }

    /*
     * Return the first word of line and remove it from line. If there is no
     * word, return ""
     */
    private String scan() {
        line = line.trim();
        if (line.length() == 0) {
            return "";
        }

        int i = 0;
        while (i < line.length() && line.charAt(i) != ' ' && line.charAt(i) != '\t') {
            i = i + 1;
        }
        String word = line.substring(0, i);
        line = line.substring(i);
        return word;
    }

    // Return the first word of line as an integer. If there is
    // any error, return the maximum int
    private int scanInt() {
        String word = scan();
        if (word.length() == 0) {
            return Integer.MAX_VALUE;
        }

        try {
            return Integer.parseInt(word);
        } catch (NumberFormatException e) {
            return Integer.MAX_VALUE;
        }
    }
}
