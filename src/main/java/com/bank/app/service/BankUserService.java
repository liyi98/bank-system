package com.bank.app.service;

import com.bank.app.service.dto.BankUserDTO;
import com.bank.app.service.dto.BankUserLoginDTO;
import com.bank.app.service.exception.AuthenticationException;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.bank.app.domain.BankUser}.
 */
public interface BankUserService {
    /**
     * Save a bankUser.
     *
     * @param bankUserDTO the entity to save.
     * @return the persisted entity.
     */
    BankUserDTO save(BankUserDTO bankUserDTO);

    /**
     * Updates a bankUser.
     *
     * @param bankUserDTO the entity to update.
     * @return the persisted entity.
     */
    BankUserDTO update(BankUserDTO bankUserDTO);

    /**
     * Partially updates a bankUser.
     *
     * @param bankUserDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BankUserDTO> partialUpdate(BankUserDTO bankUserDTO);

    /**
     * Get all the bankUsers.
     *
     * @return the list of entities.
     */
    List<BankUserDTO> findAll();

    /**
     * Get the "id" bankUser.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BankUserDTO> findOne(Long id);

    /**
     * Delete the "id" bankUser.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    String authenticate(BankUserLoginDTO bankUserLoginDTO) throws AuthenticationException;
}
