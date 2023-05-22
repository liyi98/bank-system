package com.bank.app.service;

import com.bank.app.domain.PersonalLoanTransaction;
import com.bank.app.repository.PersonalLoanTransactionRepository;
import com.bank.app.service.dto.PersonalLoanTransactionDTO;
import com.bank.app.service.mapper.PersonalLoanTransactionMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PersonalLoanTransaction}.
 */
@Service
@Transactional
public class PersonalLoanTransactionService {

    private final Logger log = LoggerFactory.getLogger(PersonalLoanTransactionService.class);

    private final PersonalLoanTransactionRepository personalLoanTransactionRepository;

    private final PersonalLoanTransactionMapper personalLoanTransactionMapper;

    public PersonalLoanTransactionService(
        PersonalLoanTransactionRepository personalLoanTransactionRepository,
        PersonalLoanTransactionMapper personalLoanTransactionMapper
    ) {
        this.personalLoanTransactionRepository = personalLoanTransactionRepository;
        this.personalLoanTransactionMapper = personalLoanTransactionMapper;
    }

    /**
     * Save a personalLoanTransaction.
     *
     * @param personalLoanTransactionDTO the entity to save.
     * @return the persisted entity.
     */
    public PersonalLoanTransactionDTO save(PersonalLoanTransactionDTO personalLoanTransactionDTO) {
        log.debug("Request to save PersonalLoanTransaction : {}", personalLoanTransactionDTO);
        PersonalLoanTransaction personalLoanTransaction = personalLoanTransactionMapper.toEntity(personalLoanTransactionDTO);
        personalLoanTransaction = personalLoanTransactionRepository.save(personalLoanTransaction);
        return personalLoanTransactionMapper.toDto(personalLoanTransaction);
    }

    /**
     * Update a personalLoanTransaction.
     *
     * @param personalLoanTransactionDTO the entity to save.
     * @return the persisted entity.
     */
    public PersonalLoanTransactionDTO update(PersonalLoanTransactionDTO personalLoanTransactionDTO) {
        log.debug("Request to update PersonalLoanTransaction : {}", personalLoanTransactionDTO);
        PersonalLoanTransaction personalLoanTransaction = personalLoanTransactionMapper.toEntity(personalLoanTransactionDTO);
        personalLoanTransaction = personalLoanTransactionRepository.save(personalLoanTransaction);
        return personalLoanTransactionMapper.toDto(personalLoanTransaction);
    }

    /**
     * Partially update a personalLoanTransaction.
     *
     * @param personalLoanTransactionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PersonalLoanTransactionDTO> partialUpdate(PersonalLoanTransactionDTO personalLoanTransactionDTO) {
        log.debug("Request to partially update PersonalLoanTransaction : {}", personalLoanTransactionDTO);

        return personalLoanTransactionRepository
            .findById(personalLoanTransactionDTO.getId())
            .map(existingPersonalLoanTransaction -> {
                personalLoanTransactionMapper.partialUpdate(existingPersonalLoanTransaction, personalLoanTransactionDTO);

                return existingPersonalLoanTransaction;
            })
            .map(personalLoanTransactionRepository::save)
            .map(personalLoanTransactionMapper::toDto);
    }

    /**
     * Get all the personalLoanTransactions.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PersonalLoanTransactionDTO> findAll() {
        log.debug("Request to get all PersonalLoanTransactions");
        return personalLoanTransactionRepository
            .findAll()
            .stream()
            .map(personalLoanTransactionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one personalLoanTransaction by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PersonalLoanTransactionDTO> findOne(Long id) {
        log.debug("Request to get PersonalLoanTransaction : {}", id);
        return personalLoanTransactionRepository.findById(id).map(personalLoanTransactionMapper::toDto);
    }

    /**
     * Delete the personalLoanTransaction by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PersonalLoanTransaction : {}", id);
        personalLoanTransactionRepository.deleteById(id);
    }
}
