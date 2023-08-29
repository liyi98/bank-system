package com.bank.app.service.impl;

import com.bank.app.domain.Admin;
import com.bank.app.repository.AdminRepository;
import com.bank.app.security.jwt.TokenProvider;
import com.bank.app.service.AdminService;
import com.bank.app.service.dto.AdminDTO;
import com.bank.app.service.exception.AuthenticationException;
import com.bank.app.service.mapper.AdminMapper;
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
 * Service Implementation for managing {@link Admin}.
 */
@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    private final Logger log = LoggerFactory.getLogger(AdminServiceImpl.class);

    private final AdminRepository adminRepository;

    private final AdminMapper adminMapper;

    private final TokenProvider tokenProvider;

    public AdminServiceImpl(AdminRepository adminRepository, AdminMapper adminMapper, TokenProvider tokenProvider) {
        this.adminRepository = adminRepository;
        this.adminMapper = adminMapper;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public AdminDTO save(AdminDTO adminDTO) {
        log.debug("Request to save Admin : {}", adminDTO);
        adminDTO.setPassword(new BCryptPasswordEncoder().encode(adminDTO.getPassword()));
        Admin admin = adminMapper.toEntity(adminDTO);
        admin = adminRepository.save(admin);
        return adminMapper.toDto(admin);
    }

    @Override
    public AdminDTO update(AdminDTO adminDTO) {
        log.debug("Request to update Admin : {}", adminDTO);
        Admin admin = adminMapper.toEntity(adminDTO);
        admin = adminRepository.save(admin);
        return adminMapper.toDto(admin);
    }

    @Override
    public Optional<AdminDTO> partialUpdate(AdminDTO adminDTO) {
        log.debug("Request to partially update Admin : {}", adminDTO);

        return adminRepository
            .findById(adminDTO.getId())
            .map(existingAdmin -> {
                adminMapper.partialUpdate(existingAdmin, adminDTO);

                return existingAdmin;
            })
            .map(adminRepository::save)
            .map(adminMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdminDTO> findAll() {
        log.debug("Request to get all Admins");
        return adminRepository.findAll().stream().map(adminMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AdminDTO> findOne(Long id) {
        log.debug("Request to get Admin : {}", id);
        return adminRepository.findById(id).map(adminMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Admin : {}", id);
        adminRepository.deleteById(id);
    }

    @Override
    public String authenticate(AdminDTO adminDTO) throws AuthenticationException {
        Optional<Admin> userOpt = adminRepository.findOneByLogin(adminDTO.getLogin());
        if (userOpt.isEmpty()) {
            throw new AuthenticationException("Invalid Username or Password");
        }

        Admin user = userOpt.get();

        if (!new BCryptPasswordEncoder().matches(adminDTO.getPassword(), user.getPassword())) {
            throw new AuthenticationException("Invalid Username or Password");
        }

        String token = tokenProvider.createToken(
            new UsernamePasswordAuthenticationToken(adminDTO.getLogin(), new BCryptPasswordEncoder().encode(adminDTO.getPassword())),
            true,
            false
        );

        return token;
    }

    @Override
    public AdminDTO findOneByLogin(String login) {
        Optional<Admin> admin = adminRepository.findOneByLogin(login);

        return adminMapper.toDto(admin.orElse(null));
    }
}
