package com.bank.app.service.mapper;

import com.bank.app.domain.PersonalLoanApplicant;
import com.bank.app.domain.PersonalLoanTransaction;
import com.bank.app.service.dto.PersonalLoanApplicantDTO;
import com.bank.app.service.dto.PersonalLoanTransactionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PersonalLoanTransaction} and its DTO {@link PersonalLoanTransactionDTO}.
 */
@Mapper(componentModel = "spring")
public interface PersonalLoanTransactionMapper extends EntityMapper<PersonalLoanTransactionDTO, PersonalLoanTransaction> {
    @Mapping(target = "personalLoanApplicant", source = "personalLoanApplicant", qualifiedByName = "personalLoanApplicantId")
    PersonalLoanTransactionDTO toDto(PersonalLoanTransaction s);

    @Named("personalLoanApplicantId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PersonalLoanApplicantDTO toDtoPersonalLoanApplicantId(PersonalLoanApplicant personalLoanApplicant);
}
