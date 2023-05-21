package com.bank.app.service.mapper;

import com.bank.app.domain.BankUser;
import com.bank.app.domain.CreditCardApplicant;
import com.bank.app.domain.CreditCardHolder;
import com.bank.app.domain.CreditCardType;
import com.bank.app.service.dto.BankUserDTO;
import com.bank.app.service.dto.CreditCardApplicantDTO;
import com.bank.app.service.dto.CreditCardHolderDTO;
import com.bank.app.service.dto.CreditCardTypeDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CreditCardApplicant} and its DTO {@link CreditCardApplicantDTO}.
 */
@Mapper(componentModel = "spring")
public interface CreditCardApplicantMapper extends EntityMapper<CreditCardApplicantDTO, CreditCardApplicant> {
    @Mapping(target = "creditCardHolder", source = "creditCardHolder", qualifiedByName = "creditCardHolderId")
    @Mapping(target = "bankUser", source = "bankUser", qualifiedByName = "bankUserId")
    @Mapping(target = "creditCardTypes", source = "creditCardTypes", qualifiedByName = "creditCardTypeIdSet")
    CreditCardApplicantDTO toDto(CreditCardApplicant s);

    @Mapping(target = "removeCreditCardType", ignore = true)
    CreditCardApplicant toEntity(CreditCardApplicantDTO creditCardApplicantDTO);

    @Named("creditCardHolderId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CreditCardHolderDTO toDtoCreditCardHolderId(CreditCardHolder creditCardHolder);

    @Named("bankUserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BankUserDTO toDtoBankUserId(BankUser bankUser);

    @Named("creditCardTypeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CreditCardTypeDTO toDtoCreditCardTypeId(CreditCardType creditCardType);

    @Named("creditCardTypeIdSet")
    default Set<CreditCardTypeDTO> toDtoCreditCardTypeIdSet(Set<CreditCardType> creditCardType) {
        return creditCardType.stream().map(this::toDtoCreditCardTypeId).collect(Collectors.toSet());
    }
}
