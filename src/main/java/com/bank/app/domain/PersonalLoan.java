package com.bank.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PersonalLoan.
 */
@Entity
@Table(name = "personal_loan")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PersonalLoan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "period")
    private String period;

    @Column(name = "interest", precision = 21, scale = 2)
    private BigDecimal interest;

    @Column(name = "type")
    private String type;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @ManyToMany
    @JoinTable(
        name = "rel_personal_loan__personal_loan_applicant",
        joinColumns = @JoinColumn(name = "personal_loan_id"),
        inverseJoinColumns = @JoinColumn(name = "personal_loan_applicant_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "bankUser", "personalLoanTransactions", "personalLoans" }, allowSetters = true)
    private Set<PersonalLoanApplicant> personalLoanApplicants = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PersonalLoan id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPeriod() {
        return this.period;
    }

    public PersonalLoan period(String period) {
        this.setPeriod(period);
        return this;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public BigDecimal getInterest() {
        return this.interest;
    }

    public PersonalLoan interest(BigDecimal interest) {
        this.setInterest(interest);
        return this;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public String getType() {
        return this.type;
    }

    public PersonalLoan type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public PersonalLoan createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public PersonalLoan createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public PersonalLoan lastModifiedDate(Instant lastModifiedDate) {
        this.setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public PersonalLoan lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Set<PersonalLoanApplicant> getPersonalLoanApplicants() {
        return this.personalLoanApplicants;
    }

    public void setPersonalLoanApplicants(Set<PersonalLoanApplicant> personalLoanApplicants) {
        this.personalLoanApplicants = personalLoanApplicants;
    }

    public PersonalLoan personalLoanApplicants(Set<PersonalLoanApplicant> personalLoanApplicants) {
        this.setPersonalLoanApplicants(personalLoanApplicants);
        return this;
    }

    public PersonalLoan addPersonalLoanApplicant(PersonalLoanApplicant personalLoanApplicant) {
        this.personalLoanApplicants.add(personalLoanApplicant);
        personalLoanApplicant.getPersonalLoans().add(this);
        return this;
    }

    public PersonalLoan removePersonalLoanApplicant(PersonalLoanApplicant personalLoanApplicant) {
        this.personalLoanApplicants.remove(personalLoanApplicant);
        personalLoanApplicant.getPersonalLoans().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonalLoan)) {
            return false;
        }
        return id != null && id.equals(((PersonalLoan) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PersonalLoan{" +
            "id=" + getId() +
            ", period='" + getPeriod() + "'" +
            ", interest=" + getInterest() +
            ", type='" + getType() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
