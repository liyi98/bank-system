package com.bank.app.repository;

import com.bank.app.domain.CreditCardType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CreditCardType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CreditCardTypeRepository extends JpaRepository<CreditCardType, Long> {}
