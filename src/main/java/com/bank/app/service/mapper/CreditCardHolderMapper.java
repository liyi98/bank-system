package com.bank.app.service.mapper;

import com.bank.app.domain.CreditCardHolder;
import com.bank.app.service.dto.CreditCardHolderDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CreditCardHolder} and its DTO {@link CreditCardHolderDTO}.
 */
@Mapper(componentModel = "spring")
public interface CreditCardHolderMapper extends EntityMapper<CreditCardHolderDTO, CreditCardHolder> {}
