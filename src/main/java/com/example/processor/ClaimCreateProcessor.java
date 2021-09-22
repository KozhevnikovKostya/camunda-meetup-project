package com.example.processor;

import com.example.entity.AccountClaim;
import com.example.service.AccountClaimService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.example.processor.ProcessConstants.CLAIM_ID;
import static com.example.processor.ProcessConstants.VIP_STATUS;

@Component
@RequiredArgsConstructor
public class ClaimCreateProcessor implements JavaDelegate {

    private final AccountClaimService accountClaimService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        AccountClaim accountClaim = new AccountClaim();
        boolean vip = (boolean) execution.getVariable(VIP_STATUS);
        Long id = accountClaimService.save(accountClaim);
        execution.setVariable(CLAIM_ID, id);

    }
}
