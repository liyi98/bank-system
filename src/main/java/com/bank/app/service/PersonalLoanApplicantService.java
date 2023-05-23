package com.bank.app.service;

import com.bank.app.service.dto.PersonalLoanApplicantDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.bank.app.domain.PersonalLoanApplicant}.
 */
public interface PersonalLoanApplicantService {
    /**
     * Save a personalLoanApplicant.
     *
     * @param personalLoanApplicantDTO the entity to save.
     * @return the persisted entity.
     */
    PersonalLoanApplicantDTO save(PersonalLoanApplicantDTO personalLoanApplicantDTO);

    /**
     * Updates a personalLoanApplicant.
     *
     * @param personalLoanApplicantDTO the entity to update.
     * @return the persisted entity.
     */
    PersonalLoanApplicantDTO update(PersonalLoanApplicantDTO personalLoanApplicantDTO);

    /**
     * Partially updates a personalLoanApplicant.
     *
     * @param personalLoanApplicantDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PersonalLoanApplicantDTO> partialUpdate(PersonalLoanApplicantDTO personalLoanApplicantDTO);

    /**
     * Get all the personalLoanApplicants.
     *
     * @return the list of entities.
     */
    List<PersonalLoanApplicantDTO> findAll();

    /**
     * Get the "id" personalLoanApplicant.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PersonalLoanApplicantDTO> findOne(Long id);

    /**
     * Delete the "id" personalLoanApplicant.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
