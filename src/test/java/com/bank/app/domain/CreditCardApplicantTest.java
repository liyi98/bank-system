package com.bank.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.bank.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CreditCardApplicantTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CreditCardApplicant.class);
        CreditCardApplicant creditCardApplicant1 = new CreditCardApplicant();
        creditCardApplicant1.setId(1L);
        CreditCardApplicant creditCardApplicant2 = new CreditCardApplicant();
        creditCardApplicant2.setId(creditCardApplicant1.getId());
        assertThat(creditCardApplicant1).isEqualTo(creditCardApplicant2);
        creditCardApplicant2.setId(2L);
        assertThat(creditCardApplicant1).isNotEqualTo(creditCardApplicant2);
        creditCardApplicant1.setId(null);
        assertThat(creditCardApplicant1).isNotEqualTo(creditCardApplicant2);
    }
}
