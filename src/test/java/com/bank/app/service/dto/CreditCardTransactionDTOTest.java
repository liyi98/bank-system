package com.bank.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.bank.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CreditCardTransactionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CreditCardTransactionDTO.class);
        CreditCardTransactionDTO creditCardTransactionDTO1 = new CreditCardTransactionDTO();
        creditCardTransactionDTO1.setId(1L);
        CreditCardTransactionDTO creditCardTransactionDTO2 = new CreditCardTransactionDTO();
        assertThat(creditCardTransactionDTO1).isNotEqualTo(creditCardTransactionDTO2);
        creditCardTransactionDTO2.setId(creditCardTransactionDTO1.getId());
        assertThat(creditCardTransactionDTO1).isEqualTo(creditCardTransactionDTO2);
        creditCardTransactionDTO2.setId(2L);
        assertThat(creditCardTransactionDTO1).isNotEqualTo(creditCardTransactionDTO2);
        creditCardTransactionDTO1.setId(null);
        assertThat(creditCardTransactionDTO1).isNotEqualTo(creditCardTransactionDTO2);
    }
}
