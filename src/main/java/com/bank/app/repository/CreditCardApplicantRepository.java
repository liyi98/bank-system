package com.bank.app.repository;

import com.bank.app.domain.CreditCardApplicant;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CreditCardApplicant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CreditCardApplicantRepository extends JpaRepository<CreditCardApplicant, Long> {}
