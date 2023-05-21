package com.bank.app.domain;

import com.bank.app.domain.enumeration.StandardStatus;
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
 * A CreditCardApplicant.
 */
@Entity
@Table(name = "credit_card_applicant")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CreditCardApplicant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StandardStatus status;

    @Column(name = "ic_path")
    private String icPath;

    @Column(name = "payslip_path")
    private String payslipPath;

    @Column(name = "epf_path")
    private String epfPath;

    @Column(name = "limit_amount", precision = 21, scale = 2)
    private BigDecimal limitAmount;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @JsonIgnoreProperties(value = { "creditCardApplicant", "creditCardTransactions" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private CreditCardHolder creditCardHolder;

    @ManyToOne
    @JsonIgnoreProperties(value = { "creditCardApplicants" }, allowSetters = true)
    private BankUser bankUser;

    @ManyToMany
    @JoinTable(
        name = "rel_credit_card_applicant__credit_card_type",
        joinColumns = @JoinColumn(name = "credit_card_applicant_id"),
        inverseJoinColumns = @JoinColumn(name = "credit_card_type_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "creditCardApplicants" }, allowSetters = true)
    private Set<CreditCardType> creditCardTypes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CreditCardApplicant id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
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

    public BigDecimal getLimitAmount() {
        return this.limitAmount;
    }

    public CreditCardApplicant limitAmount(BigDecimal limitAmount) {
        this.setLimitAmount(limitAmount);
        return this;
    }

    public void setLimitAmount(BigDecimal limitAmount) {
        this.limitAmount = limitAmount;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public CreditCardApplicant createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public CreditCardApplicant createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public CreditCardApplicant lastModifiedDate(Instant lastModifiedDate) {
        this.setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public CreditCardApplicant lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public CreditCardHolder getCreditCardHolder() {
        return this.creditCardHolder;
    }

    public void setCreditCardHolder(CreditCardHolder creditCardHolder) {
        this.creditCardHolder = creditCardHolder;
    }

    public CreditCardApplicant creditCardHolder(CreditCardHolder creditCardHolder) {
        this.setCreditCardHolder(creditCardHolder);
        return this;
    }

    public BankUser getBankUser() {
        return this.bankUser;
    }

    public void setBankUser(BankUser bankUser) {
        this.bankUser = bankUser;
    }

    public CreditCardApplicant bankUser(BankUser bankUser) {
        this.setBankUser(bankUser);
        return this;
    }

    public Set<CreditCardType> getCreditCardTypes() {
        return this.creditCardTypes;
    }

    public void setCreditCardTypes(Set<CreditCardType> creditCardTypes) {
        this.creditCardTypes = creditCardTypes;
    }

    public CreditCardApplicant creditCardTypes(Set<CreditCardType> creditCardTypes) {
        this.setCreditCardTypes(creditCardTypes);
        return this;
    }

    public CreditCardApplicant addCreditCardType(CreditCardType creditCardType) {
        this.creditCardTypes.add(creditCardType);
        creditCardType.getCreditCardApplicants().add(this);
        return this;
    }

    public CreditCardApplicant removeCreditCardType(CreditCardType creditCardType) {
        this.creditCardTypes.remove(creditCardType);
        creditCardType.getCreditCardApplicants().remove(this);
        return this;
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
            ", status='" + getStatus() + "'" +
            ", icPath='" + getIcPath() + "'" +
            ", payslipPath='" + getPayslipPath() + "'" +
            ", epfPath='" + getEpfPath() + "'" +
            ", limitAmount=" + getLimitAmount() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
