package com.bank.app.service;

import com.bank.app.domain.BankUser;
import com.bank.app.repository.BankUserRepository;
import com.bank.app.service.dto.BankUserDTO;
import com.bank.app.service.mapper.BankUserMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BankUser}.
 */
@Service
@Transactional
public class BankUserService {

    private final Logger log = LoggerFactory.getLogger(BankUserService.class);

    private final BankUserRepository bankUserRepository;

    private final BankUserMapper bankUserMapper;

    public BankUserService(BankUserRepository bankUserRepository, BankUserMapper bankUserMapper) {
        this.bankUserRepository = bankUserRepository;
        this.bankUserMapper = bankUserMapper;
    }

    /**
     * Save a bankUser.
     *
     * @param bankUserDTO the entity to save.
     * @return the persisted entity.
     */
    public BankUserDTO save(BankUserDTO bankUserDTO) {
        log.debug("Request to save BankUser : {}", bankUserDTO);
        BankUser bankUser = bankUserMapper.toEntity(bankUserDTO);
        bankUser = bankUserRepository.save(bankUser);
        return bankUserMapper.toDto(bankUser);
    }

    /**
     * Update a bankUser.
     *
     * @param bankUserDTO the entity to save.
     * @return the persisted entity.
     */
    public BankUserDTO update(BankUserDTO bankUserDTO) {
        log.debug("Request to update BankUser : {}", bankUserDTO);
        BankUser bankUser = bankUserMapper.toEntity(bankUserDTO);
        bankUser = bankUserRepository.save(bankUser);
        return bankUserMapper.toDto(bankUser);
    }

    /**
     * Partially update a bankUser.
     *
     * @param bankUserDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BankUserDTO> partialUpdate(BankUserDTO bankUserDTO) {
        log.debug("Request to partially update BankUser : {}", bankUserDTO);

        return bankUserRepository
            .findById(bankUserDTO.getId())
            .map(existingBankUser -> {
                bankUserMapper.partialUpdate(existingBankUser, bankUserDTO);

                return existingBankUser;
            })
            .map(bankUserRepository::save)
            .map(bankUserMapper::toDto);
    }

    /**
     * Get all the bankUsers.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<BankUserDTO> findAll() {
        log.debug("Request to get all BankUsers");
        return bankUserRepository.findAll().stream().map(bankUserMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one bankUser by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BankUserDTO> findOne(Long id) {
        log.debug("Request to get BankUser : {}", id);
        return bankUserRepository.findById(id).map(bankUserMapper::toDto);
    }

    /**
     * Delete the bankUser by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BankUser : {}", id);
        bankUserRepository.deleteById(id);
    }
}
