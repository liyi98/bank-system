package com.bank.app.domain;

import com.bank.app.domain.enumeration.StandardStatus;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CreditCardApplicant.
 */
@Entity
@Table(name = "credit_card_applicant")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CreditCardApplicant extends AbstractAuditingEntity<CreditCardApplicant> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StandardStatus status;

    @Column(name = "ic")
    private String ic;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "sector")
    private String sector;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "service_length", precision = 21, scale = 2)
    private BigDecimal serviceLength;

    @Column(name = "annual_income", precision = 21, scale = 2)
    private BigDecimal annualIncome;

    @Column(name = "occupation")
    private String occupation;

    @Column(name = "ic_path")
    private String icPath;

    @Column(name = "payslip_path")
    private String payslipPath;

    @Column(name = "epf_path")
    private String epfPath;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "bank_user_id")
    private Long bankUserId;

    @Column(name = "credit_card_holder_id")
    private Long creditCardHolderId;

    @Column(name = "credit_card_type_id")
    private Long creditCardTypeId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.getId();
    }

    public CreditCardApplicant id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public CreditCardApplicant name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StandardStatus getStatus() {
        return this.status;
    }

    public CreditCardApplicant status(StandardStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(StandardStatus status) {
        this.status = status;
    }

    public String getIc() {
        return this.ic;
    }

    public CreditCardApplicant ic(String ic) {
        this.setIc(ic);
        return this;
    }

    public void setIc(String ic) {
        this.ic = ic;
    }

    public LocalDate getDob() {
        return this.dob;
    }

    public CreditCardApplicant dob(LocalDate dob) {
        this.setDob(dob);
        return this;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return this.address;
    }

    public CreditCardApplicant address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return this.phone;
    }

    public CreditCardApplicant phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSector() {
        return this.sector;
    }

    public CreditCardApplicant sector(String sector) {
        this.setSector(sector);
        return this;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public CreditCardApplicant companyName(String companyName) {
        this.setCompanyName(companyName);
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public BigDecimal getServiceLength() {
        return this.serviceLength;
    }

    public CreditCardApplicant serviceLength(BigDecimal serviceLength) {
        this.setServiceLength(serviceLength);
        return this;
    }

    public void setServiceLength(BigDecimal serviceLength) {
        this.serviceLength = serviceLength;
    }

    public BigDecimal getAnnualIncome() {
        return this.annualIncome;
    }

    public CreditCardApplicant annualIncome(BigDecimal annualIncome) {
        this.setAnnualIncome(annualIncome);
        return this;
    }

    public void setAnnualIncome(BigDecimal annualIncome) {
        this.annualIncome = annualIncome;
    }

    public String getOccupation() {
        return this.occupation;
    }

    public CreditCardApplicant occupation(String occupation) {
        this.setOccupation(occupation);
        return this;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getIcPath() {
        return this.icPath;
    }

    public CreditCardApplicant icPath(String icPath) {
        this.setIcPath(icPath);
        return this;
    }

    public void setIcPath(String icPath) {
        this.icPath = icPath;
    }

    public String getPayslipPath() {
        return this.payslipPath;
    }

    public CreditCardApplicant payslipPath(String payslipPath) {
        this.setPayslipPath(payslipPath);
        return this;
    }

    public void setPayslipPath(String payslipPath) {
        this.payslipPath = payslipPath;
    }

    public String getEpfPath() {
        return this.epfPath;
    }

    public CreditCardApplicant epfPath(String epfPath) {
        this.setEpfPath(epfPath);
        return this;
    }

    public void setEpfPath(String epfPath) {
        this.epfPath = epfPath;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Long getBankUserId() {
        return this.bankUserId;
    }

    public CreditCardApplicant bankUserId(Long bankUserId) {
        this.setBankUserId(bankUserId);
        return this;
    }

    public void setBankUserId(Long bankUserId) {
        this.bankUserId = bankUserId;
    }

    public Long getCreditCardHolderId() {
        return this.creditCardHolderId;
    }

    public CreditCardApplicant creditCardHolderId(Long creditCardHolderId) {
        this.setCreditCardHolderId(creditCardHolderId);
        return this;
    }

    public void setCreditCardHolderId(Long creditCardHolderId) {
        this.creditCardHolderId = creditCardHolderId;
    }

    public Long getCreditCardTypeId() {
        return this.creditCardTypeId;
    }

    public CreditCardApplicant creditCardTypeId(Long creditCardTypeId) {
        this.setCreditCardTypeId(creditCardTypeId);
        return this;
    }

    public void setCreditCardTypeId(Long creditCardTypeId) {
        this.creditCardTypeId = creditCardTypeId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreditCardApplicant)) {
            return false;
        }
        return id != null && id.equals(((CreditCardApplicant) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CreditCardApplicant{" +
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
