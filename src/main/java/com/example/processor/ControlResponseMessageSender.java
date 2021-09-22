package com.example.processor;

import com.example.entity.AccountClaim;
import com.example.service.AccountClaimService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import static com.example.processor.ProcessConstants.CLAIM_ID;

@Component
@RequiredArgsConstructor
public class ControlResponseMessageSender implements JavaDelegate {

    private final AccountClaimService accountClaimService;
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        AccountClaim accountClaim = accountClaimService.findAccountClaim((String) execution.getVariable(CLAIM_ID));
        accountClaim.setUserName((String) execution.getVariable("USER_NAME"));
        accountClaim.setClientName((String) execution.getVariable("CLIENT_NAME"));
        accountClaim.setAccountNumber((String) execution.getVariable("ACCOUNT_NUMBER"));
        accountClaimService.save(accountClaim);
        /**
         * код который отсылает запрос на контроль другому пользователю
         */
    }
}
