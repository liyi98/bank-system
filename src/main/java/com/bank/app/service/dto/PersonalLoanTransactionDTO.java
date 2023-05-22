package com.bank.app.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.bank.app.domain.PersonalLoanTransaction} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PersonalLoanTransactionDTO implements Serializable {

    private Long id;

    private BigDecimal balance;

    private String description;

    private Instant createdDate;

    private String createdBy;

    private Instant lastModifiedDate;

    private String lastModifiedBy;

    private PersonalLoanApplicantDTO personalLoanApplicant;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public PersonalLoanApplicantDTO getPersonalLoanApplicant() {
        return personalLoanApplicant;
    }

    public void setPersonalLoanApplicant(PersonalLoanApplicantDTO personalLoanApplicant) {
        this.personalLoanApplicant = personalLoanApplicant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonalLoanTransactionDTO)) {
            return false;
        }

        PersonalLoanTransactionDTO personalLoanTransactionDTO = (PersonalLoanTransactionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, personalLoanTransactionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PersonalLoanTransactionDTO{" +
            "id=" + getId() +
            ", balance=" + getBalance() +
            ", description='" + getDescription() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", personalLoanApplicant=" + getPersonalLoanApplicant() +
            "}";
    }
}
