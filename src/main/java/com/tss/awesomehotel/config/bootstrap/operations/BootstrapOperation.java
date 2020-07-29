package com.tss.awesomehotel.config.bootstrap.operations;

import org.springframework.context.annotation.Bean;

/**
 * This is the interface to implement by the different classes
 * to perform an operation when the application starts
 */
public interface BootstrapOperation
{
    /**
     * The method that will be executed
     */
    void executeBootstrapOperation();
}
