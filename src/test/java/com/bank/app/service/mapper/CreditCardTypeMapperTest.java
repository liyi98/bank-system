package com.bank.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CreditCardTypeMapperTest {

    private CreditCardTypeMapper creditCardTypeMapper;

    @BeforeEach
    public void setUp() {
        creditCardTypeMapper = new CreditCardTypeMapperImpl();
    }
}
