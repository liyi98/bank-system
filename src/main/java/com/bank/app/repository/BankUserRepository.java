package com.bank.app.repository;

import com.bank.app.domain.BankUser;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BankUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BankUserRepository extends JpaRepository<BankUser, Long> {}
