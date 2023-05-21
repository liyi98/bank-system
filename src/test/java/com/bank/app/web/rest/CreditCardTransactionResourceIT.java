package com.bank.app.web.rest;

import static com.bank.app.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bank.app.IntegrationTest;
import com.bank.app.domain.CreditCardTransaction;
import com.bank.app.repository.CreditCardTransactionRepository;
import com.bank.app.service.CreditCardTransactionService;
import com.bank.app.service.dto.CreditCardTransactionDTO;
import com.bank.app.service.mapper.CreditCardTransactionMapper;
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
 * Integration tests for the {@link CreditCardTransactionResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CreditCardTransactionResourceIT {

    private static final BigDecimal DEFAULT_BALANCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_BALANCE = new BigDecimal(2);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/credit-card-transactions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CreditCardTransactionRepository creditCardTransactionRepository;

    @Mock
    private CreditCardTransactionRepository creditCardTransactionRepositoryMock;

    @Autowired
    private CreditCardTransactionMapper creditCardTransactionMapper;

    @Mock
    private CreditCardTransactionService creditCardTransactionServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCreditCardTransactionMockMvc;

    private CreditCardTransaction creditCardTransaction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CreditCardTransaction createEntity(EntityManager em) {
        CreditCardTransaction creditCardTransaction = new CreditCardTransaction()
            .balance(DEFAULT_BALANCE)
            .description(DEFAULT_DESCRIPTION)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return creditCardTransaction;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CreditCardTransaction createUpdatedEntity(EntityManager em) {
        CreditCardTransaction creditCardTransaction = new CreditCardTransaction()
            .balance(UPDATED_BALANCE)
            .description(UPDATED_DESCRIPTION)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return creditCardTransaction;
    }

    @BeforeEach
    public void initTest() {
        creditCardTransaction = createEntity(em);
    }

    @Test
    @Transactional
    void createCreditCardTransaction() throws Exception {
        int databaseSizeBeforeCreate = creditCardTransactionRepository.findAll().size();
        // Create the CreditCardTransaction
        CreditCardTransactionDTO creditCardTransactionDTO = creditCardTransactionMapper.toDto(creditCardTransaction);
        restCreditCardTransactionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(creditCardTransactionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CreditCardTransaction in the database
        List<CreditCardTransaction> creditCardTransactionList = creditCardTransactionRepository.findAll();
        assertThat(creditCardTransactionList).hasSize(databaseSizeBeforeCreate + 1);
        CreditCardTransaction testCreditCardTransaction = creditCardTransactionList.get(creditCardTransactionList.size() - 1);
        assertThat(testCreditCardTransaction.getBalance()).isEqualByComparingTo(DEFAULT_BALANCE);
        assertThat(testCreditCardTransaction.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCreditCardTransaction.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testCreditCardTransaction.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testCreditCardTransaction.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testCreditCardTransaction.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createCreditCardTransactionWithExistingId() throws Exception {
        // Create the CreditCardTransaction with an existing ID
        creditCardTransaction.setId(1L);
        CreditCardTransactionDTO creditCardTransactionDTO = creditCardTransactionMapper.toDto(creditCardTransaction);

        int databaseSizeBeforeCreate = creditCardTransactionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCreditCardTransactionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(creditCardTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CreditCardTransaction in the database
        List<CreditCardTransaction> creditCardTransactionList = creditCardTransactionRepository.findAll();
        assertThat(creditCardTransactionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCreditCardTransactions() throws Exception {
        // Initialize the database
        creditCardTransactionRepository.saveAndFlush(creditCardTransaction);

        // Get all the creditCardTransactionList
        restCreditCardTransactionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(creditCardTransaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(sameNumber(DEFAULT_BALANCE))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCreditCardTransactionsWithEagerRelationshipsIsEnabled() throws Exception {
        when(creditCardTransactionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCreditCardTransactionMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(creditCardTransactionServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCreditCardTransactionsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(creditCardTransactionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCreditCardTransactionMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(creditCardTransactionRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getCreditCardTransaction() throws Exception {
        // Initialize the database
        creditCardTransactionRepository.saveAndFlush(creditCardTransaction);

        // Get the creditCardTransaction
        restCreditCardTransactionMockMvc
            .perform(get(ENTITY_API_URL_ID, creditCardTransaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(creditCardTransaction.getId().intValue()))
            .andExpect(jsonPath("$.balance").value(sameNumber(DEFAULT_BALANCE)))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getNonExistingCreditCardTransaction() throws Exception {
        // Get the creditCardTransaction
        restCreditCardTransactionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCreditCardTransaction() throws Exception {
        // Initialize the database
        creditCardTransactionRepository.saveAndFlush(creditCardTransaction);

        int databaseSizeBeforeUpdate = creditCardTransactionRepository.findAll().size();

        // Update the creditCardTransaction
        CreditCardTransaction updatedCreditCardTransaction = creditCardTransactionRepository.findById(creditCardTransaction.getId()).get();
        // Disconnect from session so that the updates on updatedCreditCardTransaction are not directly saved in db
        em.detach(updatedCreditCardTransaction);
        updatedCreditCardTransaction
            .balance(UPDATED_BALANCE)
            .description(UPDATED_DESCRIPTION)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        CreditCardTransactionDTO creditCardTransactionDTO = creditCardTransactionMapper.toDto(updatedCreditCardTransaction);

        restCreditCardTransactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, creditCardTransactionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(creditCardTransactionDTO))
            )
            .andExpect(status().isOk());

        // Validate the CreditCardTransaction in the database
        List<CreditCardTransaction> creditCardTransactionList = creditCardTransactionRepository.findAll();
        assertThat(creditCardTransactionList).hasSize(databaseSizeBeforeUpdate);
        CreditCardTransaction testCreditCardTransaction = creditCardTransactionList.get(creditCardTransactionList.size() - 1);
        assertThat(testCreditCardTransaction.getBalance()).isEqualByComparingTo(UPDATED_BALANCE);
        assertThat(testCreditCardTransaction.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCreditCardTransaction.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testCreditCardTransaction.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCreditCardTransaction.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testCreditCardTransaction.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingCreditCardTransaction() throws Exception {
        int databaseSizeBeforeUpdate = creditCardTransactionRepository.findAll().size();
        creditCardTransaction.setId(count.incrementAndGet());

        // Create the CreditCardTransaction
        CreditCardTransactionDTO creditCardTransactionDTO = creditCardTransactionMapper.toDto(creditCardTransaction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCreditCardTransactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, creditCardTransactionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(creditCardTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CreditCardTransaction in the database
        List<CreditCardTransaction> creditCardTransactionList = creditCardTransactionRepository.findAll();
        assertThat(creditCardTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCreditCardTransaction() throws Exception {
        int databaseSizeBeforeUpdate = creditCardTransactionRepository.findAll().size();
        creditCardTransaction.setId(count.incrementAndGet());

        // Create the CreditCardTransaction
        CreditCardTransactionDTO creditCardTransactionDTO = creditCardTransactionMapper.toDto(creditCardTransaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCreditCardTransactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(creditCardTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CreditCardTransaction in the database
        List<CreditCardTransaction> creditCardTransactionList = creditCardTransactionRepository.findAll();
        assertThat(creditCardTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCreditCardTransaction() throws Exception {
        int databaseSizeBeforeUpdate = creditCardTransactionRepository.findAll().size();
        creditCardTransaction.setId(count.incrementAndGet());

        // Create the CreditCardTransaction
        CreditCardTransactionDTO creditCardTransactionDTO = creditCardTransactionMapper.toDto(creditCardTransaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCreditCardTransactionMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(creditCardTransactionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CreditCardTransaction in the database
        List<CreditCardTransaction> creditCardTransactionList = creditCardTransactionRepository.findAll();
        assertThat(creditCardTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCreditCardTransactionWithPatch() throws Exception {
        // Initialize the database
        creditCardTransactionRepository.saveAndFlush(creditCardTransaction);

        int databaseSizeBeforeUpdate = creditCardTransactionRepository.findAll().size();

        // Update the creditCardTransaction using partial update
        CreditCardTransaction partialUpdatedCreditCardTransaction = new CreditCardTransaction();
        partialUpdatedCreditCardTransaction.setId(creditCardTransaction.getId());

        partialUpdatedCreditCardTransaction
            .balance(UPDATED_BALANCE)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restCreditCardTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCreditCardTransaction.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCreditCardTransaction))
            )
            .andExpect(status().isOk());

        // Validate the CreditCardTransaction in the database
        List<CreditCardTransaction> creditCardTransactionList = creditCardTransactionRepository.findAll();
        assertThat(creditCardTransactionList).hasSize(databaseSizeBeforeUpdate);
        CreditCardTransaction testCreditCardTransaction = creditCardTransactionList.get(creditCardTransactionList.size() - 1);
        assertThat(testCreditCardTransaction.getBalance()).isEqualByComparingTo(UPDATED_BALANCE);
        assertThat(testCreditCardTransaction.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCreditCardTransaction.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testCreditCardTransaction.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCreditCardTransaction.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testCreditCardTransaction.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateCreditCardTransactionWithPatch() throws Exception {
        // Initialize the database
        creditCardTransactionRepository.saveAndFlush(creditCardTransaction);

        int databaseSizeBeforeUpdate = creditCardTransactionRepository.findAll().size();

        // Update the creditCardTransaction using partial update
        CreditCardTransaction partialUpdatedCreditCardTransaction = new CreditCardTransaction();
        partialUpdatedCreditCardTransaction.setId(creditCardTransaction.getId());

        partialUpdatedCreditCardTransaction
            .balance(UPDATED_BALANCE)
            .description(UPDATED_DESCRIPTION)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restCreditCardTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCreditCardTransaction.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCreditCardTransaction))
            )
            .andExpect(status().isOk());

        // Validate the CreditCardTransaction in the database
        List<CreditCardTransaction> creditCardTransactionList = creditCardTransactionRepository.findAll();
        assertThat(creditCardTransactionList).hasSize(databaseSizeBeforeUpdate);
        CreditCardTransaction testCreditCardTransaction = creditCardTransactionList.get(creditCardTransactionList.size() - 1);
        assertThat(testCreditCardTransaction.getBalance()).isEqualByComparingTo(UPDATED_BALANCE);
        assertThat(testCreditCardTransaction.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCreditCardTransaction.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testCreditCardTransaction.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCreditCardTransaction.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testCreditCardTransaction.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingCreditCardTransaction() throws Exception {
        int databaseSizeBeforeUpdate = creditCardTransactionRepository.findAll().size();
        creditCardTransaction.setId(count.incrementAndGet());

        // Create the CreditCardTransaction
        CreditCardTransactionDTO creditCardTransactionDTO = creditCardTransactionMapper.toDto(creditCardTransaction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCreditCardTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, creditCardTransactionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(creditCardTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CreditCardTransaction in the database
        List<CreditCardTransaction> creditCardTransactionList = creditCardTransactionRepository.findAll();
        assertThat(creditCardTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCreditCardTransaction() throws Exception {
        int databaseSizeBeforeUpdate = creditCardTransactionRepository.findAll().size();
        creditCardTransaction.setId(count.incrementAndGet());

        // Create the CreditCardTransaction
        CreditCardTransactionDTO creditCardTransactionDTO = creditCardTransactionMapper.toDto(creditCardTransaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCreditCardTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(creditCardTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CreditCardTransaction in the database
        List<CreditCardTransaction> creditCardTransactionList = creditCardTransactionRepository.findAll();
        assertThat(creditCardTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCreditCardTransaction() throws Exception {
        int databaseSizeBeforeUpdate = creditCardTransactionRepository.findAll().size();
        creditCardTransaction.setId(count.incrementAndGet());

        // Create the CreditCardTransaction
        CreditCardTransactionDTO creditCardTransactionDTO = creditCardTransactionMapper.toDto(creditCardTransaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCreditCardTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(creditCardTransactionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CreditCardTransaction in the database
        List<CreditCardTransaction> creditCardTransactionList = creditCardTransactionRepository.findAll();
        assertThat(creditCardTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCreditCardTransaction() throws Exception {
        // Initialize the database
        creditCardTransactionRepository.saveAndFlush(creditCardTransaction);

        int databaseSizeBeforeDelete = creditCardTransactionRepository.findAll().size();

        // Delete the creditCardTransaction
        restCreditCardTransactionMockMvc
            .perform(delete(ENTITY_API_URL_ID, creditCardTransaction.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CreditCardTransaction> creditCardTransactionList = creditCardTransactionRepository.findAll();
        assertThat(creditCardTransactionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
