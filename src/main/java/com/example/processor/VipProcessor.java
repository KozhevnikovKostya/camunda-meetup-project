package com.example.processor;

import com.example.entity.AccountClaim;
import com.example.service.AccountClaimService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import static com.example.processor.ProcessConstants.CLAIM_ID;
import static com.example.processor.ProcessConstants.VIP_STATUS;

@Component
@RequiredArgsConstructor
public class VipProcessor implements JavaDelegate {
    private final AccountClaimService accountClaimService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        AccountClaim accountClaim = accountClaimService.findAccountClaim((String) execution.getVariable(CLAIM_ID));
        accountClaim.setVip(true);
        accountClaimService.save(accountClaim);
    }
}
