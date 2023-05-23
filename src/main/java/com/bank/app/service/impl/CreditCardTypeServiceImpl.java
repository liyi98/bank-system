package com.bank.app.service.impl;

import com.bank.app.domain.CreditCardType;
import com.bank.app.repository.CreditCardTypeRepository;
import com.bank.app.service.CreditCardTypeService;
import com.bank.app.service.dto.CreditCardTypeDTO;
import com.bank.app.service.mapper.CreditCardTypeMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CreditCardType}.
 */
@Service
@Transactional
public class CreditCardTypeServiceImpl implements CreditCardTypeService {

    private final Logger log = LoggerFactory.getLogger(CreditCardTypeServiceImpl.class);

    private final CreditCardTypeRepository creditCardTypeRepository;

    private final CreditCardTypeMapper creditCardTypeMapper;

    public CreditCardTypeServiceImpl(CreditCardTypeRepository creditCardTypeRepository, CreditCardTypeMapper creditCardTypeMapper) {
        this.creditCardTypeRepository = creditCardTypeRepository;
        this.creditCardTypeMapper = creditCardTypeMapper;
    }

    @Override
    public CreditCardTypeDTO save(CreditCardTypeDTO creditCardTypeDTO) {
        log.debug("Request to save CreditCardType : {}", creditCardTypeDTO);
        CreditCardType creditCardType = creditCardTypeMapper.toEntity(creditCardTypeDTO);
        creditCardType = creditCardTypeRepository.save(creditCardType);
        return creditCardTypeMapper.toDto(creditCardType);
    }

    @Override
    public CreditCardTypeDTO update(CreditCardTypeDTO creditCardTypeDTO) {
        log.debug("Request to update CreditCardType : {}", creditCardTypeDTO);
        CreditCardType creditCardType = creditCardTypeMapper.toEntity(creditCardTypeDTO);
        creditCardType = creditCardTypeRepository.save(creditCardType);
        return creditCardTypeMapper.toDto(creditCardType);
    }

    @Override
    public Optional<CreditCardTypeDTO> partialUpdate(CreditCardTypeDTO creditCardTypeDTO) {
        log.debug("Request to partially update CreditCardType : {}", creditCardTypeDTO);

        return creditCardTypeRepository
            .findById(creditCardTypeDTO.getId())
            .map(existingCreditCardType -> {
                creditCardTypeMapper.partialUpdate(existingCreditCardType, creditCardTypeDTO);

                return existingCreditCardType;
            })
            .map(creditCardTypeRepository::save)
            .map(creditCardTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CreditCardTypeDTO> findAll() {
        log.debug("Request to get all CreditCardTypes");
        return creditCardTypeRepository
            .findAll()
            .stream()
            .map(creditCardTypeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CreditCardTypeDTO> findOne(Long id) {
        log.debug("Request to get CreditCardType : {}", id);
        return creditCardTypeRepository.findById(id).map(creditCardTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CreditCardType : {}", id);
        creditCardTypeRepository.deleteById(id);
    }
}
