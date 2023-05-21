package com.bank.app.web.rest;

import static com.bank.app.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bank.app.IntegrationTest;
import com.bank.app.domain.CreditCardType;
import com.bank.app.repository.CreditCardTypeRepository;
import com.bank.app.service.dto.CreditCardTypeDTO;
import com.bank.app.service.mapper.CreditCardTypeMapper;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CreditCardTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CreditCardTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_MINIMUM_ANNUAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_MINIMUM_ANNUAL = new BigDecimal(2);

    private static final String DEFAULT_FIRST_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_SECOND_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_SECOND_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_THIRD_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_THIRD_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_FOURTH_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_FOURTH_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_PATH = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_PATH = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/credit-card-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CreditCardTypeRepository creditCardTypeRepository;

    @Autowired
    private CreditCardTypeMapper creditCardTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCreditCardTypeMockMvc;

    private CreditCardType creditCardType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CreditCardType createEntity(EntityManager em) {
        CreditCardType creditCardType = new CreditCardType()
            .name(DEFAULT_NAME)
            .minimumAnnual(DEFAULT_MINIMUM_ANNUAL)
            .firstDescription(DEFAULT_FIRST_DESCRIPTION)
            .secondDescription(DEFAULT_SECOND_DESCRIPTION)
            .thirdDescription(DEFAULT_THIRD_DESCRIPTION)
            .fourthDescription(DEFAULT_FOURTH_DESCRIPTION)
            .imagePath(DEFAULT_IMAGE_PATH)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return creditCardType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CreditCardType createUpdatedEntity(EntityManager em) {
        CreditCardType creditCardType = new CreditCardType()
            .name(UPDATED_NAME)
            .minimumAnnual(UPDATED_MINIMUM_ANNUAL)
            .firstDescription(UPDATED_FIRST_DESCRIPTION)
            .secondDescription(UPDATED_SECOND_DESCRIPTION)
            .thirdDescription(UPDATED_THIRD_DESCRIPTION)
            .fourthDescription(UPDATED_FOURTH_DESCRIPTION)
            .imagePath(UPDATED_IMAGE_PATH)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return creditCardType;
    }

    @BeforeEach
    public void initTest() {
        creditCardType = createEntity(em);
    }

    @Test
    @Transactional
    void createCreditCardType() throws Exception {
        int databaseSizeBeforeCreate = creditCardTypeRepository.findAll().size();
        // Create the CreditCardType
        CreditCardTypeDTO creditCardTypeDTO = creditCardTypeMapper.toDto(creditCardType);
        restCreditCardTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(creditCardTypeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CreditCardType in the database
        List<CreditCardType> creditCardTypeList = creditCardTypeRepository.findAll();
        assertThat(creditCardTypeList).hasSize(databaseSizeBeforeCreate + 1);
        CreditCardType testCreditCardType = creditCardTypeList.get(creditCardTypeList.size() - 1);
        assertThat(testCreditCardType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCreditCardType.getMinimumAnnual()).isEqualByComparingTo(DEFAULT_MINIMUM_ANNUAL);
        assertThat(testCreditCardType.getFirstDescription()).isEqualTo(DEFAULT_FIRST_DESCRIPTION);
        assertThat(testCreditCardType.getSecondDescription()).isEqualTo(DEFAULT_SECOND_DESCRIPTION);
        assertThat(testCreditCardType.getThirdDescription()).isEqualTo(DEFAULT_THIRD_DESCRIPTION);
        assertThat(testCreditCardType.getFourthDescription()).isEqualTo(DEFAULT_FOURTH_DESCRIPTION);
        assertThat(testCreditCardType.getImagePath()).isEqualTo(DEFAULT_IMAGE_PATH);
        assertThat(testCreditCardType.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testCreditCardType.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testCreditCardType.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testCreditCardType.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createCreditCardTypeWithExistingId() throws Exception {
        // Create the CreditCardType with an existing ID
        creditCardType.setId(1L);
        CreditCardTypeDTO creditCardTypeDTO = creditCardTypeMapper.toDto(creditCardType);

        int databaseSizeBeforeCreate = creditCardTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCreditCardTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(creditCardTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CreditCardType in the database
        List<CreditCardType> creditCardTypeList = creditCardTypeRepository.findAll();
        assertThat(creditCardTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCreditCardTypes() throws Exception {
        // Initialize the database
        creditCardTypeRepository.saveAndFlush(creditCardType);

        // Get all the creditCardTypeList
        restCreditCardTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(creditCardType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].minimumAnnual").value(hasItem(sameNumber(DEFAULT_MINIMUM_ANNUAL))))
            .andExpect(jsonPath("$.[*].firstDescription").value(hasItem(DEFAULT_FIRST_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].secondDescription").value(hasItem(DEFAULT_SECOND_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].thirdDescription").value(hasItem(DEFAULT_THIRD_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].fourthDescription").value(hasItem(DEFAULT_FOURTH_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].imagePath").value(hasItem(DEFAULT_IMAGE_PATH)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getCreditCardType() throws Exception {
        // Initialize the database
        creditCardTypeRepository.saveAndFlush(creditCardType);

        // Get the creditCardType
        restCreditCardTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, creditCardType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(creditCardType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.minimumAnnual").value(sameNumber(DEFAULT_MINIMUM_ANNUAL)))
            .andExpect(jsonPath("$.firstDescription").value(DEFAULT_FIRST_DESCRIPTION))
            .andExpect(jsonPath("$.secondDescription").value(DEFAULT_SECOND_DESCRIPTION))
            .andExpect(jsonPath("$.thirdDescription").value(DEFAULT_THIRD_DESCRIPTION))
            .andExpect(jsonPath("$.fourthDescription").value(DEFAULT_FOURTH_DESCRIPTION))
            .andExpect(jsonPath("$.imagePath").value(DEFAULT_IMAGE_PATH))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getNonExistingCreditCardType() throws Exception {
        // Get the creditCardType
        restCreditCardTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCreditCardType() throws Exception {
        // Initialize the database
        creditCardTypeRepository.saveAndFlush(creditCardType);

        int databaseSizeBeforeUpdate = creditCardTypeRepository.findAll().size();

        // Update the creditCardType
        CreditCardType updatedCreditCardType = creditCardTypeRepository.findById(creditCardType.getId()).get();
        // Disconnect from session so that the updates on updatedCreditCardType are not directly saved in db
        em.detach(updatedCreditCardType);
        updatedCreditCardType
            .name(UPDATED_NAME)
            .minimumAnnual(UPDATED_MINIMUM_ANNUAL)
            .firstDescription(UPDATED_FIRST_DESCRIPTION)
            .secondDescription(UPDATED_SECOND_DESCRIPTION)
            .thirdDescription(UPDATED_THIRD_DESCRIPTION)
            .fourthDescription(UPDATED_FOURTH_DESCRIPTION)
            .imagePath(UPDATED_IMAGE_PATH)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        CreditCardTypeDTO creditCardTypeDTO = creditCardTypeMapper.toDto(updatedCreditCardType);

        restCreditCardTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, creditCardTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(creditCardTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the CreditCardType in the database
        List<CreditCardType> creditCardTypeList = creditCardTypeRepository.findAll();
        assertThat(creditCardTypeList).hasSize(databaseSizeBeforeUpdate);
        CreditCardType testCreditCardType = creditCardTypeList.get(creditCardTypeList.size() - 1);
        assertThat(testCreditCardType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCreditCardType.getMinimumAnnual()).isEqualByComparingTo(UPDATED_MINIMUM_ANNUAL);
        assertThat(testCreditCardType.getFirstDescription()).isEqualTo(UPDATED_FIRST_DESCRIPTION);
        assertThat(testCreditCardType.getSecondDescription()).isEqualTo(UPDATED_SECOND_DESCRIPTION);
        assertThat(testCreditCardType.getThirdDescription()).isEqualTo(UPDATED_THIRD_DESCRIPTION);
        assertThat(testCreditCardType.getFourthDescription()).isEqualTo(UPDATED_FOURTH_DESCRIPTION);
        assertThat(testCreditCardType.getImagePath()).isEqualTo(UPDATED_IMAGE_PATH);
        assertThat(testCreditCardType.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testCreditCardType.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCreditCardType.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testCreditCardType.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingCreditCardType() throws Exception {
        int databaseSizeBeforeUpdate = creditCardTypeRepository.findAll().size();
        creditCardType.setId(count.incrementAndGet());

        // Create the CreditCardType
        CreditCardTypeDTO creditCardTypeDTO = creditCardTypeMapper.toDto(creditCardType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCreditCardTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, creditCardTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(creditCardTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CreditCardType in the database
        List<CreditCardType> creditCardTypeList = creditCardTypeRepository.findAll();
        assertThat(creditCardTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCreditCardType() throws Exception {
        int databaseSizeBeforeUpdate = creditCardTypeRepository.findAll().size();
        creditCardType.setId(count.incrementAndGet());

        // Create the CreditCardType
        CreditCardTypeDTO creditCardTypeDTO = creditCardTypeMapper.toDto(creditCardType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCreditCardTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(creditCardTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CreditCardType in the database
        List<CreditCardType> creditCardTypeList = creditCardTypeRepository.findAll();
        assertThat(creditCardTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCreditCardType() throws Exception {
        int databaseSizeBeforeUpdate = creditCardTypeRepository.findAll().size();
        creditCardType.setId(count.incrementAndGet());

        // Create the CreditCardType
        CreditCardTypeDTO creditCardTypeDTO = creditCardTypeMapper.toDto(creditCardType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCreditCardTypeMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(creditCardTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CreditCardType in the database
        List<CreditCardType> creditCardTypeList = creditCardTypeRepository.findAll();
        assertThat(creditCardTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCreditCardTypeWithPatch() throws Exception {
        // Initialize the database
        creditCardTypeRepository.saveAndFlush(creditCardType);

        int databaseSizeBeforeUpdate = creditCardTypeRepository.findAll().size();

        // Update the creditCardType using partial update
        CreditCardType partialUpdatedCreditCardType = new CreditCardType();
        partialUpdatedCreditCardType.setId(creditCardType.getId());

        partialUpdatedCreditCardType
            .name(UPDATED_NAME)
            .minimumAnnual(UPDATED_MINIMUM_ANNUAL)
            .firstDescription(UPDATED_FIRST_DESCRIPTION)
            .secondDescription(UPDATED_SECOND_DESCRIPTION)
            .thirdDescription(UPDATED_THIRD_DESCRIPTION);

        restCreditCardTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCreditCardType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCreditCardType))
            )
            .andExpect(status().isOk());

        // Validate the CreditCardType in the database
        List<CreditCardType> creditCardTypeList = creditCardTypeRepository.findAll();
        assertThat(creditCardTypeList).hasSize(databaseSizeBeforeUpdate);
        CreditCardType testCreditCardType = creditCardTypeList.get(creditCardTypeList.size() - 1);
        assertThat(testCreditCardType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCreditCardType.getMinimumAnnual()).isEqualByComparingTo(UPDATED_MINIMUM_ANNUAL);
        assertThat(testCreditCardType.getFirstDescription()).isEqualTo(UPDATED_FIRST_DESCRIPTION);
        assertThat(testCreditCardType.getSecondDescription()).isEqualTo(UPDATED_SECOND_DESCRIPTION);
        assertThat(testCreditCardType.getThirdDescription()).isEqualTo(UPDATED_THIRD_DESCRIPTION);
        assertThat(testCreditCardType.getFourthDescription()).isEqualTo(DEFAULT_FOURTH_DESCRIPTION);
        assertThat(testCreditCardType.getImagePath()).isEqualTo(DEFAULT_IMAGE_PATH);
        assertThat(testCreditCardType.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testCreditCardType.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testCreditCardType.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testCreditCardType.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateCreditCardTypeWithPatch() throws Exception {
        // Initialize the database
        creditCardTypeRepository.saveAndFlush(creditCardType);

        int databaseSizeBeforeUpdate = creditCardTypeRepository.findAll().size();

        // Update the creditCardType using partial update
        CreditCardType partialUpdatedCreditCardType = new CreditCardType();
        partialUpdatedCreditCardType.setId(creditCardType.getId());

        partialUpdatedCreditCardType
            .name(UPDATED_NAME)
            .minimumAnnual(UPDATED_MINIMUM_ANNUAL)
            .firstDescription(UPDATED_FIRST_DESCRIPTION)
            .secondDescription(UPDATED_SECOND_DESCRIPTION)
            .thirdDescription(UPDATED_THIRD_DESCRIPTION)
            .fourthDescription(UPDATED_FOURTH_DESCRIPTION)
            .imagePath(UPDATED_IMAGE_PATH)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restCreditCardTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCreditCardType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCreditCardType))
            )
            .andExpect(status().isOk());

        // Validate the CreditCardType in the database
        List<CreditCardType> creditCardTypeList = creditCardTypeRepository.findAll();
        assertThat(creditCardTypeList).hasSize(databaseSizeBeforeUpdate);
        CreditCardType testCreditCardType = creditCardTypeList.get(creditCardTypeList.size() - 1);
        assertThat(testCreditCardType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCreditCardType.getMinimumAnnual()).isEqualByComparingTo(UPDATED_MINIMUM_ANNUAL);
        assertThat(testCreditCardType.getFirstDescription()).isEqualTo(UPDATED_FIRST_DESCRIPTION);
        assertThat(testCreditCardType.getSecondDescription()).isEqualTo(UPDATED_SECOND_DESCRIPTION);
        assertThat(testCreditCardType.getThirdDescription()).isEqualTo(UPDATED_THIRD_DESCRIPTION);
        assertThat(testCreditCardType.getFourthDescription()).isEqualTo(UPDATED_FOURTH_DESCRIPTION);
        assertThat(testCreditCardType.getImagePath()).isEqualTo(UPDATED_IMAGE_PATH);
        assertThat(testCreditCardType.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testCreditCardType.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCreditCardType.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testCreditCardType.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingCreditCardType() throws Exception {
        int databaseSizeBeforeUpdate = creditCardTypeRepository.findAll().size();
        creditCardType.setId(count.incrementAndGet());

        // Create the CreditCardType
        CreditCardTypeDTO creditCardTypeDTO = creditCardTypeMapper.toDto(creditCardType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCreditCardTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, creditCardTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(creditCardTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CreditCardType in the database
        List<CreditCardType> creditCardTypeList = creditCardTypeRepository.findAll();
        assertThat(creditCardTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCreditCardType() throws Exception {
        int databaseSizeBeforeUpdate = creditCardTypeRepository.findAll().size();
        creditCardType.setId(count.incrementAndGet());

        // Create the CreditCardType
        CreditCardTypeDTO creditCardTypeDTO = creditCardTypeMapper.toDto(creditCardType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCreditCardTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(creditCardTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CreditCardType in the database
        List<CreditCardType> creditCardTypeList = creditCardTypeRepository.findAll();
        assertThat(creditCardTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCreditCardType() throws Exception {
        int databaseSizeBeforeUpdate = creditCardTypeRepository.findAll().size();
        creditCardType.setId(count.incrementAndGet());

        // Create the CreditCardType
        CreditCardTypeDTO creditCardTypeDTO = creditCardTypeMapper.toDto(creditCardType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCreditCardTypeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(creditCardTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CreditCardType in the database
        List<CreditCardType> creditCardTypeList = creditCardTypeRepository.findAll();
        assertThat(creditCardTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCreditCardType() throws Exception {
        // Initialize the database
        creditCardTypeRepository.saveAndFlush(creditCardType);

        int databaseSizeBeforeDelete = creditCardTypeRepository.findAll().size();

        // Delete the creditCardType
        restCreditCardTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, creditCardType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CreditCardType> creditCardTypeList = creditCardTypeRepository.findAll();
        assertThat(creditCardTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
