package com.bank.app.service;

import com.bank.app.service.dto.PersonalLoanDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.bank.app.domain.PersonalLoan}.
 */
public interface PersonalLoanService {
    /**
     * Save a personalLoan.
     *
     * @param personalLoanDTO the entity to save.
     * @return the persisted entity.
     */
    PersonalLoanDTO save(PersonalLoanDTO personalLoanDTO);

    /**
     * Updates a personalLoan.
     *
     * @param personalLoanDTO the entity to update.
     * @return the persisted entity.
     */
    PersonalLoanDTO update(PersonalLoanDTO personalLoanDTO);

    /**
     * Partially updates a personalLoan.
     *
     * @param personalLoanDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PersonalLoanDTO> partialUpdate(PersonalLoanDTO personalLoanDTO);

    /**
     * Get all the personalLoans.
     *
     * @return the list of entities.
     */
    List<PersonalLoanDTO> findAll();

    /**
     * Get all the personalLoans with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PersonalLoanDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" personalLoan.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PersonalLoanDTO> findOne(Long id);

    /**
     * Delete the "id" personalLoan.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
