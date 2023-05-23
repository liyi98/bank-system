package com.bank.app.repository;

import com.bank.app.domain.BankUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BankUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BankUserRepository extends JpaRepository<BankUser, Long> {
    Optional<BankUser> findOneByLogin(String login);
}
