package com.bank.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BankUserMapperTest {

    private BankUserMapper bankUserMapper;

    @BeforeEach
    public void setUp() {
        bankUserMapper = new BankUserMapperImpl();
    }
}
