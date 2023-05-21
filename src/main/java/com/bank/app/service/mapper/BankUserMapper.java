package com.bank.app.service.mapper;

import com.bank.app.domain.BankUser;
import com.bank.app.service.dto.BankUserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BankUser} and its DTO {@link BankUserDTO}.
 */
@Mapper(componentModel = "spring")
public interface BankUserMapper extends EntityMapper<BankUserDTO, BankUser> {}
