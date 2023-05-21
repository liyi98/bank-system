package com.bank.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.bank.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BankUserDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankUserDTO.class);
        BankUserDTO bankUserDTO1 = new BankUserDTO();
        bankUserDTO1.setId(1L);
        BankUserDTO bankUserDTO2 = new BankUserDTO();
        assertThat(bankUserDTO1).isNotEqualTo(bankUserDTO2);
        bankUserDTO2.setId(bankUserDTO1.getId());
        assertThat(bankUserDTO1).isEqualTo(bankUserDTO2);
        bankUserDTO2.setId(2L);
        assertThat(bankUserDTO1).isNotEqualTo(bankUserDTO2);
        bankUserDTO1.setId(null);
        assertThat(bankUserDTO1).isNotEqualTo(bankUserDTO2);
    }
}
