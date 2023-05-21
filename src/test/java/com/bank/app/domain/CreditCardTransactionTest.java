package com.bank.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.bank.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CreditCardTransactionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CreditCardTransaction.class);
        CreditCardTransaction creditCardTransaction1 = new CreditCardTransaction();
        creditCardTransaction1.setId(1L);
        CreditCardTransaction creditCardTransaction2 = new CreditCardTransaction();
        creditCardTransaction2.setId(creditCardTransaction1.getId());
        assertThat(creditCardTransaction1).isEqualTo(creditCardTransaction2);
        creditCardTransaction2.setId(2L);
        assertThat(creditCardTransaction1).isNotEqualTo(creditCardTransaction2);
        creditCardTransaction1.setId(null);
        assertThat(creditCardTransaction1).isNotEqualTo(creditCardTransaction2);
    }
}
