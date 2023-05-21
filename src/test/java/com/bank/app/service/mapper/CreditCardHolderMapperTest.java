package com.bank.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CreditCardHolderMapperTest {

    private CreditCardHolderMapper creditCardHolderMapper;

    @BeforeEach
    public void setUp() {
        creditCardHolderMapper = new CreditCardHolderMapperImpl();
    }
}
