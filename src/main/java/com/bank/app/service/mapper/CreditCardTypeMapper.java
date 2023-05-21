package com.bank.app.service.mapper;

import com.bank.app.domain.CreditCardType;
import com.bank.app.service.dto.CreditCardTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CreditCardType} and its DTO {@link CreditCardTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface CreditCardTypeMapper extends EntityMapper<CreditCardTypeDTO, CreditCardType> {}
