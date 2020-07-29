package com.tss.awesomehotel.config.bootstrap;

import com.tss.awesomehotel.config.bootstrap.operations.BootstrapOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * This is the class that will execute all the different {@link BootstrapOperation}
 * registered. This implements {@link CommandLineRunner} to execute code
 * at startup time
 */
@Component
public class Bootstrap implements CommandLineRunner
{

    /**
     * The list of {@link BootstrapOperation}
     */
    @Autowired
    private List<BootstrapOperation> bootstrapOperation;

    @Override
    public void run(String... args)
    {
        this.bootstrapOperation.stream().forEach(BootstrapOperation::executeBootstrapOperation);
    }
}
