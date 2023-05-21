package com.bank.app.service;

import com.bank.app.domain.CreditCardType;
import com.bank.app.repository.CreditCardTypeRepository;
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
public class CreditCardTypeService {

    private final Logger log = LoggerFactory.getLogger(CreditCardTypeService.class);

    private final CreditCardTypeRepository creditCardTypeRepository;

    private final CreditCardTypeMapper creditCardTypeMapper;

    public CreditCardTypeService(CreditCardTypeRepository creditCardTypeRepository, CreditCardTypeMapper creditCardTypeMapper) {
        this.creditCardTypeRepository = creditCardTypeRepository;
        this.creditCardTypeMapper = creditCardTypeMapper;
    }

    /**
     * Save a creditCardType.
     *
     * @param creditCardTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public CreditCardTypeDTO save(CreditCardTypeDTO creditCardTypeDTO) {
        log.debug("Request to save CreditCardType : {}", creditCardTypeDTO);
        CreditCardType creditCardType = creditCardTypeMapper.toEntity(creditCardTypeDTO);
        creditCardType = creditCardTypeRepository.save(creditCardType);
        return creditCardTypeMapper.toDto(creditCardType);
    }

    /**
     * Update a creditCardType.
     *
     * @param creditCardTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public CreditCardTypeDTO update(CreditCardTypeDTO creditCardTypeDTO) {
        log.debug("Request to update CreditCardType : {}", creditCardTypeDTO);
        CreditCardType creditCardType = creditCardTypeMapper.toEntity(creditCardTypeDTO);
        creditCardType = creditCardTypeRepository.save(creditCardType);
        return creditCardTypeMapper.toDto(creditCardType);
    }

    /**
     * Partially update a creditCardType.
     *
     * @param creditCardTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
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

    /**
     * Get all the creditCardTypes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CreditCardTypeDTO> findAll() {
        log.debug("Request to get all CreditCardTypes");
        return creditCardTypeRepository
            .findAll()
            .stream()
            .map(creditCardTypeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one creditCardType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CreditCardTypeDTO> findOne(Long id) {
        log.debug("Request to get CreditCardType : {}", id);
        return creditCardTypeRepository.findById(id).map(creditCardTypeMapper::toDto);
    }

    /**
     * Delete the creditCardType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CreditCardType : {}", id);
        creditCardTypeRepository.deleteById(id);
    }
}
