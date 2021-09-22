package com.example.repository;

import com.example.entity.AccountClaim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface AccountClaimRepository extends JpaRepository<AccountClaim, Long> {

}
