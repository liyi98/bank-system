package com.bank.app.service.impl;

import com.bank.app.domain.PersonalLoan;
import com.bank.app.repository.PersonalLoanRepository;
import com.bank.app.service.PersonalLoanService;
import com.bank.app.service.dto.PersonalLoanDTO;
import com.bank.app.service.mapper.PersonalLoanMapper;
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
 * Service Implementation for managing {@link PersonalLoan}.
 */
@Service
@Transactional
public class PersonalLoanServiceImpl implements PersonalLoanService {

    private final Logger log = LoggerFactory.getLogger(PersonalLoanServiceImpl.class);

    private final PersonalLoanRepository personalLoanRepository;

    private final PersonalLoanMapper personalLoanMapper;

    public PersonalLoanServiceImpl(PersonalLoanRepository personalLoanRepository, PersonalLoanMapper personalLoanMapper) {
        this.personalLoanRepository = personalLoanRepository;
        this.personalLoanMapper = personalLoanMapper;
    }

    @Override
    public PersonalLoanDTO save(PersonalLoanDTO personalLoanDTO) {
        log.debug("Request to save PersonalLoan : {}", personalLoanDTO);
        PersonalLoan personalLoan = personalLoanMapper.toEntity(personalLoanDTO);
        personalLoan = personalLoanRepository.save(personalLoan);
        return personalLoanMapper.toDto(personalLoan);
    }

    @Override
    public PersonalLoanDTO update(PersonalLoanDTO personalLoanDTO) {
        log.debug("Request to update PersonalLoan : {}", personalLoanDTO);
        PersonalLoan personalLoan = personalLoanMapper.toEntity(personalLoanDTO);
        personalLoan = personalLoanRepository.save(personalLoan);
        return personalLoanMapper.toDto(personalLoan);
    }

    @Override
    public Optional<PersonalLoanDTO> partialUpdate(PersonalLoanDTO personalLoanDTO) {
        log.debug("Request to partially update PersonalLoan : {}", personalLoanDTO);

        return personalLoanRepository
            .findById(personalLoanDTO.getId())
            .map(existingPersonalLoan -> {
                personalLoanMapper.partialUpdate(existingPersonalLoan, personalLoanDTO);

                return existingPersonalLoan;
            })
            .map(personalLoanRepository::save)
            .map(personalLoanMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonalLoanDTO> findAll() {
        log.debug("Request to get all PersonalLoans");
        return personalLoanRepository.findAll().stream().map(personalLoanMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    public Page<PersonalLoanDTO> findAllWithEagerRelationships(Pageable pageable) {
        return personalLoanRepository.findAllWithEagerRelationships(pageable).map(personalLoanMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PersonalLoanDTO> findOne(Long id) {
        log.debug("Request to get PersonalLoan : {}", id);
        return personalLoanRepository.findOneWithEagerRelationships(id).map(personalLoanMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PersonalLoan : {}", id);
        personalLoanRepository.deleteById(id);
    }
}
