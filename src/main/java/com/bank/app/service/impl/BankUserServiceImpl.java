package com.bank.app.service.impl;

import com.bank.app.domain.BankUser;
import com.bank.app.repository.BankUserRepository;
import com.bank.app.security.jwt.TokenProvider;
import com.bank.app.service.BankUserService;
import com.bank.app.service.dto.BankUserDTO;
import com.bank.app.service.dto.BankUserLoginDTO;
import com.bank.app.service.exception.AuthenticationException;
import com.bank.app.service.mapper.BankUserMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BankUser}.
 */
@Service
@Transactional
public class BankUserServiceImpl implements BankUserService {

    private final Logger log = LoggerFactory.getLogger(BankUserServiceImpl.class);

    private final BankUserRepository bankUserRepository;

    private final BankUserMapper bankUserMapper;

    private final TokenProvider tokenProvider;

    public BankUserServiceImpl(BankUserRepository bankUserRepository, BankUserMapper bankUserMapper, TokenProvider tokenProvider) {
        this.bankUserRepository = bankUserRepository;
        this.bankUserMapper = bankUserMapper;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public BankUserDTO save(BankUserDTO bankUserDTO) {
        log.debug("Request to save BankUser : {}", bankUserDTO);
        BankUser bankUser = bankUserMapper.toEntity(bankUserDTO);
        bankUser = bankUserRepository.save(bankUser);
        return bankUserMapper.toDto(bankUser);
    }

    @Override
    public BankUserDTO update(BankUserDTO bankUserDTO) {
        log.debug("Request to update BankUser : {}", bankUserDTO);
        BankUser bankUser = bankUserMapper.toEntity(bankUserDTO);
        bankUser = bankUserRepository.save(bankUser);
        return bankUserMapper.toDto(bankUser);
    }

    @Override
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

    @Override
    @Transactional(readOnly = true)
    public List<BankUserDTO> findAll() {
        log.debug("Request to get all BankUsers");
        return bankUserRepository.findAll().stream().map(bankUserMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BankUserDTO> findOne(Long id) {
        log.debug("Request to get BankUser : {}", id);
        return bankUserRepository.findById(id).map(bankUserMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BankUser : {}", id);
        bankUserRepository.deleteById(id);
    }

    @Override
    public String authenticate(BankUserLoginDTO bankUserLoginDTO) throws AuthenticationException {
        Optional<BankUser> userOpt = bankUserRepository.findOneByLogin(bankUserLoginDTO.getLogin());
        if (userOpt.isEmpty()) {
            throw new AuthenticationException("Invalid Username or Password");
        }

        BankUser user = userOpt.get();

        if (!new BCryptPasswordEncoder().matches(bankUserLoginDTO.getPassword(), user.getPassword())) {
            throw new AuthenticationException("Invalid Username or Password");
        }
        String token = tokenProvider.createToken(
            new UsernamePasswordAuthenticationToken(
                bankUserLoginDTO.getLogin(),
                new BCryptPasswordEncoder().encode(bankUserLoginDTO.getPassword())
            ),
            bankUserLoginDTO.isRememberMe()
        );

        return token;
    }
}
