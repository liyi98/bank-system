package com.bank.app.service;

import com.bank.app.service.dto.CreditCardTypeDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.bank.app.domain.CreditCardType}.
 */
public interface CreditCardTypeService {
    /**
     * Save a creditCardType.
     *
     * @param creditCardTypeDTO the entity to save.
     * @return the persisted entity.
     */
    CreditCardTypeDTO save(CreditCardTypeDTO creditCardTypeDTO);

    /**
     * Updates a creditCardType.
     *
     * @param creditCardTypeDTO the entity to update.
     * @return the persisted entity.
     */
    CreditCardTypeDTO update(CreditCardTypeDTO creditCardTypeDTO);

    /**
     * Partially updates a creditCardType.
     *
     * @param creditCardTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CreditCardTypeDTO> partialUpdate(CreditCardTypeDTO creditCardTypeDTO);

    /**
     * Get all the creditCardTypes.
     *
     * @return the list of entities.
     */
    List<CreditCardTypeDTO> findAll();

    /**
     * Get the "id" creditCardType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CreditCardTypeDTO> findOne(Long id);

    /**
     * Delete the "id" creditCardType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
