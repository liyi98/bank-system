package com.bank.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bank.app.IntegrationTest;
import com.bank.app.domain.CreditCardHolder;
import com.bank.app.repository.CreditCardHolderRepository;
import com.bank.app.service.dto.CreditCardHolderDTO;
import com.bank.app.service.mapper.CreditCardHolderMapper;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link CreditCardHolderResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CreditCardHolderResourceIT {

    private static final Integer DEFAULT_CARD_NUMBER = 1;
    private static final Integer UPDATED_CARD_NUMBER = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_EXPIRED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EXPIRED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_SECURE_NUMBER = 1;
    private static final Integer UPDATED_SECURE_NUMBER = 2;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/credit-card-holders";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CreditCardHolderRepository creditCardHolderRepository;

    @Autowired
    private CreditCardHolderMapper creditCardHolderMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCreditCardHolderMockMvc;

    private CreditCardHolder creditCardHolder;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CreditCardHolder createEntity(EntityManager em) {
        CreditCardHolder creditCardHolder = new CreditCardHolder()
            .cardNumber(DEFAULT_CARD_NUMBER)
            .name(DEFAULT_NAME)
            .expiredDate(DEFAULT_EXPIRED_DATE)
            .secureNumber(DEFAULT_SECURE_NUMBER)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return creditCardHolder;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CreditCardHolder createUpdatedEntity(EntityManager em) {
        CreditCardHolder creditCardHolder = new CreditCardHolder()
            .cardNumber(UPDATED_CARD_NUMBER)
            .name(UPDATED_NAME)
            .expiredDate(UPDATED_EXPIRED_DATE)
            .secureNumber(UPDATED_SECURE_NUMBER)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return creditCardHolder;
    }

    @BeforeEach
    public void initTest() {
        creditCardHolder = createEntity(em);
    }

    @Test
    @Transactional
    void createCreditCardHolder() throws Exception {
        int databaseSizeBeforeCreate = creditCardHolderRepository.findAll().size();
        // Create the CreditCardHolder
        CreditCardHolderDTO creditCardHolderDTO = creditCardHolderMapper.toDto(creditCardHolder);
        restCreditCardHolderMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(creditCardHolderDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CreditCardHolder in the database
        List<CreditCardHolder> creditCardHolderList = creditCardHolderRepository.findAll();
        assertThat(creditCardHolderList).hasSize(databaseSizeBeforeCreate + 1);
        CreditCardHolder testCreditCardHolder = creditCardHolderList.get(creditCardHolderList.size() - 1);
        assertThat(testCreditCardHolder.getCardNumber()).isEqualTo(DEFAULT_CARD_NUMBER);
        assertThat(testCreditCardHolder.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCreditCardHolder.getExpiredDate()).isEqualTo(DEFAULT_EXPIRED_DATE);
        assertThat(testCreditCardHolder.getSecureNumber()).isEqualTo(DEFAULT_SECURE_NUMBER);
        assertThat(testCreditCardHolder.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testCreditCardHolder.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testCreditCardHolder.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testCreditCardHolder.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createCreditCardHolderWithExistingId() throws Exception {
        // Create the CreditCardHolder with an existing ID
        creditCardHolder.setId(1L);
        CreditCardHolderDTO creditCardHolderDTO = creditCardHolderMapper.toDto(creditCardHolder);

        int databaseSizeBeforeCreate = creditCardHolderRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCreditCardHolderMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(creditCardHolderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CreditCardHolder in the database
        List<CreditCardHolder> creditCardHolderList = creditCardHolderRepository.findAll();
        assertThat(creditCardHolderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCreditCardHolders() throws Exception {
        // Initialize the database
        creditCardHolderRepository.saveAndFlush(creditCardHolder);

        // Get all the creditCardHolderList
        restCreditCardHolderMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(creditCardHolder.getId().intValue())))
            .andExpect(jsonPath("$.[*].cardNumber").value(hasItem(DEFAULT_CARD_NUMBER)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].expiredDate").value(hasItem(DEFAULT_EXPIRED_DATE.toString())))
            .andExpect(jsonPath("$.[*].secureNumber").value(hasItem(DEFAULT_SECURE_NUMBER)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getCreditCardHolder() throws Exception {
        // Initialize the database
        creditCardHolderRepository.saveAndFlush(creditCardHolder);

        // Get the creditCardHolder
        restCreditCardHolderMockMvc
            .perform(get(ENTITY_API_URL_ID, creditCardHolder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(creditCardHolder.getId().intValue()))
            .andExpect(jsonPath("$.cardNumber").value(DEFAULT_CARD_NUMBER))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.expiredDate").value(DEFAULT_EXPIRED_DATE.toString()))
            .andExpect(jsonPath("$.secureNumber").value(DEFAULT_SECURE_NUMBER))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getNonExistingCreditCardHolder() throws Exception {
        // Get the creditCardHolder
        restCreditCardHolderMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCreditCardHolder() throws Exception {
        // Initialize the database
        creditCardHolderRepository.saveAndFlush(creditCardHolder);

        int databaseSizeBeforeUpdate = creditCardHolderRepository.findAll().size();

        // Update the creditCardHolder
        CreditCardHolder updatedCreditCardHolder = creditCardHolderRepository.findById(creditCardHolder.getId()).get();
        // Disconnect from session so that the updates on updatedCreditCardHolder are not directly saved in db
        em.detach(updatedCreditCardHolder);
        updatedCreditCardHolder
            .cardNumber(UPDATED_CARD_NUMBER)
            .name(UPDATED_NAME)
            .expiredDate(UPDATED_EXPIRED_DATE)
            .secureNumber(UPDATED_SECURE_NUMBER)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        CreditCardHolderDTO creditCardHolderDTO = creditCardHolderMapper.toDto(updatedCreditCardHolder);

        restCreditCardHolderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, creditCardHolderDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(creditCardHolderDTO))
            )
            .andExpect(status().isOk());

        // Validate the CreditCardHolder in the database
        List<CreditCardHolder> creditCardHolderList = creditCardHolderRepository.findAll();
        assertThat(creditCardHolderList).hasSize(databaseSizeBeforeUpdate);
        CreditCardHolder testCreditCardHolder = creditCardHolderList.get(creditCardHolderList.size() - 1);
        assertThat(testCreditCardHolder.getCardNumber()).isEqualTo(UPDATED_CARD_NUMBER);
        assertThat(testCreditCardHolder.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCreditCardHolder.getExpiredDate()).isEqualTo(UPDATED_EXPIRED_DATE);
        assertThat(testCreditCardHolder.getSecureNumber()).isEqualTo(UPDATED_SECURE_NUMBER);
        assertThat(testCreditCardHolder.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testCreditCardHolder.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCreditCardHolder.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testCreditCardHolder.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingCreditCardHolder() throws Exception {
        int databaseSizeBeforeUpdate = creditCardHolderRepository.findAll().size();
        creditCardHolder.setId(count.incrementAndGet());

        // Create the CreditCardHolder
        CreditCardHolderDTO creditCardHolderDTO = creditCardHolderMapper.toDto(creditCardHolder);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCreditCardHolderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, creditCardHolderDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(creditCardHolderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CreditCardHolder in the database
        List<CreditCardHolder> creditCardHolderList = creditCardHolderRepository.findAll();
        assertThat(creditCardHolderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCreditCardHolder() throws Exception {
        int databaseSizeBeforeUpdate = creditCardHolderRepository.findAll().size();
        creditCardHolder.setId(count.incrementAndGet());

        // Create the CreditCardHolder
        CreditCardHolderDTO creditCardHolderDTO = creditCardHolderMapper.toDto(creditCardHolder);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCreditCardHolderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(creditCardHolderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CreditCardHolder in the database
        List<CreditCardHolder> creditCardHolderList = creditCardHolderRepository.findAll();
        assertThat(creditCardHolderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCreditCardHolder() throws Exception {
        int databaseSizeBeforeUpdate = creditCardHolderRepository.findAll().size();
        creditCardHolder.setId(count.incrementAndGet());

        // Create the CreditCardHolder
        CreditCardHolderDTO creditCardHolderDTO = creditCardHolderMapper.toDto(creditCardHolder);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCreditCardHolderMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(creditCardHolderDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CreditCardHolder in the database
        List<CreditCardHolder> creditCardHolderList = creditCardHolderRepository.findAll();
        assertThat(creditCardHolderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCreditCardHolderWithPatch() throws Exception {
        // Initialize the database
        creditCardHolderRepository.saveAndFlush(creditCardHolder);

        int databaseSizeBeforeUpdate = creditCardHolderRepository.findAll().size();

        // Update the creditCardHolder using partial update
        CreditCardHolder partialUpdatedCreditCardHolder = new CreditCardHolder();
        partialUpdatedCreditCardHolder.setId(creditCardHolder.getId());

        partialUpdatedCreditCardHolder
            .name(UPDATED_NAME)
            .secureNumber(UPDATED_SECURE_NUMBER)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restCreditCardHolderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCreditCardHolder.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCreditCardHolder))
            )
            .andExpect(status().isOk());

        // Validate the CreditCardHolder in the database
        List<CreditCardHolder> creditCardHolderList = creditCardHolderRepository.findAll();
        assertThat(creditCardHolderList).hasSize(databaseSizeBeforeUpdate);
        CreditCardHolder testCreditCardHolder = creditCardHolderList.get(creditCardHolderList.size() - 1);
        assertThat(testCreditCardHolder.getCardNumber()).isEqualTo(DEFAULT_CARD_NUMBER);
        assertThat(testCreditCardHolder.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCreditCardHolder.getExpiredDate()).isEqualTo(DEFAULT_EXPIRED_DATE);
        assertThat(testCreditCardHolder.getSecureNumber()).isEqualTo(UPDATED_SECURE_NUMBER);
        assertThat(testCreditCardHolder.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testCreditCardHolder.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCreditCardHolder.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testCreditCardHolder.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateCreditCardHolderWithPatch() throws Exception {
        // Initialize the database
        creditCardHolderRepository.saveAndFlush(creditCardHolder);

        int databaseSizeBeforeUpdate = creditCardHolderRepository.findAll().size();

        // Update the creditCardHolder using partial update
        CreditCardHolder partialUpdatedCreditCardHolder = new CreditCardHolder();
        partialUpdatedCreditCardHolder.setId(creditCardHolder.getId());

        partialUpdatedCreditCardHolder
            .cardNumber(UPDATED_CARD_NUMBER)
            .name(UPDATED_NAME)
            .expiredDate(UPDATED_EXPIRED_DATE)
            .secureNumber(UPDATED_SECURE_NUMBER)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restCreditCardHolderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCreditCardHolder.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCreditCardHolder))
            )
            .andExpect(status().isOk());

        // Validate the CreditCardHolder in the database
        List<CreditCardHolder> creditCardHolderList = creditCardHolderRepository.findAll();
        assertThat(creditCardHolderList).hasSize(databaseSizeBeforeUpdate);
        CreditCardHolder testCreditCardHolder = creditCardHolderList.get(creditCardHolderList.size() - 1);
        assertThat(testCreditCardHolder.getCardNumber()).isEqualTo(UPDATED_CARD_NUMBER);
        assertThat(testCreditCardHolder.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCreditCardHolder.getExpiredDate()).isEqualTo(UPDATED_EXPIRED_DATE);
        assertThat(testCreditCardHolder.getSecureNumber()).isEqualTo(UPDATED_SECURE_NUMBER);
        assertThat(testCreditCardHolder.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testCreditCardHolder.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCreditCardHolder.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testCreditCardHolder.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingCreditCardHolder() throws Exception {
        int databaseSizeBeforeUpdate = creditCardHolderRepository.findAll().size();
        creditCardHolder.setId(count.incrementAndGet());

        // Create the CreditCardHolder
        CreditCardHolderDTO creditCardHolderDTO = creditCardHolderMapper.toDto(creditCardHolder);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCreditCardHolderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, creditCardHolderDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(creditCardHolderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CreditCardHolder in the database
        List<CreditCardHolder> creditCardHolderList = creditCardHolderRepository.findAll();
        assertThat(creditCardHolderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCreditCardHolder() throws Exception {
        int databaseSizeBeforeUpdate = creditCardHolderRepository.findAll().size();
        creditCardHolder.setId(count.incrementAndGet());

        // Create the CreditCardHolder
        CreditCardHolderDTO creditCardHolderDTO = creditCardHolderMapper.toDto(creditCardHolder);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCreditCardHolderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(creditCardHolderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CreditCardHolder in the database
        List<CreditCardHolder> creditCardHolderList = creditCardHolderRepository.findAll();
        assertThat(creditCardHolderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCreditCardHolder() throws Exception {
        int databaseSizeBeforeUpdate = creditCardHolderRepository.findAll().size();
        creditCardHolder.setId(count.incrementAndGet());

        // Create the CreditCardHolder
        CreditCardHolderDTO creditCardHolderDTO = creditCardHolderMapper.toDto(creditCardHolder);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCreditCardHolderMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(creditCardHolderDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CreditCardHolder in the database
        List<CreditCardHolder> creditCardHolderList = creditCardHolderRepository.findAll();
        assertThat(creditCardHolderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCreditCardHolder() throws Exception {
        // Initialize the database
        creditCardHolderRepository.saveAndFlush(creditCardHolder);

        int databaseSizeBeforeDelete = creditCardHolderRepository.findAll().size();

        // Delete the creditCardHolder
        restCreditCardHolderMockMvc
            .perform(delete(ENTITY_API_URL_ID, creditCardHolder.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CreditCardHolder> creditCardHolderList = creditCardHolderRepository.findAll();
        assertThat(creditCardHolderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
