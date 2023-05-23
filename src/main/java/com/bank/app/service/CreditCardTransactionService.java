package com.bank.app.service;

import com.bank.app.service.dto.CreditCardTransactionDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.bank.app.domain.CreditCardTransaction}.
 */
public interface CreditCardTransactionService {
    /**
     * Save a creditCardTransaction.
     *
     * @param creditCardTransactionDTO the entity to save.
     * @return the persisted entity.
     */
    CreditCardTransactionDTO save(CreditCardTransactionDTO creditCardTransactionDTO);

    /**
     * Updates a creditCardTransaction.
     *
     * @param creditCardTransactionDTO the entity to update.
     * @return the persisted entity.
     */
    CreditCardTransactionDTO update(CreditCardTransactionDTO creditCardTransactionDTO);

    /**
     * Partially updates a creditCardTransaction.
     *
     * @param creditCardTransactionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CreditCardTransactionDTO> partialUpdate(CreditCardTransactionDTO creditCardTransactionDTO);

    /**
     * Get all the creditCardTransactions.
     *
     * @return the list of entities.
     */
    List<CreditCardTransactionDTO> findAll();

    /**
     * Get all the creditCardTransactions with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CreditCardTransactionDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" creditCardTransaction.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CreditCardTransactionDTO> findOne(Long id);

    /**
     * Delete the "id" creditCardTransaction.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
