package com.bank.app.repository;

import com.bank.app.domain.PersonalLoanTransaction;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PersonalLoanTransaction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonalLoanTransactionRepository extends JpaRepository<PersonalLoanTransaction, Long> {}
