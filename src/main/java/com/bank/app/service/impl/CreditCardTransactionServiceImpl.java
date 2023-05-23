package com.bank.app.service.impl;

import com.bank.app.domain.CreditCardTransaction;
import com.bank.app.repository.CreditCardTransactionRepository;
import com.bank.app.service.CreditCardTransactionService;
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
public class CreditCardTransactionServiceImpl implements CreditCardTransactionService {

    private final Logger log = LoggerFactory.getLogger(CreditCardTransactionServiceImpl.class);

    private final CreditCardTransactionRepository creditCardTransactionRepository;

    private final CreditCardTransactionMapper creditCardTransactionMapper;

    public CreditCardTransactionServiceImpl(
        CreditCardTransactionRepository creditCardTransactionRepository,
        CreditCardTransactionMapper creditCardTransactionMapper
    ) {
        this.creditCardTransactionRepository = creditCardTransactionRepository;
        this.creditCardTransactionMapper = creditCardTransactionMapper;
    }

    @Override
    public CreditCardTransactionDTO save(CreditCardTransactionDTO creditCardTransactionDTO) {
        log.debug("Request to save CreditCardTransaction : {}", creditCardTransactionDTO);
        CreditCardTransaction creditCardTransaction = creditCardTransactionMapper.toEntity(creditCardTransactionDTO);
        creditCardTransaction = creditCardTransactionRepository.save(creditCardTransaction);
        return creditCardTransactionMapper.toDto(creditCardTransaction);
    }

    @Override
    public CreditCardTransactionDTO update(CreditCardTransactionDTO creditCardTransactionDTO) {
        log.debug("Request to update CreditCardTransaction : {}", creditCardTransactionDTO);
        CreditCardTransaction creditCardTransaction = creditCardTransactionMapper.toEntity(creditCardTransactionDTO);
        creditCardTransaction = creditCardTransactionRepository.save(creditCardTransaction);
        return creditCardTransactionMapper.toDto(creditCardTransaction);
    }

    @Override
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

    @Override
    @Transactional(readOnly = true)
    public List<CreditCardTransactionDTO> findAll() {
        log.debug("Request to get all CreditCardTransactions");
        return creditCardTransactionRepository
            .findAll()
            .stream()
            .map(creditCardTransactionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    public Page<CreditCardTransactionDTO> findAllWithEagerRelationships(Pageable pageable) {
        return creditCardTransactionRepository.findAllWithEagerRelationships(pageable).map(creditCardTransactionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CreditCardTransactionDTO> findOne(Long id) {
        log.debug("Request to get CreditCardTransaction : {}", id);
        return creditCardTransactionRepository.findOneWithEagerRelationships(id).map(creditCardTransactionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CreditCardTransaction : {}", id);
        creditCardTransactionRepository.deleteById(id);
    }
}
