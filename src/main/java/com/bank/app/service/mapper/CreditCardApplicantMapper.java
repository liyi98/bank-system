package com.bank.app.service.mapper;

import com.bank.app.domain.CreditCardApplicant;
import com.bank.app.service.dto.CreditCardApplicantDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CreditCardApplicant} and its DTO {@link CreditCardApplicantDTO}.
 */
@Mapper(componentModel = "spring")
public interface CreditCardApplicantMapper extends EntityMapper<CreditCardApplicantDTO, CreditCardApplicant> {}
