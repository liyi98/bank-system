package com.bank.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CreditCardApplicantMapperTest {

    private CreditCardApplicantMapper creditCardApplicantMapper;

    @BeforeEach
    public void setUp() {
        creditCardApplicantMapper = new CreditCardApplicantMapperImpl();
    }
}
