package com.bank.app.repository;

import com.bank.app.domain.CreditCardTransaction;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CreditCardTransaction entity.
 *
 * When extending this class, extend CreditCardTransactionRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface CreditCardTransactionRepository
    extends CreditCardTransactionRepositoryWithBagRelationships, JpaRepository<CreditCardTransaction, Long> {
    default Optional<CreditCardTransaction> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<CreditCardTransaction> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<CreditCardTransaction> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
