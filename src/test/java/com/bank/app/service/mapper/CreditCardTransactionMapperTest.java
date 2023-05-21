package com.bank.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CreditCardTransactionMapperTest {

    private CreditCardTransactionMapper creditCardTransactionMapper;

    @BeforeEach
    public void setUp() {
        creditCardTransactionMapper = new CreditCardTransactionMapperImpl();
    }
}
