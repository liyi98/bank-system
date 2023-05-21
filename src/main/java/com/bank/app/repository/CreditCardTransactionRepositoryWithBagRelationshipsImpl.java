package com.bank.app.repository;

import com.bank.app.domain.CreditCardTransaction;
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
public class CreditCardTransactionRepositoryWithBagRelationshipsImpl implements CreditCardTransactionRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<CreditCardTransaction> fetchBagRelationships(Optional<CreditCardTransaction> creditCardTransaction) {
        return creditCardTransaction.map(this::fetchMerchants);
    }

    @Override
    public Page<CreditCardTransaction> fetchBagRelationships(Page<CreditCardTransaction> creditCardTransactions) {
        return new PageImpl<>(
            fetchBagRelationships(creditCardTransactions.getContent()),
            creditCardTransactions.getPageable(),
            creditCardTransactions.getTotalElements()
        );
    }

    @Override
    public List<CreditCardTransaction> fetchBagRelationships(List<CreditCardTransaction> creditCardTransactions) {
        return Optional.of(creditCardTransactions).map(this::fetchMerchants).orElse(Collections.emptyList());
    }

    CreditCardTransaction fetchMerchants(CreditCardTransaction result) {
        return entityManager
            .createQuery(
                "select creditCardTransaction from CreditCardTransaction creditCardTransaction left join fetch creditCardTransaction.merchants where creditCardTransaction is :creditCardTransaction",
                CreditCardTransaction.class
            )
            .setParameter("creditCardTransaction", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<CreditCardTransaction> fetchMerchants(List<CreditCardTransaction> creditCardTransactions) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, creditCardTransactions.size()).forEach(index -> order.put(creditCardTransactions.get(index).getId(), index));
        List<CreditCardTransaction> result = entityManager
            .createQuery(
                "select distinct creditCardTransaction from CreditCardTransaction creditCardTransaction left join fetch creditCardTransaction.merchants where creditCardTransaction in :creditCardTransactions",
                CreditCardTransaction.class
            )
            .setParameter("creditCardTransactions", creditCardTransactions)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
