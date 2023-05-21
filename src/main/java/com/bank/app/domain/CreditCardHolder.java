package com.bank.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CreditCardHolder.
 */
@Entity
@Table(name = "credit_card_holder")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CreditCardHolder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "card_number")
    private Integer cardNumber;

    @Column(name = "name")
    private String name;

    @Column(name = "expired_date")
    private LocalDate expiredDate;

    @Column(name = "secure_number")
    private Integer secureNumber;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @JsonIgnoreProperties(value = { "creditCardHolder", "bankUser", "creditCardTypes" }, allowSetters = true)
    @OneToOne(mappedBy = "creditCardHolder")
    private CreditCardApplicant creditCardApplicant;

    @OneToMany(mappedBy = "creditCardHolder")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "creditCardHolder", "merchants" }, allowSetters = true)
    private Set<CreditCardTransaction> creditCardTransactions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CreditCardHolder id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCardNumber() {
        return this.cardNumber;
    }

    public CreditCardHolder cardNumber(Integer cardNumber) {
        this.setCardNumber(cardNumber);
        return this;
    }

    public void setCardNumber(Integer cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getName() {
        return this.name;
    }

    public CreditCardHolder name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getExpiredDate() {
        return this.expiredDate;
    }

    public CreditCardHolder expiredDate(LocalDate expiredDate) {
        this.setExpiredDate(expiredDate);
        return this;
    }

    public void setExpiredDate(LocalDate expiredDate) {
        this.expiredDate = expiredDate;
    }

    public Integer getSecureNumber() {
        return this.secureNumber;
    }

    public CreditCardHolder secureNumber(Integer secureNumber) {
        this.setSecureNumber(secureNumber);
        return this;
    }

    public void setSecureNumber(Integer secureNumber) {
        this.secureNumber = secureNumber;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public CreditCardHolder createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public CreditCardHolder createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public CreditCardHolder lastModifiedDate(Instant lastModifiedDate) {
        this.setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public CreditCardHolder lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public CreditCardApplicant getCreditCardApplicant() {
        return this.creditCardApplicant;
    }

    public void setCreditCardApplicant(CreditCardApplicant creditCardApplicant) {
        if (this.creditCardApplicant != null) {
            this.creditCardApplicant.setCreditCardHolder(null);
        }
        if (creditCardApplicant != null) {
            creditCardApplicant.setCreditCardHolder(this);
        }
        this.creditCardApplicant = creditCardApplicant;
    }

    public CreditCardHolder creditCardApplicant(CreditCardApplicant creditCardApplicant) {
        this.setCreditCardApplicant(creditCardApplicant);
        return this;
    }

    public Set<CreditCardTransaction> getCreditCardTransactions() {
        return this.creditCardTransactions;
    }

    public void setCreditCardTransactions(Set<CreditCardTransaction> creditCardTransactions) {
        if (this.creditCardTransactions != null) {
            this.creditCardTransactions.forEach(i -> i.setCreditCardHolder(null));
        }
        if (creditCardTransactions != null) {
            creditCardTransactions.forEach(i -> i.setCreditCardHolder(this));
        }
        this.creditCardTransactions = creditCardTransactions;
    }

    public CreditCardHolder creditCardTransactions(Set<CreditCardTransaction> creditCardTransactions) {
        this.setCreditCardTransactions(creditCardTransactions);
        return this;
    }

    public CreditCardHolder addCreditCardTransaction(CreditCardTransaction creditCardTransaction) {
        this.creditCardTransactions.add(creditCardTransaction);
        creditCardTransaction.setCreditCardHolder(this);
        return this;
    }

    public CreditCardHolder removeCreditCardTransaction(CreditCardTransaction creditCardTransaction) {
        this.creditCardTransactions.remove(creditCardTransaction);
        creditCardTransaction.setCreditCardHolder(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreditCardHolder)) {
            return false;
        }
        return id != null && id.equals(((CreditCardHolder) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CreditCardHolder{" +
            "id=" + getId() +
            ", cardNumber=" + getCardNumber() +
            ", name='" + getName() + "'" +
            ", expiredDate='" + getExpiredDate() + "'" +
            ", secureNumber=" + getSecureNumber() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}