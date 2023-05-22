package com.bank.app.service.dto;

import com.bank.app.domain.enumeration.StandardStatus;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.bank.app.domain.PersonalLoanApplicant} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PersonalLoanApplicantDTO implements Serializable {

    private Long id;

    private StandardStatus status;

    private String icPath;

    private String payslipPath;

    private String epfPath;

    private BigDecimal loanAmount;

    private BankUserDTO bankUser;

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

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public BankUserDTO getBankUser() {
        return bankUser;
    }

    public void setBankUser(BankUserDTO bankUser) {
        this.bankUser = bankUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonalLoanApplicantDTO)) {
            return false;
        }

        PersonalLoanApplicantDTO personalLoanApplicantDTO = (PersonalLoanApplicantDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, personalLoanApplicantDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PersonalLoanApplicantDTO{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", icPath='" + getIcPath() + "'" +
            ", payslipPath='" + getPayslipPath() + "'" +
            ", epfPath='" + getEpfPath() + "'" +
            ", loanAmount=" + getLoanAmount() +
            ", bankUser=" + getBankUser() +
            "}";
    }
}
