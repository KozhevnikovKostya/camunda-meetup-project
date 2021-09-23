package com.example.processor;

import com.example.bpm.ActionRequestAdapter;
import com.example.bpm.context.ExecutionContext;
import com.example.processor.rq.StartOpenAccountRequest;
import org.springframework.stereotype.Component;

import static com.example.processor.ProcessConstants.CLIENT_FIO;
import static com.example.processor.ProcessConstants.VIP_STATUS;

@Component
public class StartProcessCustomHandler implements ActionRequestAdapter<StartOpenAccountRequest> {
    @Override
    public void process(ExecutionContext executionContext, StartOpenAccountRequest requestObject) throws Exception {
        executionContext.setVariable(VIP_STATUS, requestObject.isVip());
        executionContext.setVariable(CLIENT_FIO, requestObject.getFio());
    }
}
