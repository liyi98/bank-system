package com.bank.app.repository;

import com.bank.app.domain.CreditCardApplicant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface CreditCardApplicantRepositoryWithBagRelationships {
    Optional<CreditCardApplicant> fetchBagRelationships(Optional<CreditCardApplicant> creditCardApplicant);

    List<CreditCardApplicant> fetchBagRelationships(List<CreditCardApplicant> creditCardApplicants);

    Page<CreditCardApplicant> fetchBagRelationships(Page<CreditCardApplicant> creditCardApplicants);
}
