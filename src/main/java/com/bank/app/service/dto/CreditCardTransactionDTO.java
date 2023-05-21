package com.bank.app.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.bank.app.domain.CreditCardTransaction} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CreditCardTransactionDTO implements Serializable {

    private Long id;

    private BigDecimal balance;

    private String description;

    private Instant createdDate;

    private String createdBy;

    private Instant lastModifiedDate;

    private String lastModifiedBy;

    private CreditCardHolderDTO creditCardHolder;

    private Set<MerchantDTO> merchants = new HashSet<>();

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

    public CreditCardHolderDTO getCreditCardHolder() {
        return creditCardHolder;
    }

    public void setCreditCardHolder(CreditCardHolderDTO creditCardHolder) {
        this.creditCardHolder = creditCardHolder;
    }

    public Set<MerchantDTO> getMerchants() {
        return merchants;
    }

    public void setMerchants(Set<MerchantDTO> merchants) {
        this.merchants = merchants;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreditCardTransactionDTO)) {
            return false;
        }

        CreditCardTransactionDTO creditCardTransactionDTO = (CreditCardTransactionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, creditCardTransactionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CreditCardTransactionDTO{" +
            "id=" + getId() +
            ", balance=" + getBalance() +
            ", description='" + getDescription() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", creditCardHolder=" + getCreditCardHolder() +
            ", merchants=" + getMerchants() +
            "}";
    }
}
