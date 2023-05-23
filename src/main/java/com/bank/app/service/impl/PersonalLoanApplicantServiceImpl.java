package com.bank.app.service.impl;

import com.bank.app.domain.PersonalLoanApplicant;
import com.bank.app.repository.PersonalLoanApplicantRepository;
import com.bank.app.service.PersonalLoanApplicantService;
import com.bank.app.service.dto.PersonalLoanApplicantDTO;
import com.bank.app.service.mapper.PersonalLoanApplicantMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PersonalLoanApplicant}.
 */
@Service
@Transactional
public class PersonalLoanApplicantServiceImpl implements PersonalLoanApplicantService {

    private final Logger log = LoggerFactory.getLogger(PersonalLoanApplicantServiceImpl.class);

    private final PersonalLoanApplicantRepository personalLoanApplicantRepository;

    private final PersonalLoanApplicantMapper personalLoanApplicantMapper;

    public PersonalLoanApplicantServiceImpl(
        PersonalLoanApplicantRepository personalLoanApplicantRepository,
        PersonalLoanApplicantMapper personalLoanApplicantMapper
    ) {
        this.personalLoanApplicantRepository = personalLoanApplicantRepository;
        this.personalLoanApplicantMapper = personalLoanApplicantMapper;
    }

    @Override
    public PersonalLoanApplicantDTO save(PersonalLoanApplicantDTO personalLoanApplicantDTO) {
        log.debug("Request to save PersonalLoanApplicant : {}", personalLoanApplicantDTO);
        PersonalLoanApplicant personalLoanApplicant = personalLoanApplicantMapper.toEntity(personalLoanApplicantDTO);
        personalLoanApplicant = personalLoanApplicantRepository.save(personalLoanApplicant);
        return personalLoanApplicantMapper.toDto(personalLoanApplicant);
    }

    @Override
    public PersonalLoanApplicantDTO update(PersonalLoanApplicantDTO personalLoanApplicantDTO) {
        log.debug("Request to update PersonalLoanApplicant : {}", personalLoanApplicantDTO);
        PersonalLoanApplicant personalLoanApplicant = personalLoanApplicantMapper.toEntity(personalLoanApplicantDTO);
        personalLoanApplicant = personalLoanApplicantRepository.save(personalLoanApplicant);
        return personalLoanApplicantMapper.toDto(personalLoanApplicant);
    }

    @Override
    public Optional<PersonalLoanApplicantDTO> partialUpdate(PersonalLoanApplicantDTO personalLoanApplicantDTO) {
        log.debug("Request to partially update PersonalLoanApplicant : {}", personalLoanApplicantDTO);

        return personalLoanApplicantRepository
            .findById(personalLoanApplicantDTO.getId())
            .map(existingPersonalLoanApplicant -> {
                personalLoanApplicantMapper.partialUpdate(existingPersonalLoanApplicant, personalLoanApplicantDTO);

                return existingPersonalLoanApplicant;
            })
            .map(personalLoanApplicantRepository::save)
            .map(personalLoanApplicantMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonalLoanApplicantDTO> findAll() {
        log.debug("Request to get all PersonalLoanApplicants");
        return personalLoanApplicantRepository
            .findAll()
            .stream()
            .map(personalLoanApplicantMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PersonalLoanApplicantDTO> findOne(Long id) {
        log.debug("Request to get PersonalLoanApplicant : {}", id);
        return personalLoanApplicantRepository.findById(id).map(personalLoanApplicantMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PersonalLoanApplicant : {}", id);
        personalLoanApplicantRepository.deleteById(id);
    }
}
