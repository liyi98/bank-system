package com.bank.app.service;

import com.bank.app.service.dto.CreditCardApplicantDTO;
import com.bank.app.service.exception.CreditCardException;
import java.util.List;
import java.util.Optional;

public interface CreditCardApplicantService {
    CreditCardApplicantDTO save(CreditCardApplicantDTO creditCardApplicantDTO);

    CreditCardApplicantDTO update(CreditCardApplicantDTO creditCardApplicantDTO);

    Optional<CreditCardApplicantDTO> partialUpdate(CreditCardApplicantDTO creditCardApplicantDTO);

    List<CreditCardApplicantDTO> findAll();

    Optional<CreditCardApplicantDTO> findOne(Long id);

    void delete(Long id);

    void approve(Long id) throws CreditCardException;

    void reject(Long id) throws CreditCardException;
}
