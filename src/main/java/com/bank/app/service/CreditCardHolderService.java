package com.bank.app.service;

import com.bank.app.service.dto.CreditCardHolderDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.bank.app.domain.CreditCardHolder}.
 */
public interface CreditCardHolderService {
    /**
     * Save a creditCardHolder.
     *
     * @param creditCardHolderDTO the entity to save.
     * @return the persisted entity.
     */
    CreditCardHolderDTO save(CreditCardHolderDTO creditCardHolderDTO);

    /**
     * Updates a creditCardHolder.
     *
     * @param creditCardHolderDTO the entity to update.
     * @return the persisted entity.
     */
    CreditCardHolderDTO update(CreditCardHolderDTO creditCardHolderDTO);

    /**
     * Partially updates a creditCardHolder.
     *
     * @param creditCardHolderDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CreditCardHolderDTO> partialUpdate(CreditCardHolderDTO creditCardHolderDTO);

    /**
     * Get all the creditCardHolders.
     *
     * @return the list of entities.
     */
    List<CreditCardHolderDTO> findAll();

    /**
     * Get the "id" creditCardHolder.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CreditCardHolderDTO> findOne(Long id);

    /**
     * Delete the "id" creditCardHolder.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
