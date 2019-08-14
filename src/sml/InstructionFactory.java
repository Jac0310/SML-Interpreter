package sml;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;

import java.io.FileInputStream;
import java.util.Properties;

public class InstructionFactory implements Factory {
     /*
    Code is repurposing of implementation in class shared repo springDependencyInjection
     */

    private final String FILENAME = "resources/beanspring.properties";

    private BeanFactory factory;

    private static InstructionFactory instance = null;
    static
    {
        instance = new InstructionFactory();
    }

    private InstructionFactory()
    {
        try
        {
              factory = getBeanFactory();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static InstructionFactory getInstance()
    {
        return instance;
    }

    public Instruction getInstruction(String name, Object... args) {
        try {
            return (Instruction)getBeanFactory().getBean(name, args);
        } catch (Exception e) {
            e.printStackTrace();
        };
        return null;
    }




    private  BeanFactory getBeanFactory() throws Exception {

        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();

        // create a definition reader
        PropertiesBeanDefinitionReader rdr = new PropertiesBeanDefinitionReader(
                factory);

        // load the configuration options
        Properties props = new Properties();
        props.load(new FileInputStream(FILENAME));

        rdr.registerBeanDefinitions(props);

        return factory;
    }
}
