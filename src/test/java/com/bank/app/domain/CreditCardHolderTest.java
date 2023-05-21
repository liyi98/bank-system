package com.bank.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.bank.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CreditCardHolderTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CreditCardHolder.class);
        CreditCardHolder creditCardHolder1 = new CreditCardHolder();
        creditCardHolder1.setId(1L);
        CreditCardHolder creditCardHolder2 = new CreditCardHolder();
        creditCardHolder2.setId(creditCardHolder1.getId());
        assertThat(creditCardHolder1).isEqualTo(creditCardHolder2);
        creditCardHolder2.setId(2L);
        assertThat(creditCardHolder1).isNotEqualTo(creditCardHolder2);
        creditCardHolder1.setId(null);
        assertThat(creditCardHolder1).isNotEqualTo(creditCardHolder2);
    }
}
