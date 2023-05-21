package com.bank.app.web.rest;

import static com.bank.app.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bank.app.IntegrationTest;
import com.bank.app.domain.CreditCardApplicant;
import com.bank.app.domain.enumeration.StandardStatus;
import com.bank.app.repository.CreditCardApplicantRepository;
import com.bank.app.service.CreditCardApplicantService;
import com.bank.app.service.dto.CreditCardApplicantDTO;
import com.bank.app.service.mapper.CreditCardApplicantMapper;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CreditCardApplicantResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CreditCardApplicantResourceIT {

    private static final StandardStatus DEFAULT_STATUS = StandardStatus.P;
    private static final StandardStatus UPDATED_STATUS = StandardStatus.A;

    private static final String DEFAULT_IC_PATH = "AAAAAAAAAA";
    private static final String UPDATED_IC_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_PAYSLIP_PATH = "AAAAAAAAAA";
    private static final String UPDATED_PAYSLIP_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_EPF_PATH = "AAAAAAAAAA";
    private static final String UPDATED_EPF_PATH = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_LIMIT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_LIMIT_AMOUNT = new BigDecimal(2);

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/credit-card-applicants";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CreditCardApplicantRepository creditCardApplicantRepository;

    @Mock
    private CreditCardApplicantRepository creditCardApplicantRepositoryMock;

    @Autowired
    private CreditCardApplicantMapper creditCardApplicantMapper;

    @Mock
    private CreditCardApplicantService creditCardApplicantServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCreditCardApplicantMockMvc;

    private CreditCardApplicant creditCardApplicant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CreditCardApplicant createEntity(EntityManager em) {
        CreditCardApplicant creditCardApplicant = new CreditCardApplicant()
            .status(DEFAULT_STATUS)
            .icPath(DEFAULT_IC_PATH)
            .payslipPath(DEFAULT_PAYSLIP_PATH)
            .epfPath(DEFAULT_EPF_PATH)
            .limitAmount(DEFAULT_LIMIT_AMOUNT)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return creditCardApplicant;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CreditCardApplicant createUpdatedEntity(EntityManager em) {
        CreditCardApplicant creditCardApplicant = new CreditCardApplicant()
            .status(UPDATED_STATUS)
            .icPath(UPDATED_IC_PATH)
            .payslipPath(UPDATED_PAYSLIP_PATH)
            .epfPath(UPDATED_EPF_PATH)
            .limitAmount(UPDATED_LIMIT_AMOUNT)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return creditCardApplicant;
    }

    @BeforeEach
    public void initTest() {
        creditCardApplicant = createEntity(em);
    }

    @Test
    @Transactional
    void createCreditCardApplicant() throws Exception {
        int databaseSizeBeforeCreate = creditCardApplicantRepository.findAll().size();
        // Create the CreditCardApplicant
        CreditCardApplicantDTO creditCardApplicantDTO = creditCardApplicantMapper.toDto(creditCardApplicant);
        restCreditCardApplicantMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(creditCardApplicantDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CreditCardApplicant in the database
        List<CreditCardApplicant> creditCardApplicantList = creditCardApplicantRepository.findAll();
        assertThat(creditCardApplicantList).hasSize(databaseSizeBeforeCreate + 1);
        CreditCardApplicant testCreditCardApplicant = creditCardApplicantList.get(creditCardApplicantList.size() - 1);
        assertThat(testCreditCardApplicant.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCreditCardApplicant.getIcPath()).isEqualTo(DEFAULT_IC_PATH);
        assertThat(testCreditCardApplicant.getPayslipPath()).isEqualTo(DEFAULT_PAYSLIP_PATH);
        assertThat(testCreditCardApplicant.getEpfPath()).isEqualTo(DEFAULT_EPF_PATH);
        assertThat(testCreditCardApplicant.getLimitAmount()).isEqualByComparingTo(DEFAULT_LIMIT_AMOUNT);
        assertThat(testCreditCardApplicant.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testCreditCardApplicant.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testCreditCardApplicant.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testCreditCardApplicant.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createCreditCardApplicantWithExistingId() throws Exception {
        // Create the CreditCardApplicant with an existing ID
        creditCardApplicant.setId(1L);
        CreditCardApplicantDTO creditCardApplicantDTO = creditCardApplicantMapper.toDto(creditCardApplicant);

        int databaseSizeBeforeCreate = creditCardApplicantRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCreditCardApplicantMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(creditCardApplicantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CreditCardApplicant in the database
        List<CreditCardApplicant> creditCardApplicantList = creditCardApplicantRepository.findAll();
        assertThat(creditCardApplicantList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCreditCardApplicants() throws Exception {
        // Initialize the database
        creditCardApplicantRepository.saveAndFlush(creditCardApplicant);

        // Get all the creditCardApplicantList
        restCreditCardApplicantMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(creditCardApplicant.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].icPath").value(hasItem(DEFAULT_IC_PATH)))
            .andExpect(jsonPath("$.[*].payslipPath").value(hasItem(DEFAULT_PAYSLIP_PATH)))
            .andExpect(jsonPath("$.[*].epfPath").value(hasItem(DEFAULT_EPF_PATH)))
            .andExpect(jsonPath("$.[*].limitAmount").value(hasItem(sameNumber(DEFAULT_LIMIT_AMOUNT))))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCreditCardApplicantsWithEagerRelationshipsIsEnabled() throws Exception {
        when(creditCardApplicantServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCreditCardApplicantMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(creditCardApplicantServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCreditCardApplicantsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(creditCardApplicantServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCreditCardApplicantMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(creditCardApplicantRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getCreditCardApplicant() throws Exception {
        // Initialize the database
        creditCardApplicantRepository.saveAndFlush(creditCardApplicant);

        // Get the creditCardApplicant
        restCreditCardApplicantMockMvc
            .perform(get(ENTITY_API_URL_ID, creditCardApplicant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(creditCardApplicant.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.icPath").value(DEFAULT_IC_PATH))
            .andExpect(jsonPath("$.payslipPath").value(DEFAULT_PAYSLIP_PATH))
            .andExpect(jsonPath("$.epfPath").value(DEFAULT_EPF_PATH))
            .andExpect(jsonPath("$.limitAmount").value(sameNumber(DEFAULT_LIMIT_AMOUNT)))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getNonExistingCreditCardApplicant() throws Exception {
        // Get the creditCardApplicant
        restCreditCardApplicantMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCreditCardApplicant() throws Exception {
        // Initialize the database
        creditCardApplicantRepository.saveAndFlush(creditCardApplicant);

        int databaseSizeBeforeUpdate = creditCardApplicantRepository.findAll().size();

        // Update the creditCardApplicant
        CreditCardApplicant updatedCreditCardApplicant = creditCardApplicantRepository.findById(creditCardApplicant.getId()).get();
        // Disconnect from session so that the updates on updatedCreditCardApplicant are not directly saved in db
        em.detach(updatedCreditCardApplicant);
        updatedCreditCardApplicant
            .status(UPDATED_STATUS)
            .icPath(UPDATED_IC_PATH)
            .payslipPath(UPDATED_PAYSLIP_PATH)
            .epfPath(UPDATED_EPF_PATH)
            .limitAmount(UPDATED_LIMIT_AMOUNT)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        CreditCardApplicantDTO creditCardApplicantDTO = creditCardApplicantMapper.toDto(updatedCreditCardApplicant);

        restCreditCardApplicantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, creditCardApplicantDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(creditCardApplicantDTO))
            )
            .andExpect(status().isOk());

        // Validate the CreditCardApplicant in the database
        List<CreditCardApplicant> creditCardApplicantList = creditCardApplicantRepository.findAll();
        assertThat(creditCardApplicantList).hasSize(databaseSizeBeforeUpdate);
        CreditCardApplicant testCreditCardApplicant = creditCardApplicantList.get(creditCardApplicantList.size() - 1);
        assertThat(testCreditCardApplicant.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCreditCardApplicant.getIcPath()).isEqualTo(UPDATED_IC_PATH);
        assertThat(testCreditCardApplicant.getPayslipPath()).isEqualTo(UPDATED_PAYSLIP_PATH);
        assertThat(testCreditCardApplicant.getEpfPath()).isEqualTo(UPDATED_EPF_PATH);
        assertThat(testCreditCardApplicant.getLimitAmount()).isEqualByComparingTo(UPDATED_LIMIT_AMOUNT);
        assertThat(testCreditCardApplicant.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testCreditCardApplicant.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCreditCardApplicant.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testCreditCardApplicant.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingCreditCardApplicant() throws Exception {
        int databaseSizeBeforeUpdate = creditCardApplicantRepository.findAll().size();
        creditCardApplicant.setId(count.incrementAndGet());

        // Create the CreditCardApplicant
        CreditCardApplicantDTO creditCardApplicantDTO = creditCardApplicantMapper.toDto(creditCardApplicant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCreditCardApplicantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, creditCardApplicantDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(creditCardApplicantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CreditCardApplicant in the database
        List<CreditCardApplicant> creditCardApplicantList = creditCardApplicantRepository.findAll();
        assertThat(creditCardApplicantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCreditCardApplicant() throws Exception {
        int databaseSizeBeforeUpdate = creditCardApplicantRepository.findAll().size();
        creditCardApplicant.setId(count.incrementAndGet());

        // Create the CreditCardApplicant
        CreditCardApplicantDTO creditCardApplicantDTO = creditCardApplicantMapper.toDto(creditCardApplicant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCreditCardApplicantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(creditCardApplicantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CreditCardApplicant in the database
        List<CreditCardApplicant> creditCardApplicantList = creditCardApplicantRepository.findAll();
        assertThat(creditCardApplicantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCreditCardApplicant() throws Exception {
        int databaseSizeBeforeUpdate = creditCardApplicantRepository.findAll().size();
        creditCardApplicant.setId(count.incrementAndGet());

        // Create the CreditCardApplicant
        CreditCardApplicantDTO creditCardApplicantDTO = creditCardApplicantMapper.toDto(creditCardApplicant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCreditCardApplicantMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(creditCardApplicantDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CreditCardApplicant in the database
        List<CreditCardApplicant> creditCardApplicantList = creditCardApplicantRepository.findAll();
        assertThat(creditCardApplicantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCreditCardApplicantWithPatch() throws Exception {
        // Initialize the database
        creditCardApplicantRepository.saveAndFlush(creditCardApplicant);

        int databaseSizeBeforeUpdate = creditCardApplicantRepository.findAll().size();

        // Update the creditCardApplicant using partial update
        CreditCardApplicant partialUpdatedCreditCardApplicant = new CreditCardApplicant();
        partialUpdatedCreditCardApplicant.setId(creditCardApplicant.getId());

        partialUpdatedCreditCardApplicant
            .icPath(UPDATED_IC_PATH)
            .payslipPath(UPDATED_PAYSLIP_PATH)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restCreditCardApplicantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCreditCardApplicant.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCreditCardApplicant))
            )
            .andExpect(status().isOk());

        // Validate the CreditCardApplicant in the database
        List<CreditCardApplicant> creditCardApplicantList = creditCardApplicantRepository.findAll();
        assertThat(creditCardApplicantList).hasSize(databaseSizeBeforeUpdate);
        CreditCardApplicant testCreditCardApplicant = creditCardApplicantList.get(creditCardApplicantList.size() - 1);
        assertThat(testCreditCardApplicant.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCreditCardApplicant.getIcPath()).isEqualTo(UPDATED_IC_PATH);
        assertThat(testCreditCardApplicant.getPayslipPath()).isEqualTo(UPDATED_PAYSLIP_PATH);
        assertThat(testCreditCardApplicant.getEpfPath()).isEqualTo(DEFAULT_EPF_PATH);
        assertThat(testCreditCardApplicant.getLimitAmount()).isEqualByComparingTo(DEFAULT_LIMIT_AMOUNT);
        assertThat(testCreditCardApplicant.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testCreditCardApplicant.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testCreditCardApplicant.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testCreditCardApplicant.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateCreditCardApplicantWithPatch() throws Exception {
        // Initialize the database
        creditCardApplicantRepository.saveAndFlush(creditCardApplicant);

        int databaseSizeBeforeUpdate = creditCardApplicantRepository.findAll().size();

        // Update the creditCardApplicant using partial update
        CreditCardApplicant partialUpdatedCreditCardApplicant = new CreditCardApplicant();
        partialUpdatedCreditCardApplicant.setId(creditCardApplicant.getId());

        partialUpdatedCreditCardApplicant
            .status(UPDATED_STATUS)
            .icPath(UPDATED_IC_PATH)
            .payslipPath(UPDATED_PAYSLIP_PATH)
            .epfPath(UPDATED_EPF_PATH)
            .limitAmount(UPDATED_LIMIT_AMOUNT)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restCreditCardApplicantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCreditCardApplicant.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCreditCardApplicant))
            )
            .andExpect(status().isOk());

        // Validate the CreditCardApplicant in the database
        List<CreditCardApplicant> creditCardApplicantList = creditCardApplicantRepository.findAll();
        assertThat(creditCardApplicantList).hasSize(databaseSizeBeforeUpdate);
        CreditCardApplicant testCreditCardApplicant = creditCardApplicantList.get(creditCardApplicantList.size() - 1);
        assertThat(testCreditCardApplicant.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCreditCardApplicant.getIcPath()).isEqualTo(UPDATED_IC_PATH);
        assertThat(testCreditCardApplicant.getPayslipPath()).isEqualTo(UPDATED_PAYSLIP_PATH);
        assertThat(testCreditCardApplicant.getEpfPath()).isEqualTo(UPDATED_EPF_PATH);
        assertThat(testCreditCardApplicant.getLimitAmount()).isEqualByComparingTo(UPDATED_LIMIT_AMOUNT);
        assertThat(testCreditCardApplicant.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testCreditCardApplicant.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCreditCardApplicant.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testCreditCardApplicant.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingCreditCardApplicant() throws Exception {
        int databaseSizeBeforeUpdate = creditCardApplicantRepository.findAll().size();
        creditCardApplicant.setId(count.incrementAndGet());

        // Create the CreditCardApplicant
        CreditCardApplicantDTO creditCardApplicantDTO = creditCardApplicantMapper.toDto(creditCardApplicant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCreditCardApplicantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, creditCardApplicantDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(creditCardApplicantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CreditCardApplicant in the database
        List<CreditCardApplicant> creditCardApplicantList = creditCardApplicantRepository.findAll();
        assertThat(creditCardApplicantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCreditCardApplicant() throws Exception {
        int databaseSizeBeforeUpdate = creditCardApplicantRepository.findAll().size();
        creditCardApplicant.setId(count.incrementAndGet());

        // Create the CreditCardApplicant
        CreditCardApplicantDTO creditCardApplicantDTO = creditCardApplicantMapper.toDto(creditCardApplicant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCreditCardApplicantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(creditCardApplicantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CreditCardApplicant in the database
        List<CreditCardApplicant> creditCardApplicantList = creditCardApplicantRepository.findAll();
        assertThat(creditCardApplicantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCreditCardApplicant() throws Exception {
        int databaseSizeBeforeUpdate = creditCardApplicantRepository.findAll().size();
        creditCardApplicant.setId(count.incrementAndGet());

        // Create the CreditCardApplicant
        CreditCardApplicantDTO creditCardApplicantDTO = creditCardApplicantMapper.toDto(creditCardApplicant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCreditCardApplicantMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(creditCardApplicantDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CreditCardApplicant in the database
        List<CreditCardApplicant> creditCardApplicantList = creditCardApplicantRepository.findAll();
        assertThat(creditCardApplicantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCreditCardApplicant() throws Exception {
        // Initialize the database
        creditCardApplicantRepository.saveAndFlush(creditCardApplicant);

        int databaseSizeBeforeDelete = creditCardApplicantRepository.findAll().size();

        // Delete the creditCardApplicant
        restCreditCardApplicantMockMvc
            .perform(delete(ENTITY_API_URL_ID, creditCardApplicant.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CreditCardApplicant> creditCardApplicantList = creditCardApplicantRepository.findAll();
        assertThat(creditCardApplicantList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
