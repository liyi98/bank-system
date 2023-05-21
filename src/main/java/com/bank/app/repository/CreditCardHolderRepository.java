package com.bank.app.repository;

import com.bank.app.domain.CreditCardHolder;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CreditCardHolder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CreditCardHolderRepository extends JpaRepository<CreditCardHolder, Long> {}
