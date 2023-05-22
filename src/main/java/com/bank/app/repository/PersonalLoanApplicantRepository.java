package com.bank.app.repository;

import com.bank.app.domain.PersonalLoanApplicant;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PersonalLoanApplicant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonalLoanApplicantRepository extends JpaRepository<PersonalLoanApplicant, Long> {}
