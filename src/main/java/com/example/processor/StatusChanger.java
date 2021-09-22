package com.example.processor;

import java.time.LocalDateTime;
import java.util.List;

import com.example.entity.AccountClaim;
import com.example.entity.DocumentState;
import com.example.service.AccountClaimService;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


import static org.hibernate.annotations.common.util.StringHelper.isNotEmpty;

/**
 * Компонент меняющий статус у заявок
 *
 * @author Zolotarev-EA
 * @since 05.07.2021
 */
@Component
@RequiredArgsConstructor
public class StatusChanger {

    private final AccountClaimService accountClaimService;

    public void setStatus(String claimId, DocumentState state) {
        setStatus(claimId, state, null);
    }

    /**
     * Установить статус и дату последнего изменения статуса
     * @param claimId идентификатор заявки
     * @param state новое состояние
     * @param refuseReason причина отказа
     */
    public void setStatus(String claimId, DocumentState state, String refuseReason) {
        AccountClaim claim = accountClaimService.findAccountClaim(claimId);
        claim.setState(state);
        claim.setErrorMessage(refuseReason);
        accountClaimService.save(claim);
    }
}
