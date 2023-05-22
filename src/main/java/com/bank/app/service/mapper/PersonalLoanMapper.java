package com.bank.app.service.mapper;

import com.bank.app.domain.PersonalLoan;
import com.bank.app.domain.PersonalLoanApplicant;
import com.bank.app.service.dto.PersonalLoanApplicantDTO;
import com.bank.app.service.dto.PersonalLoanDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PersonalLoan} and its DTO {@link PersonalLoanDTO}.
 */
@Mapper(componentModel = "spring")
public interface PersonalLoanMapper extends EntityMapper<PersonalLoanDTO, PersonalLoan> {
    @Mapping(target = "personalLoanApplicants", source = "personalLoanApplicants", qualifiedByName = "personalLoanApplicantIdSet")
    PersonalLoanDTO toDto(PersonalLoan s);

    @Mapping(target = "removePersonalLoanApplicant", ignore = true)
    PersonalLoan toEntity(PersonalLoanDTO personalLoanDTO);

    @Named("personalLoanApplicantId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PersonalLoanApplicantDTO toDtoPersonalLoanApplicantId(PersonalLoanApplicant personalLoanApplicant);

    @Named("personalLoanApplicantIdSet")
    default Set<PersonalLoanApplicantDTO> toDtoPersonalLoanApplicantIdSet(Set<PersonalLoanApplicant> personalLoanApplicant) {
        return personalLoanApplicant.stream().map(this::toDtoPersonalLoanApplicantId).collect(Collectors.toSet());
    }
}
