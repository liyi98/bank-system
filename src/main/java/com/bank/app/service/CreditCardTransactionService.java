package com.bank.app.service;

import com.bank.app.domain.CreditCardTransaction;
import com.bank.app.repository.CreditCardTransactionRepository;
import com.bank.app.service.dto.CreditCardTransactionDTO;
import com.bank.app.service.mapper.CreditCardTransactionMapper;
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
 * Service Implementation for managing {@link CreditCardTransaction}.
 */
@Service
@Transactional
public class CreditCardTransactionService {

    private final Logger log = LoggerFactory.getLogger(CreditCardTransactionService.class);

    private final CreditCardTransactionRepository creditCardTransactionRepository;

    private final CreditCardTransactionMapper creditCardTransactionMapper;

    public CreditCardTransactionService(
        CreditCardTransactionRepository creditCardTransactionRepository,
        CreditCardTransactionMapper creditCardTransactionMapper
    ) {
        this.creditCardTransactionRepository = creditCardTransactionRepository;
        this.creditCardTransactionMapper = creditCardTransactionMapper;
    }

    /**
     * Save a creditCardTransaction.
     *
     * @param creditCardTransactionDTO the entity to save.
     * @return the persisted entity.
     */
    public CreditCardTransactionDTO save(CreditCardTransactionDTO creditCardTransactionDTO) {
        log.debug("Request to save CreditCardTransaction : {}", creditCardTransactionDTO);
        CreditCardTransaction creditCardTransaction = creditCardTransactionMapper.toEntity(creditCardTransactionDTO);
        creditCardTransaction = creditCardTransactionRepository.save(creditCardTransaction);
        return creditCardTransactionMapper.toDto(creditCardTransaction);
    }

    /**
     * Update a creditCardTransaction.
     *
     * @param creditCardTransactionDTO the entity to save.
     * @return the persisted entity.
     */
    public CreditCardTransactionDTO update(CreditCardTransactionDTO creditCardTransactionDTO) {
        log.debug("Request to update CreditCardTransaction : {}", creditCardTransactionDTO);
        CreditCardTransaction creditCardTransaction = creditCardTransactionMapper.toEntity(creditCardTransactionDTO);
        creditCardTransaction = creditCardTransactionRepository.save(creditCardTransaction);
        return creditCardTransactionMapper.toDto(creditCardTransaction);
    }

    /**
     * Partially update a creditCardTransaction.
     *
     * @param creditCardTransactionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CreditCardTransactionDTO> partialUpdate(CreditCardTransactionDTO creditCardTransactionDTO) {
        log.debug("Request to partially update CreditCardTransaction : {}", creditCardTransactionDTO);

        return creditCardTransactionRepository
            .findById(creditCardTransactionDTO.getId())
            .map(existingCreditCardTransaction -> {
                creditCardTransactionMapper.partialUpdate(existingCreditCardTransaction, creditCardTransactionDTO);

                return existingCreditCardTransaction;
            })
            .map(creditCardTransactionRepository::save)
            .map(creditCardTransactionMapper::toDto);
    }

    /**
     * Get all the creditCardTransactions.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CreditCardTransactionDTO> findAll() {
        log.debug("Request to get all CreditCardTransactions");
        return creditCardTransactionRepository
            .findAll()
            .stream()
            .map(creditCardTransactionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the creditCardTransactions with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<CreditCardTransactionDTO> findAllWithEagerRelationships(Pageable pageable) {
        return creditCardTransactionRepository.findAllWithEagerRelationships(pageable).map(creditCardTransactionMapper::toDto);
    }

    /**
     * Get one creditCardTransaction by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CreditCardTransactionDTO> findOne(Long id) {
        log.debug("Request to get CreditCardTransaction : {}", id);
        return creditCardTransactionRepository.findOneWithEagerRelationships(id).map(creditCardTransactionMapper::toDto);
    }

    /**
     * Delete the creditCardTransaction by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CreditCardTransaction : {}", id);
        creditCardTransactionRepository.deleteById(id);
    }
}
