package com.bank.app.service;

import com.bank.app.domain.PersonalLoan;
import com.bank.app.repository.PersonalLoanRepository;
import com.bank.app.service.dto.PersonalLoanDTO;
import com.bank.app.service.mapper.PersonalLoanMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PersonalLoan}.
 */
@Service
@Transactional
public class PersonalLoanService {

    private final Logger log = LoggerFactory.getLogger(PersonalLoanService.class);

    private final PersonalLoanRepository personalLoanRepository;

    private final PersonalLoanMapper personalLoanMapper;

    public PersonalLoanService(PersonalLoanRepository personalLoanRepository, PersonalLoanMapper personalLoanMapper) {
        this.personalLoanRepository = personalLoanRepository;
        this.personalLoanMapper = personalLoanMapper;
    }

    /**
     * Save a personalLoan.
     *
     * @param personalLoanDTO the entity to save.
     * @return the persisted entity.
     */
    public PersonalLoanDTO save(PersonalLoanDTO personalLoanDTO) {
        log.debug("Request to save PersonalLoan : {}", personalLoanDTO);
        PersonalLoan personalLoan = personalLoanMapper.toEntity(personalLoanDTO);
        personalLoan = personalLoanRepository.save(personalLoan);
        return personalLoanMapper.toDto(personalLoan);
    }

    /**
     * Update a personalLoan.
     *
     * @param personalLoanDTO the entity to save.
     * @return the persisted entity.
     */
    public PersonalLoanDTO update(PersonalLoanDTO personalLoanDTO) {
        log.debug("Request to update PersonalLoan : {}", personalLoanDTO);
        PersonalLoan personalLoan = personalLoanMapper.toEntity(personalLoanDTO);
        personalLoan = personalLoanRepository.save(personalLoan);
        return personalLoanMapper.toDto(personalLoan);
    }

    /**
     * Partially update a personalLoan.
     *
     * @param personalLoanDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PersonalLoanDTO> partialUpdate(PersonalLoanDTO personalLoanDTO) {
        log.debug("Request to partially update PersonalLoan : {}", personalLoanDTO);

        return personalLoanRepository
            .findById(personalLoanDTO.getId())
            .map(existingPersonalLoan -> {
                personalLoanMapper.partialUpdate(existingPersonalLoan, personalLoanDTO);

                return existingPersonalLoan;
            })
            .map(personalLoanRepository::save)
            .map(personalLoanMapper::toDto);
    }

    /**
     * Get all the personalLoans.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PersonalLoanDTO> findAll() {
        log.debug("Request to get all PersonalLoans");
        return personalLoanRepository.findAll().stream().map(personalLoanMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the personalLoans with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<PersonalLoanDTO> findAllWithEagerRelationships(Pageable pageable) {
        return personalLoanRepository.findAllWithEagerRelationships(pageable).map(personalLoanMapper::toDto);
    }

    /**
     * Get one personalLoan by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PersonalLoanDTO> findOne(Long id) {
        log.debug("Request to get PersonalLoan : {}", id);
        return personalLoanRepository.findOneWithEagerRelationships(id).map(personalLoanMapper::toDto);
    }

    /**
     * Delete the personalLoan by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PersonalLoan : {}", id);
        personalLoanRepository.deleteById(id);
    }
}
