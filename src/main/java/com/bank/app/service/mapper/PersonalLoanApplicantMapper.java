package com.bank.app.service.mapper;

import com.bank.app.domain.BankUser;
import com.bank.app.domain.PersonalLoanApplicant;
import com.bank.app.service.dto.BankUserDTO;
import com.bank.app.service.dto.PersonalLoanApplicantDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PersonalLoanApplicant} and its DTO {@link PersonalLoanApplicantDTO}.
 */
@Mapper(componentModel = "spring")
public interface PersonalLoanApplicantMapper extends EntityMapper<PersonalLoanApplicantDTO, PersonalLoanApplicant> {}
