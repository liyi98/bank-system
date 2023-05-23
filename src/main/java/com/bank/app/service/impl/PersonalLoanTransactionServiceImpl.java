package com.bank.app.service.impl;

import com.bank.app.domain.PersonalLoanTransaction;
import com.bank.app.repository.PersonalLoanTransactionRepository;
import com.bank.app.service.PersonalLoanTransactionService;
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
public class PersonalLoanTransactionServiceImpl implements PersonalLoanTransactionService {

    private final Logger log = LoggerFactory.getLogger(PersonalLoanTransactionServiceImpl.class);

    private final PersonalLoanTransactionRepository personalLoanTransactionRepository;

    private final PersonalLoanTransactionMapper personalLoanTransactionMapper;

    public PersonalLoanTransactionServiceImpl(
        PersonalLoanTransactionRepository personalLoanTransactionRepository,
        PersonalLoanTransactionMapper personalLoanTransactionMapper
    ) {
        this.personalLoanTransactionRepository = personalLoanTransactionRepository;
        this.personalLoanTransactionMapper = personalLoanTransactionMapper;
    }

    @Override
    public PersonalLoanTransactionDTO save(PersonalLoanTransactionDTO personalLoanTransactionDTO) {
        log.debug("Request to save PersonalLoanTransaction : {}", personalLoanTransactionDTO);
        PersonalLoanTransaction personalLoanTransaction = personalLoanTransactionMapper.toEntity(personalLoanTransactionDTO);
        personalLoanTransaction = personalLoanTransactionRepository.save(personalLoanTransaction);
        return personalLoanTransactionMapper.toDto(personalLoanTransaction);
    }

    @Override
    public PersonalLoanTransactionDTO update(PersonalLoanTransactionDTO personalLoanTransactionDTO) {
        log.debug("Request to update PersonalLoanTransaction : {}", personalLoanTransactionDTO);
        PersonalLoanTransaction personalLoanTransaction = personalLoanTransactionMapper.toEntity(personalLoanTransactionDTO);
        personalLoanTransaction = personalLoanTransactionRepository.save(personalLoanTransaction);
        return personalLoanTransactionMapper.toDto(personalLoanTransaction);
    }

    @Override
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

    @Override
    @Transactional(readOnly = true)
    public List<PersonalLoanTransactionDTO> findAll() {
        log.debug("Request to get all PersonalLoanTransactions");
        return personalLoanTransactionRepository
            .findAll()
            .stream()
            .map(personalLoanTransactionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PersonalLoanTransactionDTO> findOne(Long id) {
        log.debug("Request to get PersonalLoanTransaction : {}", id);
        return personalLoanTransactionRepository.findById(id).map(personalLoanTransactionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PersonalLoanTransaction : {}", id);
        personalLoanTransactionRepository.deleteById(id);
    }
}
