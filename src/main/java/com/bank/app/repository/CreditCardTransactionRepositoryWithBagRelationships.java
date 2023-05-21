package com.bank.app.repository;

import com.bank.app.domain.CreditCardTransaction;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface CreditCardTransactionRepositoryWithBagRelationships {
    Optional<CreditCardTransaction> fetchBagRelationships(Optional<CreditCardTransaction> creditCardTransaction);

    List<CreditCardTransaction> fetchBagRelationships(List<CreditCardTransaction> creditCardTransactions);

    Page<CreditCardTransaction> fetchBagRelationships(Page<CreditCardTransaction> creditCardTransactions);
}
