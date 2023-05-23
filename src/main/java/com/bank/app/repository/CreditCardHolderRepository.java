package com.bank.app.repository;

import com.bank.app.domain.CreditCardHolder;
import java.math.BigDecimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CreditCardHolder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CreditCardHolderRepository extends JpaRepository<CreditCardHolder, Long> {
    @Modifying
    @Query("update CreditCardHolder c set c.bankUserId = :bankUserId, c.limitAmount = :limitAmount where id = 1 ")
    int updateBankUserIdAndLimit(@Param("bankUserId") Long bankUserId, @Param("limitAmount") BigDecimal limitAmount);
}
