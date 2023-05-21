package com.bank.app.repository;

import com.bank.app.domain.CreditCardApplicant;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class CreditCardApplicantRepositoryWithBagRelationshipsImpl implements CreditCardApplicantRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<CreditCardApplicant> fetchBagRelationships(Optional<CreditCardApplicant> creditCardApplicant) {
        return creditCardApplicant.map(this::fetchCreditCardTypes);
    }

    @Override
    public Page<CreditCardApplicant> fetchBagRelationships(Page<CreditCardApplicant> creditCardApplicants) {
        return new PageImpl<>(
            fetchBagRelationships(creditCardApplicants.getContent()),
            creditCardApplicants.getPageable(),
            creditCardApplicants.getTotalElements()
        );
    }

    @Override
    public List<CreditCardApplicant> fetchBagRelationships(List<CreditCardApplicant> creditCardApplicants) {
        return Optional.of(creditCardApplicants).map(this::fetchCreditCardTypes).orElse(Collections.emptyList());
    }

    CreditCardApplicant fetchCreditCardTypes(CreditCardApplicant result) {
        return entityManager
            .createQuery(
                "select creditCardApplicant from CreditCardApplicant creditCardApplicant left join fetch creditCardApplicant.creditCardTypes where creditCardApplicant is :creditCardApplicant",
                CreditCardApplicant.class
            )
            .setParameter("creditCardApplicant", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<CreditCardApplicant> fetchCreditCardTypes(List<CreditCardApplicant> creditCardApplicants) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, creditCardApplicants.size()).forEach(index -> order.put(creditCardApplicants.get(index).getId(), index));
        List<CreditCardApplicant> result = entityManager
            .createQuery(
                "select distinct creditCardApplicant from CreditCardApplicant creditCardApplicant left join fetch creditCardApplicant.creditCardTypes where creditCardApplicant in :creditCardApplicants",
                CreditCardApplicant.class
            )
            .setParameter("creditCardApplicants", creditCardApplicants)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
