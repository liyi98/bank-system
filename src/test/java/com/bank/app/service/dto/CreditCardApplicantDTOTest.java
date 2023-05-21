package com.bank.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.bank.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CreditCardApplicantDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CreditCardApplicantDTO.class);
        CreditCardApplicantDTO creditCardApplicantDTO1 = new CreditCardApplicantDTO();
        creditCardApplicantDTO1.setId(1L);
        CreditCardApplicantDTO creditCardApplicantDTO2 = new CreditCardApplicantDTO();
        assertThat(creditCardApplicantDTO1).isNotEqualTo(creditCardApplicantDTO2);
        creditCardApplicantDTO2.setId(creditCardApplicantDTO1.getId());
        assertThat(creditCardApplicantDTO1).isEqualTo(creditCardApplicantDTO2);
        creditCardApplicantDTO2.setId(2L);
        assertThat(creditCardApplicantDTO1).isNotEqualTo(creditCardApplicantDTO2);
        creditCardApplicantDTO1.setId(null);
        assertThat(creditCardApplicantDTO1).isNotEqualTo(creditCardApplicantDTO2);
    }
}
