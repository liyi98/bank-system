package com.bank.app.service.impl;

import com.bank.app.domain.CreditCardHolder;
import com.bank.app.repository.CreditCardHolderRepository;
import com.bank.app.service.CreditCardHolderService;
import com.bank.app.service.dto.CreditCardHolderDTO;
import com.bank.app.service.mapper.CreditCardHolderMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CreditCardHolder}.
 */
@Service
@Transactional
public class CreditCardHolderServiceImpl implements CreditCardHolderService {

    private final Logger log = LoggerFactory.getLogger(CreditCardHolderServiceImpl.class);

    private final CreditCardHolderRepository creditCardHolderRepository;

    private final CreditCardHolderMapper creditCardHolderMapper;

    public CreditCardHolderServiceImpl(
        CreditCardHolderRepository creditCardHolderRepository,
        CreditCardHolderMapper creditCardHolderMapper
    ) {
        this.creditCardHolderRepository = creditCardHolderRepository;
        this.creditCardHolderMapper = creditCardHolderMapper;
    }

    @Override
    public CreditCardHolderDTO save(CreditCardHolderDTO creditCardHolderDTO) {
        log.debug("Request to save CreditCardHolder : {}", creditCardHolderDTO);
        CreditCardHolder creditCardHolder = creditCardHolderMapper.toEntity(creditCardHolderDTO);
        creditCardHolder = creditCardHolderRepository.save(creditCardHolder);
        return creditCardHolderMapper.toDto(creditCardHolder);
    }

    @Override
    public CreditCardHolderDTO update(CreditCardHolderDTO creditCardHolderDTO) {
        log.debug("Request to update CreditCardHolder : {}", creditCardHolderDTO);
        CreditCardHolder creditCardHolder = creditCardHolderMapper.toEntity(creditCardHolderDTO);
        creditCardHolder = creditCardHolderRepository.save(creditCardHolder);
        return creditCardHolderMapper.toDto(creditCardHolder);
    }

    @Override
    public Optional<CreditCardHolderDTO> partialUpdate(CreditCardHolderDTO creditCardHolderDTO) {
        log.debug("Request to partially update CreditCardHolder : {}", creditCardHolderDTO);

        return creditCardHolderRepository
            .findById(creditCardHolderDTO.getId())
            .map(existingCreditCardHolder -> {
                creditCardHolderMapper.partialUpdate(existingCreditCardHolder, creditCardHolderDTO);

                return existingCreditCardHolder;
            })
            .map(creditCardHolderRepository::save)
            .map(creditCardHolderMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CreditCardHolderDTO> findAll() {
        log.debug("Request to get all CreditCardHolders");
        return creditCardHolderRepository
            .findAll()
            .stream()
            .map(creditCardHolderMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CreditCardHolderDTO> findOne(Long id) {
        log.debug("Request to get CreditCardHolder : {}", id);
        return creditCardHolderRepository.findById(id).map(creditCardHolderMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CreditCardHolder : {}", id);
        creditCardHolderRepository.deleteById(id);
    }
}
