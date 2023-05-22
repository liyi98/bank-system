package com.bank.app.service.impl;

import com.bank.app.domain.CreditCardApplicant;
import com.bank.app.domain.enumeration.StandardStatus;
import com.bank.app.repository.CreditCardApplicantRepository;
import com.bank.app.service.BankUserService;
import com.bank.app.service.CreditCardApplicantService;
import com.bank.app.service.dto.BankUserDTO;
import com.bank.app.service.dto.CreditCardApplicantDTO;
import com.bank.app.service.exception.CreditCardException;
import com.bank.app.service.mapper.CreditCardApplicantMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CreditCardApplicant}.
 */
@Service
@Transactional
public class CreditCardApplicantServiceImpl implements CreditCardApplicantService {

    private final Logger log = LoggerFactory.getLogger(CreditCardApplicantServiceImpl.class);

    private final CreditCardApplicantRepository creditCardApplicantRepository;

    private final CreditCardApplicantMapper creditCardApplicantMapper;

    private final BankUserService bankService;

    public CreditCardApplicantServiceImpl(
        CreditCardApplicantRepository creditCardApplicantRepository,
        CreditCardApplicantMapper creditCardApplicantMapper,
        BankUserService bankService
    ) {
        this.creditCardApplicantRepository = creditCardApplicantRepository;
        this.creditCardApplicantMapper = creditCardApplicantMapper;
        this.bankService = bankService;
    }

    /**
     * Save a creditCardApplicant.
     *
     * @param creditCardApplicantDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
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
    @Override
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
    @Override
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

    @Override
    @Transactional(rollbackFor = CreditCardException.class)
    public void approve(Long id) throws CreditCardException {
        Optional<CreditCardApplicant> creditCardApplicantOpt = creditCardApplicantRepository.findById(id);
        if (creditCardApplicantOpt.isEmpty()) {
            throw new CreditCardException("Applicant Not Available");
        }
        CreditCardApplicant creditCardApplicant = creditCardApplicantOpt.get();
        if (!StandardStatus.P.equals(creditCardApplicant.getStatus())) {
            throw new CreditCardException("This applicant done proceed");
        }
        creditCardApplicant.setStatus(StandardStatus.A);

        creditCardApplicantRepository.save(creditCardApplicant);

        BankUserDTO bankUserDTO = new BankUserDTO();
        bankUserDTO.setAddress(creditCardApplicant.getAddress());
        bankUserDTO.setDob(creditCardApplicant.getDob());
        bankUserDTO.setIc(creditCardApplicant.getIc());
        bankUserDTO.setEmail(creditCardApplicant.getEmail());
        bankUserDTO.setPhone(creditCardApplicant.getPhone());
        bankUserDTO.setLogin(creditCardApplicant.getEmail());
        bankUserDTO.setPassword(new BCryptPasswordEncoder().encode(creditCardApplicant.getIc()));

        bankService.save(bankUserDTO);
    }

    @Override
    public void reject(Long id) throws CreditCardException {
        Optional<CreditCardApplicant> creditCardApplicantOpt = creditCardApplicantRepository.findById(id);
        if (creditCardApplicantOpt.isEmpty()) {
            throw new CreditCardException("Applicant Not Available");
        }
        CreditCardApplicant creditCardApplicant = creditCardApplicantOpt.get();
        if (!StandardStatus.P.equals(creditCardApplicant.getStatus())) {
            throw new CreditCardException("This applicant done proceed");
        }
        creditCardApplicant.setStatus(StandardStatus.I);

        creditCardApplicantRepository.save(creditCardApplicant);
    }
}
