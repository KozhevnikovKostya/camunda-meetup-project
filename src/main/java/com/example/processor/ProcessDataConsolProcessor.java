package com.example.processor;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class ProcessDataConsolProcessor implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        execution.getVariables().entrySet().forEach(s -> {
            System.out.println(s.getKey() + " : " + s.getValue());
        });
    }
}
