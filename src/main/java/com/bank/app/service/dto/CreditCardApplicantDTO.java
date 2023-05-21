package com.bank.app.service.dto;

import com.bank.app.domain.enumeration.StandardStatus;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.bank.app.domain.CreditCardApplicant} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CreditCardApplicantDTO implements Serializable {

    private Long id;

    private StandardStatus status;

    private String icPath;

    private String payslipPath;

    private String epfPath;

    private BigDecimal limitAmount;

    private Instant createdDate;

    private String createdBy;

    private Instant lastModifiedDate;

    private String lastModifiedBy;

    private CreditCardHolderDTO creditCardHolder;

    private BankUserDTO bankUser;

    private Set<CreditCardTypeDTO> creditCardTypes = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StandardStatus getStatus() {
        return status;
    }

    public void setStatus(StandardStatus status) {
        this.status = status;
    }

    public String getIcPath() {
        return icPath;
    }

    public void setIcPath(String icPath) {
        this.icPath = icPath;
    }

    public String getPayslipPath() {
        return payslipPath;
    }

    public void setPayslipPath(String payslipPath) {
        this.payslipPath = payslipPath;
    }

    public String getEpfPath() {
        return epfPath;
    }

    public void setEpfPath(String epfPath) {
        this.epfPath = epfPath;
    }

    public BigDecimal getLimitAmount() {
        return limitAmount;
    }

    public void setLimitAmount(BigDecimal limitAmount) {
        this.limitAmount = limitAmount;
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

    public BankUserDTO getBankUser() {
        return bankUser;
    }

    public void setBankUser(BankUserDTO bankUser) {
        this.bankUser = bankUser;
    }

    public Set<CreditCardTypeDTO> getCreditCardTypes() {
        return creditCardTypes;
    }

    public void setCreditCardTypes(Set<CreditCardTypeDTO> creditCardTypes) {
        this.creditCardTypes = creditCardTypes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreditCardApplicantDTO)) {
            return false;
        }

        CreditCardApplicantDTO creditCardApplicantDTO = (CreditCardApplicantDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, creditCardApplicantDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CreditCardApplicantDTO{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", icPath='" + getIcPath() + "'" +
            ", payslipPath='" + getPayslipPath() + "'" +
            ", epfPath='" + getEpfPath() + "'" +
            ", limitAmount=" + getLimitAmount() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", creditCardHolder=" + getCreditCardHolder() +
            ", bankUser=" + getBankUser() +
            ", creditCardTypes=" + getCreditCardTypes() +
            "}";
    }
}
