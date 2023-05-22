package com.bank.app.service;

import com.bank.app.domain.CreditCardApplicant;
import com.bank.app.domain.enumeration.StandardStatus;
import com.bank.app.repository.CreditCardApplicantRepository;
import com.bank.app.service.dto.CreditCardApplicantDTO;
import com.bank.app.service.mapper.CreditCardApplicantMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CreditCardApplicant}.
 */
@Service
@Transactional
public class CreditCardApplicantService {

    private final Logger log = LoggerFactory.getLogger(CreditCardApplicantService.class);

    private final CreditCardApplicantRepository creditCardApplicantRepository;

    private final CreditCardApplicantMapper creditCardApplicantMapper;

    public CreditCardApplicantService(
        CreditCardApplicantRepository creditCardApplicantRepository,
        CreditCardApplicantMapper creditCardApplicantMapper
    ) {
        this.creditCardApplicantRepository = creditCardApplicantRepository;
        this.creditCardApplicantMapper = creditCardApplicantMapper;
    }

    /**
     * Save a creditCardApplicant.
     *
     * @param creditCardApplicantDTO the entity to save.
     * @return the persisted entity.
     */
    public CreditCardApplicantDTO save(CreditCardApplicantDTO creditCardApplicantDTO) {
        log.debug("Request to save CreditCardApplicant : {}", creditCardApplicantDTO);
        creditCardApplicantDTO.setStatus(StandardStatus.P);
        CreditCardApplicant creditCardApplicant = creditCardApplicantMapper.toEntity(creditCardApplicantDTO);
        creditCardApplicant = creditCardApplicantRepository.save(creditCardApplicant);
        return creditCardApplicantMapper.toDto(creditCardApplicant);
    }

    /**
     * Update a creditCardApplicant.
     *
     * @param creditCardApplicantDTO the entity to save.
     * @return the persisted entity.
     */
    public CreditCardApplicantDTO update(CreditCardApplicantDTO creditCardApplicantDTO) {
        log.debug("Request to update CreditCardApplicant : {}", creditCardApplicantDTO);
        CreditCardApplicant creditCardApplicant = creditCardApplicantMapper.toEntity(creditCardApplicantDTO);
        creditCardApplicant = creditCardApplicantRepository.save(creditCardApplicant);
        return creditCardApplicantMapper.toDto(creditCardApplicant);
    }

    /**
     * Partially update a creditCardApplicant.
     *
     * @param creditCardApplicantDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CreditCardApplicantDTO> partialUpdate(CreditCardApplicantDTO creditCardApplicantDTO) {
        log.debug("Request to partially update CreditCardApplicant : {}", creditCardApplicantDTO);

        return creditCardApplicantRepository
            .findById(creditCardApplicantDTO.getId())
            .map(existingCreditCardApplicant -> {
                creditCardApplicantMapper.partialUpdate(existingCreditCardApplicant, creditCardApplicantDTO);

                return existingCreditCardApplicant;
            })
            .map(creditCardApplicantRepository::save)
            .map(creditCardApplicantMapper::toDto);
    }

    /**
     * Get all the creditCardApplicants.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CreditCardApplicantDTO> findAll() {
        log.debug("Request to get all CreditCardApplicants");
        return creditCardApplicantRepository
            .findAll()
            .stream()
            .map(creditCardApplicantMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one creditCardApplicant by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CreditCardApplicantDTO> findOne(Long id) {
        log.debug("Request to get CreditCardApplicant : {}", id);
        return creditCardApplicantRepository.findById(id).map(creditCardApplicantMapper::toDto);
    }

    /**
     * Delete the creditCardApplicant by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CreditCardApplicant : {}", id);
        creditCardApplicantRepository.deleteById(id);
    }
}
