package com.bank.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bank.app.IntegrationTest;
import com.bank.app.domain.BankUser;
import com.bank.app.domain.enumeration.UserStatus;
import com.bank.app.repository.BankUserRepository;
import com.bank.app.service.dto.BankUserDTO;
import com.bank.app.service.mapper.BankUserMapper;
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
 * Integration tests for the {@link BankUserResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BankUserResourceIT {

    private static final String DEFAULT_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final UserStatus DEFAULT_STATUS = UserStatus.A;
    private static final UserStatus UPDATED_STATUS = UserStatus.I;

    private static final LocalDate DEFAULT_DOB = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DOB = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_IC = "AAAAAAAAAA";
    private static final String UPDATED_IC = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_POST_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POST_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_REFERRAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_REFERRAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PROFILE_IMAGE_PATH = "AAAAAAAAAA";
    private static final String UPDATED_PROFILE_IMAGE_PATH = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/bank-users";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BankUserRepository bankUserRepository;

    @Autowired
    private BankUserMapper bankUserMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBankUserMockMvc;

    private BankUser bankUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankUser createEntity(EntityManager em) {
        BankUser bankUser = new BankUser()
            .login(DEFAULT_LOGIN)
            .password(DEFAULT_PASSWORD)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .status(DEFAULT_STATUS)
            .dob(DEFAULT_DOB)
            .ic(DEFAULT_IC)
            .email(DEFAULT_EMAIL)
            .phone(DEFAULT_PHONE)
            .address(DEFAULT_ADDRESS)
            .postCode(DEFAULT_POST_CODE)
            .referralCode(DEFAULT_REFERRAL_CODE)
            .profileImagePath(DEFAULT_PROFILE_IMAGE_PATH)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return bankUser;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankUser createUpdatedEntity(EntityManager em) {
        BankUser bankUser = new BankUser()
            .login(UPDATED_LOGIN)
            .password(UPDATED_PASSWORD)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .status(UPDATED_STATUS)
            .dob(UPDATED_DOB)
            .ic(UPDATED_IC)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .address(UPDATED_ADDRESS)
            .postCode(UPDATED_POST_CODE)
            .referralCode(UPDATED_REFERRAL_CODE)
            .profileImagePath(UPDATED_PROFILE_IMAGE_PATH)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return bankUser;
    }

    @BeforeEach
    public void initTest() {
        bankUser = createEntity(em);
    }

    @Test
    @Transactional
    void createBankUser() throws Exception {
        int databaseSizeBeforeCreate = bankUserRepository.findAll().size();
        // Create the BankUser
        BankUserDTO bankUserDTO = bankUserMapper.toDto(bankUser);
        restBankUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankUserDTO)))
            .andExpect(status().isCreated());

        // Validate the BankUser in the database
        List<BankUser> bankUserList = bankUserRepository.findAll();
        assertThat(bankUserList).hasSize(databaseSizeBeforeCreate + 1);
        BankUser testBankUser = bankUserList.get(bankUserList.size() - 1);
        assertThat(testBankUser.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(testBankUser.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testBankUser.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testBankUser.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testBankUser.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testBankUser.getDob()).isEqualTo(DEFAULT_DOB);
        assertThat(testBankUser.getIc()).isEqualTo(DEFAULT_IC);
        assertThat(testBankUser.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testBankUser.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testBankUser.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testBankUser.getPostCode()).isEqualTo(DEFAULT_POST_CODE);
        assertThat(testBankUser.getReferralCode()).isEqualTo(DEFAULT_REFERRAL_CODE);
        assertThat(testBankUser.getProfileImagePath()).isEqualTo(DEFAULT_PROFILE_IMAGE_PATH);
        assertThat(testBankUser.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testBankUser.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testBankUser.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testBankUser.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createBankUserWithExistingId() throws Exception {
        // Create the BankUser with an existing ID
        bankUser.setId(1L);
        BankUserDTO bankUserDTO = bankUserMapper.toDto(bankUser);

        int databaseSizeBeforeCreate = bankUserRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBankUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BankUser in the database
        List<BankUser> bankUserList = bankUserRepository.findAll();
        assertThat(bankUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankUserRepository.findAll().size();
        // set the field null
        bankUser.setFirstName(null);

        // Create the BankUser, which fails.
        BankUserDTO bankUserDTO = bankUserMapper.toDto(bankUser);

        restBankUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankUserDTO)))
            .andExpect(status().isBadRequest());

        List<BankUser> bankUserList = bankUserRepository.findAll();
        assertThat(bankUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankUserRepository.findAll().size();
        // set the field null
        bankUser.setLastName(null);

        // Create the BankUser, which fails.
        BankUserDTO bankUserDTO = bankUserMapper.toDto(bankUser);

        restBankUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankUserDTO)))
            .andExpect(status().isBadRequest());

        List<BankUser> bankUserList = bankUserRepository.findAll();
        assertThat(bankUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBankUsers() throws Exception {
        // Initialize the database
        bankUserRepository.saveAndFlush(bankUser);

        // Get all the bankUserList
        restBankUserMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bankUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].dob").value(hasItem(DEFAULT_DOB.toString())))
            .andExpect(jsonPath("$.[*].ic").value(hasItem(DEFAULT_IC)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].postCode").value(hasItem(DEFAULT_POST_CODE)))
            .andExpect(jsonPath("$.[*].referralCode").value(hasItem(DEFAULT_REFERRAL_CODE)))
            .andExpect(jsonPath("$.[*].profileImagePath").value(hasItem(DEFAULT_PROFILE_IMAGE_PATH)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getBankUser() throws Exception {
        // Initialize the database
        bankUserRepository.saveAndFlush(bankUser);

        // Get the bankUser
        restBankUserMockMvc
            .perform(get(ENTITY_API_URL_ID, bankUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bankUser.getId().intValue()))
            .andExpect(jsonPath("$.login").value(DEFAULT_LOGIN))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.dob").value(DEFAULT_DOB.toString()))
            .andExpect(jsonPath("$.ic").value(DEFAULT_IC))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.postCode").value(DEFAULT_POST_CODE))
            .andExpect(jsonPath("$.referralCode").value(DEFAULT_REFERRAL_CODE))
            .andExpect(jsonPath("$.profileImagePath").value(DEFAULT_PROFILE_IMAGE_PATH))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getNonExistingBankUser() throws Exception {
        // Get the bankUser
        restBankUserMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBankUser() throws Exception {
        // Initialize the database
        bankUserRepository.saveAndFlush(bankUser);

        int databaseSizeBeforeUpdate = bankUserRepository.findAll().size();

        // Update the bankUser
        BankUser updatedBankUser = bankUserRepository.findById(bankUser.getId()).get();
        // Disconnect from session so that the updates on updatedBankUser are not directly saved in db
        em.detach(updatedBankUser);
        updatedBankUser
            .login(UPDATED_LOGIN)
            .password(UPDATED_PASSWORD)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .status(UPDATED_STATUS)
            .dob(UPDATED_DOB)
            .ic(UPDATED_IC)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .address(UPDATED_ADDRESS)
            .postCode(UPDATED_POST_CODE)
            .referralCode(UPDATED_REFERRAL_CODE)
            .profileImagePath(UPDATED_PROFILE_IMAGE_PATH)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        BankUserDTO bankUserDTO = bankUserMapper.toDto(updatedBankUser);

        restBankUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankUserDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankUserDTO))
            )
            .andExpect(status().isOk());

        // Validate the BankUser in the database
        List<BankUser> bankUserList = bankUserRepository.findAll();
        assertThat(bankUserList).hasSize(databaseSizeBeforeUpdate);
        BankUser testBankUser = bankUserList.get(bankUserList.size() - 1);
        assertThat(testBankUser.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testBankUser.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testBankUser.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testBankUser.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testBankUser.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testBankUser.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testBankUser.getIc()).isEqualTo(UPDATED_IC);
        assertThat(testBankUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testBankUser.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testBankUser.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testBankUser.getPostCode()).isEqualTo(UPDATED_POST_CODE);
        assertThat(testBankUser.getReferralCode()).isEqualTo(UPDATED_REFERRAL_CODE);
        assertThat(testBankUser.getProfileImagePath()).isEqualTo(UPDATED_PROFILE_IMAGE_PATH);
        assertThat(testBankUser.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testBankUser.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testBankUser.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testBankUser.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingBankUser() throws Exception {
        int databaseSizeBeforeUpdate = bankUserRepository.findAll().size();
        bankUser.setId(count.incrementAndGet());

        // Create the BankUser
        BankUserDTO bankUserDTO = bankUserMapper.toDto(bankUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankUserDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankUser in the database
        List<BankUser> bankUserList = bankUserRepository.findAll();
        assertThat(bankUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBankUser() throws Exception {
        int databaseSizeBeforeUpdate = bankUserRepository.findAll().size();
        bankUser.setId(count.incrementAndGet());

        // Create the BankUser
        BankUserDTO bankUserDTO = bankUserMapper.toDto(bankUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankUser in the database
        List<BankUser> bankUserList = bankUserRepository.findAll();
        assertThat(bankUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBankUser() throws Exception {
        int databaseSizeBeforeUpdate = bankUserRepository.findAll().size();
        bankUser.setId(count.incrementAndGet());

        // Create the BankUser
        BankUserDTO bankUserDTO = bankUserMapper.toDto(bankUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankUserMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankUserDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankUser in the database
        List<BankUser> bankUserList = bankUserRepository.findAll();
        assertThat(bankUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBankUserWithPatch() throws Exception {
        // Initialize the database
        bankUserRepository.saveAndFlush(bankUser);

        int databaseSizeBeforeUpdate = bankUserRepository.findAll().size();

        // Update the bankUser using partial update
        BankUser partialUpdatedBankUser = new BankUser();
        partialUpdatedBankUser.setId(bankUser.getId());

        partialUpdatedBankUser
            .login(UPDATED_LOGIN)
            .password(UPDATED_PASSWORD)
            .firstName(UPDATED_FIRST_NAME)
            .status(UPDATED_STATUS)
            .ic(UPDATED_IC)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .postCode(UPDATED_POST_CODE);

        restBankUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBankUser))
            )
            .andExpect(status().isOk());

        // Validate the BankUser in the database
        List<BankUser> bankUserList = bankUserRepository.findAll();
        assertThat(bankUserList).hasSize(databaseSizeBeforeUpdate);
        BankUser testBankUser = bankUserList.get(bankUserList.size() - 1);
        assertThat(testBankUser.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testBankUser.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testBankUser.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testBankUser.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testBankUser.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testBankUser.getDob()).isEqualTo(DEFAULT_DOB);
        assertThat(testBankUser.getIc()).isEqualTo(UPDATED_IC);
        assertThat(testBankUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testBankUser.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testBankUser.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testBankUser.getPostCode()).isEqualTo(UPDATED_POST_CODE);
        assertThat(testBankUser.getReferralCode()).isEqualTo(DEFAULT_REFERRAL_CODE);
        assertThat(testBankUser.getProfileImagePath()).isEqualTo(DEFAULT_PROFILE_IMAGE_PATH);
        assertThat(testBankUser.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testBankUser.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testBankUser.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testBankUser.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateBankUserWithPatch() throws Exception {
        // Initialize the database
        bankUserRepository.saveAndFlush(bankUser);

        int databaseSizeBeforeUpdate = bankUserRepository.findAll().size();

        // Update the bankUser using partial update
        BankUser partialUpdatedBankUser = new BankUser();
        partialUpdatedBankUser.setId(bankUser.getId());

        partialUpdatedBankUser
            .login(UPDATED_LOGIN)
            .password(UPDATED_PASSWORD)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .status(UPDATED_STATUS)
            .dob(UPDATED_DOB)
            .ic(UPDATED_IC)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .address(UPDATED_ADDRESS)
            .postCode(UPDATED_POST_CODE)
            .referralCode(UPDATED_REFERRAL_CODE)
            .profileImagePath(UPDATED_PROFILE_IMAGE_PATH)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restBankUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBankUser))
            )
            .andExpect(status().isOk());

        // Validate the BankUser in the database
        List<BankUser> bankUserList = bankUserRepository.findAll();
        assertThat(bankUserList).hasSize(databaseSizeBeforeUpdate);
        BankUser testBankUser = bankUserList.get(bankUserList.size() - 1);
        assertThat(testBankUser.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testBankUser.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testBankUser.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testBankUser.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testBankUser.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testBankUser.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testBankUser.getIc()).isEqualTo(UPDATED_IC);
        assertThat(testBankUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testBankUser.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testBankUser.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testBankUser.getPostCode()).isEqualTo(UPDATED_POST_CODE);
        assertThat(testBankUser.getReferralCode()).isEqualTo(UPDATED_REFERRAL_CODE);
        assertThat(testBankUser.getProfileImagePath()).isEqualTo(UPDATED_PROFILE_IMAGE_PATH);
        assertThat(testBankUser.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testBankUser.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testBankUser.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testBankUser.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingBankUser() throws Exception {
        int databaseSizeBeforeUpdate = bankUserRepository.findAll().size();
        bankUser.setId(count.incrementAndGet());

        // Create the BankUser
        BankUserDTO bankUserDTO = bankUserMapper.toDto(bankUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bankUserDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankUser in the database
        List<BankUser> bankUserList = bankUserRepository.findAll();
        assertThat(bankUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBankUser() throws Exception {
        int databaseSizeBeforeUpdate = bankUserRepository.findAll().size();
        bankUser.setId(count.incrementAndGet());

        // Create the BankUser
        BankUserDTO bankUserDTO = bankUserMapper.toDto(bankUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankUser in the database
        List<BankUser> bankUserList = bankUserRepository.findAll();
        assertThat(bankUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBankUser() throws Exception {
        int databaseSizeBeforeUpdate = bankUserRepository.findAll().size();
        bankUser.setId(count.incrementAndGet());

        // Create the BankUser
        BankUserDTO bankUserDTO = bankUserMapper.toDto(bankUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankUserMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(bankUserDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankUser in the database
        List<BankUser> bankUserList = bankUserRepository.findAll();
        assertThat(bankUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBankUser() throws Exception {
        // Initialize the database
        bankUserRepository.saveAndFlush(bankUser);

        int databaseSizeBeforeDelete = bankUserRepository.findAll().size();

        // Delete the bankUser
        restBankUserMockMvc
            .perform(delete(ENTITY_API_URL_ID, bankUser.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BankUser> bankUserList = bankUserRepository.findAll();
        assertThat(bankUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
