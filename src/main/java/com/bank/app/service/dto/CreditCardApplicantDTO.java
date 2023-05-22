package com.bank.app.service.dto;

import com.bank.app.domain.CreditCardApplicant;
import com.bank.app.domain.enumeration.StandardStatus;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.bank.app.domain.CreditCardApplicant} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CreditCardApplicantDTO implements Serializable {

    private Long id;

    private String name;

    private StandardStatus status;

    private String ic;

    private String email;

    private LocalDate dob;

    private String address;

    private String phone;

    private String sector;

    private String companyName;

    private BigDecimal serviceLength;

    private BigDecimal annualIncome;

    private String occupation;

    private String icPath;

    private String payslipPath;

    private String epfPath;

    private Instant createdDate;

    private String createdBy;

    private Instant lastModifiedDate;

    private String lastModifiedBy;

    private Long bankUserId;

    private Long creditCardHolderId;

    private Long creditCardTypeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StandardStatus getStatus() {
        return status;
    }

    public void setStatus(StandardStatus status) {
        this.status = status;
    }

    public String getIc() {
        return ic;
    }

    public void setIc(String ic) {
        this.ic = ic;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public BigDecimal getServiceLength() {
        return serviceLength;
    }

    public void setServiceLength(BigDecimal serviceLength) {
        this.serviceLength = serviceLength;
    }

    public BigDecimal getAnnualIncome() {
        return annualIncome;
    }

    public void setAnnualIncome(BigDecimal annualIncome) {
        this.annualIncome = annualIncome;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
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

    public Long getBankUserId() {
        return bankUserId;
    }

    public void setBankUserId(Long bankUserId) {
        this.bankUserId = bankUserId;
    }

    public Long getCreditCardHolderId() {
        return creditCardHolderId;
    }

    public void setCreditCardHolderId(Long creditCardHolderId) {
        this.creditCardHolderId = creditCardHolderId;
    }

    public Long getCreditCardTypeId() {
        return creditCardTypeId;
    }

    public void setCreditCardTypeId(Long creditCardTypeId) {
        this.creditCardTypeId = creditCardTypeId;
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
            ", name='" + getName() + "'" +
            ", status='" + getStatus() + "'" +
            ", ic='" + getIc() + "'" +
            ", dob='" + getDob() + "'" +
            ", address='" + getAddress() + "'" +
            ", phone='" + getPhone() + "'" +
            ", sector='" + getSector() + "'" +
            ", companyName='" + getCompanyName() + "'" +
            ", serviceLength=" + getServiceLength() +
            ", annualIncome=" + getAnnualIncome() +
            ", occupation='" + getOccupation() + "'" +
            ", icPath='" + getIcPath() + "'" +
            ", payslipPath='" + getPayslipPath() + "'" +
            ", epfPath='" + getEpfPath() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", bankUserId=" + getBankUserId() +
            ", creditCardHolderId=" + getCreditCardHolderId() +
            ", creditCardTypeId=" + getCreditCardTypeId() +
            "}";
    }
}
