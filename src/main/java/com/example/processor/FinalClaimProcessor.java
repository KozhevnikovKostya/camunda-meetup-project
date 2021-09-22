package com.example.processor;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import static com.example.processor.ProcessConstants.FINAL;
import static com.example.processor.ProcessConstants.FINAL_ERROR_CODE;

@Component
public class FinalClaimProcessor implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        if((boolean) execution.getVariable(FINAL)){
            System.out.println("Super");
        }else {
            throw new BpmnError(FINAL_ERROR_CODE, "tra-ta-ta-ta-ta");
        }
    }
}
