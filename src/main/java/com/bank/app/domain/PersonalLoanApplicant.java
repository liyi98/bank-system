package com.bank.app.domain;

import com.bank.app.domain.enumeration.StandardStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PersonalLoanApplicant.
 */
@Entity
@Table(name = "personal_loan_applicant")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PersonalLoanApplicant implements Serializable {

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

    @Column(name = "loan_amount", precision = 21, scale = 2)
    private BigDecimal loanAmount;

    @ManyToOne
    @JsonIgnoreProperties(value = { "personalLoanApplicants" }, allowSetters = true)
    private BankUser bankUser;

    @OneToMany(mappedBy = "personalLoanApplicant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "personalLoanApplicant" }, allowSetters = true)
    private Set<PersonalLoanTransaction> personalLoanTransactions = new HashSet<>();

    @ManyToMany(mappedBy = "personalLoanApplicants")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "personalLoanApplicants" }, allowSetters = true)
    private Set<PersonalLoan> personalLoans = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PersonalLoanApplicant id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StandardStatus getStatus() {
        return this.status;
    }

    public PersonalLoanApplicant status(StandardStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(StandardStatus status) {
        this.status = status;
    }

    public String getIcPath() {
        return this.icPath;
    }

    public PersonalLoanApplicant icPath(String icPath) {
        this.setIcPath(icPath);
        return this;
    }

    public void setIcPath(String icPath) {
        this.icPath = icPath;
    }

    public String getPayslipPath() {
        return this.payslipPath;
    }

    public PersonalLoanApplicant payslipPath(String payslipPath) {
        this.setPayslipPath(payslipPath);
        return this;
    }

    public void setPayslipPath(String payslipPath) {
        this.payslipPath = payslipPath;
    }

    public String getEpfPath() {
        return this.epfPath;
    }

    public PersonalLoanApplicant epfPath(String epfPath) {
        this.setEpfPath(epfPath);
        return this;
    }

    public void setEpfPath(String epfPath) {
        this.epfPath = epfPath;
    }

    public BigDecimal getLoanAmount() {
        return this.loanAmount;
    }

    public PersonalLoanApplicant loanAmount(BigDecimal loanAmount) {
        this.setLoanAmount(loanAmount);
        return this;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public BankUser getBankUser() {
        return this.bankUser;
    }

    public void setBankUser(BankUser bankUser) {
        this.bankUser = bankUser;
    }

    public PersonalLoanApplicant bankUser(BankUser bankUser) {
        this.setBankUser(bankUser);
        return this;
    }

    public Set<PersonalLoanTransaction> getPersonalLoanTransactions() {
        return this.personalLoanTransactions;
    }

    public void setPersonalLoanTransactions(Set<PersonalLoanTransaction> personalLoanTransactions) {
        if (this.personalLoanTransactions != null) {
            this.personalLoanTransactions.forEach(i -> i.setPersonalLoanApplicant(null));
        }
        if (personalLoanTransactions != null) {
            personalLoanTransactions.forEach(i -> i.setPersonalLoanApplicant(this));
        }
        this.personalLoanTransactions = personalLoanTransactions;
    }

    public PersonalLoanApplicant personalLoanTransactions(Set<PersonalLoanTransaction> personalLoanTransactions) {
        this.setPersonalLoanTransactions(personalLoanTransactions);
        return this;
    }

    public PersonalLoanApplicant addPersonalLoanTransaction(PersonalLoanTransaction personalLoanTransaction) {
        this.personalLoanTransactions.add(personalLoanTransaction);
        personalLoanTransaction.setPersonalLoanApplicant(this);
        return this;
    }

    public PersonalLoanApplicant removePersonalLoanTransaction(PersonalLoanTransaction personalLoanTransaction) {
        this.personalLoanTransactions.remove(personalLoanTransaction);
        personalLoanTransaction.setPersonalLoanApplicant(null);
        return this;
    }

    public Set<PersonalLoan> getPersonalLoans() {
        return this.personalLoans;
    }

    public void setPersonalLoans(Set<PersonalLoan> personalLoans) {
        if (this.personalLoans != null) {
            this.personalLoans.forEach(i -> i.removePersonalLoanApplicant(this));
        }
        if (personalLoans != null) {
            personalLoans.forEach(i -> i.addPersonalLoanApplicant(this));
        }
        this.personalLoans = personalLoans;
    }

    public PersonalLoanApplicant personalLoans(Set<PersonalLoan> personalLoans) {
        this.setPersonalLoans(personalLoans);
        return this;
    }

    public PersonalLoanApplicant addPersonalLoan(PersonalLoan personalLoan) {
        this.personalLoans.add(personalLoan);
        personalLoan.getPersonalLoanApplicants().add(this);
        return this;
    }

    public PersonalLoanApplicant removePersonalLoan(PersonalLoan personalLoan) {
        this.personalLoans.remove(personalLoan);
        personalLoan.getPersonalLoanApplicants().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonalLoanApplicant)) {
            return false;
        }
        return id != null && id.equals(((PersonalLoanApplicant) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PersonalLoanApplicant{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", icPath='" + getIcPath() + "'" +
            ", payslipPath='" + getPayslipPath() + "'" +
            ", epfPath='" + getEpfPath() + "'" +
            ", loanAmount=" + getLoanAmount() +
            "}";
    }
}
