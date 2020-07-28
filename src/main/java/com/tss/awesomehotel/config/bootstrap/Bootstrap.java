package com.tss.awesomehotel.config.bootstrap;

import com.tss.awesomehotel.config.bootstrap.operations.BootstrapOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class Bootstrap implements CommandLineRunner
{

    @Autowired
    private BootstrapOperation bootstrapOperation;

    @Override
    public void run(String... args)
    {
        this.bootstrapOperation.executeBootstrapOperation();
    }
}
