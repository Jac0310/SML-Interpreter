package sml;

import java.util.List;

public interface Factory {
    Factory instance = null;


    static Factory getinstance()
    {
        return instance;
    }

    Instruction getInstruction(String name, Object... args);
}
