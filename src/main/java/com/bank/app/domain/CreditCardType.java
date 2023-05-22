package com.bank.app.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CreditCardType.
 */
@Entity
@Table(name = "credit_card_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CreditCardType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "minimum_annual", precision = 21, scale = 2)
    private BigDecimal minimumAnnual;

    @Column(name = "first_description")
    private String firstDescription;

    @Column(name = "second_description")
    private String secondDescription;

    @Column(name = "third_description")
    private String thirdDescription;

    @Column(name = "fourth_description")
    private String fourthDescription;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CreditCardType id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public CreditCardType name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getMinimumAnnual() {
        return this.minimumAnnual;
    }

    public CreditCardType minimumAnnual(BigDecimal minimumAnnual) {
        this.setMinimumAnnual(minimumAnnual);
        return this;
    }

    public void setMinimumAnnual(BigDecimal minimumAnnual) {
        this.minimumAnnual = minimumAnnual;
    }

    public String getFirstDescription() {
        return this.firstDescription;
    }

    public CreditCardType firstDescription(String firstDescription) {
        this.setFirstDescription(firstDescription);
        return this;
    }

    public void setFirstDescription(String firstDescription) {
        this.firstDescription = firstDescription;
    }

    public String getSecondDescription() {
        return this.secondDescription;
    }

    public CreditCardType secondDescription(String secondDescription) {
        this.setSecondDescription(secondDescription);
        return this;
    }

    public void setSecondDescription(String secondDescription) {
        this.secondDescription = secondDescription;
    }

    public String getThirdDescription() {
        return this.thirdDescription;
    }

    public CreditCardType thirdDescription(String thirdDescription) {
        this.setThirdDescription(thirdDescription);
        return this;
    }

    public void setThirdDescription(String thirdDescription) {
        this.thirdDescription = thirdDescription;
    }

    public String getFourthDescription() {
        return this.fourthDescription;
    }

    public CreditCardType fourthDescription(String fourthDescription) {
        this.setFourthDescription(fourthDescription);
        return this;
    }

    public void setFourthDescription(String fourthDescription) {
        this.fourthDescription = fourthDescription;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public CreditCardType imagePath(String imagePath) {
        this.setImagePath(imagePath);
        return this;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public CreditCardType createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public CreditCardType createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public CreditCardType lastModifiedDate(Instant lastModifiedDate) {
        this.setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public CreditCardType lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreditCardType)) {
            return false;
        }
        return id != null && id.equals(((CreditCardType) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CreditCardType{" +
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
