package com.bank.app.repository;

import com.bank.app.domain.PersonalLoan;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface PersonalLoanRepositoryWithBagRelationships {
    Optional<PersonalLoan> fetchBagRelationships(Optional<PersonalLoan> personalLoan);

    List<PersonalLoan> fetchBagRelationships(List<PersonalLoan> personalLoans);

    Page<PersonalLoan> fetchBagRelationships(Page<PersonalLoan> personalLoans);
}
