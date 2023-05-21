package com.bank.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.bank.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CreditCardTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CreditCardTypeDTO.class);
        CreditCardTypeDTO creditCardTypeDTO1 = new CreditCardTypeDTO();
        creditCardTypeDTO1.setId(1L);
        CreditCardTypeDTO creditCardTypeDTO2 = new CreditCardTypeDTO();
        assertThat(creditCardTypeDTO1).isNotEqualTo(creditCardTypeDTO2);
        creditCardTypeDTO2.setId(creditCardTypeDTO1.getId());
        assertThat(creditCardTypeDTO1).isEqualTo(creditCardTypeDTO2);
        creditCardTypeDTO2.setId(2L);
        assertThat(creditCardTypeDTO1).isNotEqualTo(creditCardTypeDTO2);
        creditCardTypeDTO1.setId(null);
        assertThat(creditCardTypeDTO1).isNotEqualTo(creditCardTypeDTO2);
    }
}
