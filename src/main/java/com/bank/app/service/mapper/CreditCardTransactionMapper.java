package com.bank.app.service.mapper;

import com.bank.app.domain.CreditCardHolder;
import com.bank.app.domain.CreditCardTransaction;
import com.bank.app.domain.Merchant;
import com.bank.app.service.dto.CreditCardHolderDTO;
import com.bank.app.service.dto.CreditCardTransactionDTO;
import com.bank.app.service.dto.MerchantDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CreditCardTransaction} and its DTO {@link CreditCardTransactionDTO}.
 */
@Mapper(componentModel = "spring")
public interface CreditCardTransactionMapper extends EntityMapper<CreditCardTransactionDTO, CreditCardTransaction> {
    @Mapping(target = "creditCardHolder", source = "creditCardHolder", qualifiedByName = "creditCardHolderId")
    @Mapping(target = "merchants", source = "merchants", qualifiedByName = "merchantIdSet")
    CreditCardTransactionDTO toDto(CreditCardTransaction s);

    @Mapping(target = "removeMerchant", ignore = true)
    CreditCardTransaction toEntity(CreditCardTransactionDTO creditCardTransactionDTO);

    @Named("creditCardHolderId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CreditCardHolderDTO toDtoCreditCardHolderId(CreditCardHolder creditCardHolder);

    @Named("merchantId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MerchantDTO toDtoMerchantId(Merchant merchant);

    @Named("merchantIdSet")
    default Set<MerchantDTO> toDtoMerchantIdSet(Set<Merchant> merchant) {
        return merchant.stream().map(this::toDtoMerchantId).collect(Collectors.toSet());
    }
}
