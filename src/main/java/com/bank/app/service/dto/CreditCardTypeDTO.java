package com.bank.app.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.bank.app.domain.CreditCardType} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CreditCardTypeDTO implements Serializable {

    private Long id;

    private String name;

    private BigDecimal minimumAnnual;

    private String firstDescription;

    private String secondDescription;

    private String thirdDescription;

    private String fourthDescription;

    private String imagePath;

    private Instant createdDate;

    private String createdBy;

    private Instant lastModifiedDate;

    private String lastModifiedBy;

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

    public BigDecimal getMinimumAnnual() {
        return minimumAnnual;
    }

    public void setMinimumAnnual(BigDecimal minimumAnnual) {
        this.minimumAnnual = minimumAnnual;
    }

    public String getFirstDescription() {
        return firstDescription;
    }

    public void setFirstDescription(String firstDescription) {
        this.firstDescription = firstDescription;
    }

    public String getSecondDescription() {
        return secondDescription;
    }

    public void setSecondDescription(String secondDescription) {
        this.secondDescription = secondDescription;
    }

    public String getThirdDescription() {
        return thirdDescription;
    }

    public void setThirdDescription(String thirdDescription) {
        this.thirdDescription = thirdDescription;
    }

    public String getFourthDescription() {
        return fourthDescription;
    }

    public void setFourthDescription(String fourthDescription) {
        this.fourthDescription = fourthDescription;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreditCardTypeDTO)) {
            return false;
        }

        CreditCardTypeDTO creditCardTypeDTO = (CreditCardTypeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, creditCardTypeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CreditCardTypeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", minimumAnnual=" + getMinimumAnnual() +
            ", firstDescription='" + getFirstDescription() + "'" +
            ", secondDescription='" + getSecondDescription() + "'" +
            ", thirdDescription='" + getThirdDescription() + "'" +
            ", fourthDescription='" + getFourthDescription() + "'" +
            ", imagePath='" + getImagePath() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
