package com.bank.app.service;

import com.bank.app.service.dto.PersonalLoanTransactionDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.bank.app.domain.PersonalLoanTransaction}.
 */
public interface PersonalLoanTransactionService {
    /**
     * Save a personalLoanTransaction.
     *
     * @param personalLoanTransactionDTO the entity to save.
     * @return the persisted entity.
     */
    PersonalLoanTransactionDTO save(PersonalLoanTransactionDTO personalLoanTransactionDTO);

    /**
     * Updates a personalLoanTransaction.
     *
     * @param personalLoanTransactionDTO the entity to update.
     * @return the persisted entity.
     */
    PersonalLoanTransactionDTO update(PersonalLoanTransactionDTO personalLoanTransactionDTO);

    /**
     * Partially updates a personalLoanTransaction.
     *
     * @param personalLoanTransactionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PersonalLoanTransactionDTO> partialUpdate(PersonalLoanTransactionDTO personalLoanTransactionDTO);

    /**
     * Get all the personalLoanTransactions.
     *
     * @return the list of entities.
     */
    List<PersonalLoanTransactionDTO> findAll();

    /**
     * Get the "id" personalLoanTransaction.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PersonalLoanTransactionDTO> findOne(Long id);

    /**
     * Delete the "id" personalLoanTransaction.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
