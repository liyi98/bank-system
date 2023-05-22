package com.bank.app.repository;

import com.bank.app.domain.PersonalLoan;
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
public class PersonalLoanRepositoryWithBagRelationshipsImpl implements PersonalLoanRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<PersonalLoan> fetchBagRelationships(Optional<PersonalLoan> personalLoan) {
        return personalLoan.map(this::fetchPersonalLoanApplicants);
    }

    @Override
    public Page<PersonalLoan> fetchBagRelationships(Page<PersonalLoan> personalLoans) {
        return new PageImpl<>(
            fetchBagRelationships(personalLoans.getContent()),
            personalLoans.getPageable(),
            personalLoans.getTotalElements()
        );
    }

    @Override
    public List<PersonalLoan> fetchBagRelationships(List<PersonalLoan> personalLoans) {
        return Optional.of(personalLoans).map(this::fetchPersonalLoanApplicants).orElse(Collections.emptyList());
    }

    PersonalLoan fetchPersonalLoanApplicants(PersonalLoan result) {
        return entityManager
            .createQuery(
                "select personalLoan from PersonalLoan personalLoan left join fetch personalLoan.personalLoanApplicants where personalLoan is :personalLoan",
                PersonalLoan.class
            )
            .setParameter("personalLoan", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<PersonalLoan> fetchPersonalLoanApplicants(List<PersonalLoan> personalLoans) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, personalLoans.size()).forEach(index -> order.put(personalLoans.get(index).getId(), index));
        List<PersonalLoan> result = entityManager
            .createQuery(
                "select distinct personalLoan from PersonalLoan personalLoan left join fetch personalLoan.personalLoanApplicants where personalLoan in :personalLoans",
                PersonalLoan.class
            )
            .setParameter("personalLoans", personalLoans)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
