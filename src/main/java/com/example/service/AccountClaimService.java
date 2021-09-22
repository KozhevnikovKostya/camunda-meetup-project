package com.example.service;

import com.example.entity.AccountClaim;
import com.example.repository.AccountClaimRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountClaimService {
    private final AccountClaimRepository accountClaimRepository;

    public AccountClaim findAccountClaim(String id){
        return accountClaimRepository.getOne(Long.parseLong(id));
    }

    public Long save(AccountClaim claim) {
        return accountClaimRepository.save(claim).getId();
    }
}
