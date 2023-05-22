package com.bank.app.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.bank.app.domain.PersonalLoan} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PersonalLoanDTO implements Serializable {

    private Long id;

    private String period;

    private BigDecimal interest;

    private String type;

    private Instant createdDate;

    private String createdBy;

    private Instant lastModifiedDate;

    private String lastModifiedBy;

    private Set<PersonalLoanApplicantDTO> personalLoanApplicants = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Set<PersonalLoanApplicantDTO> getPersonalLoanApplicants() {
        return personalLoanApplicants;
    }

    public void setPersonalLoanApplicants(Set<PersonalLoanApplicantDTO> personalLoanApplicants) {
        this.personalLoanApplicants = personalLoanApplicants;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonalLoanDTO)) {
            return false;
        }

        PersonalLoanDTO personalLoanDTO = (PersonalLoanDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, personalLoanDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PersonalLoanDTO{" +
            "id=" + getId() +
            ", period='" + getPeriod() + "'" +
            ", interest=" + getInterest() +
            ", type='" + getType() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", personalLoanApplicants=" + getPersonalLoanApplicants() +
            "}";
    }
}
