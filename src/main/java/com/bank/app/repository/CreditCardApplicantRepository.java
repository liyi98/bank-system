package com.bank.app.repository;

import com.bank.app.domain.CreditCardApplicant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CreditCardApplicant entity.
 *
 * When extending this class, extend CreditCardApplicantRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface CreditCardApplicantRepository
    extends CreditCardApplicantRepositoryWithBagRelationships, JpaRepository<CreditCardApplicant, Long> {
    default Optional<CreditCardApplicant> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<CreditCardApplicant> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<CreditCardApplicant> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
