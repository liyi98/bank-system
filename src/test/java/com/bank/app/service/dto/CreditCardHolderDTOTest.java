package com.bank.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.bank.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CreditCardHolderDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CreditCardHolderDTO.class);
        CreditCardHolderDTO creditCardHolderDTO1 = new CreditCardHolderDTO();
        creditCardHolderDTO1.setId(1L);
        CreditCardHolderDTO creditCardHolderDTO2 = new CreditCardHolderDTO();
        assertThat(creditCardHolderDTO1).isNotEqualTo(creditCardHolderDTO2);
        creditCardHolderDTO2.setId(creditCardHolderDTO1.getId());
        assertThat(creditCardHolderDTO1).isEqualTo(creditCardHolderDTO2);
        creditCardHolderDTO2.setId(2L);
        assertThat(creditCardHolderDTO1).isNotEqualTo(creditCardHolderDTO2);
        creditCardHolderDTO1.setId(null);
        assertThat(creditCardHolderDTO1).isNotEqualTo(creditCardHolderDTO2);
    }
}
