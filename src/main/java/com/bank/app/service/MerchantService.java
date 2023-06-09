package com.bank.app.service;

import com.bank.app.service.dto.MerchantDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.bank.app.domain.Merchant}.
 */
public interface MerchantService {
    /**
     * Save a merchant.
     *
     * @param merchantDTO the entity to save.
     * @return the persisted entity.
     */
    MerchantDTO save(MerchantDTO merchantDTO);

    /**
     * Updates a merchant.
     *
     * @param merchantDTO the entity to update.
     * @return the persisted entity.
     */
    MerchantDTO update(MerchantDTO merchantDTO);

    /**
     * Partially updates a merchant.
     *
     * @param merchantDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MerchantDTO> partialUpdate(MerchantDTO merchantDTO);

    /**
     * Get all the merchants.
     *
     * @return the list of entities.
     */
    List<MerchantDTO> findAll();

    /**
     * Get the "id" merchant.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MerchantDTO> findOne(Long id);

    /**
     * Delete the "id" merchant.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
