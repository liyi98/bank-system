package com.bank.app.web.rest;

import com.bank.app.repository.PersonalLoanTransactionRepository;
import com.bank.app.service.PersonalLoanTransactionService;
import com.bank.app.service.dto.PersonalLoanTransactionDTO;
import com.bank.app.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.bank.app.domain.PersonalLoanTransaction}.
 */
@RestController
@RequestMapping("/api")
public class PersonalLoanTransactionResource {

    private final Logger log = LoggerFactory.getLogger(PersonalLoanTransactionResource.class);

    private static final String ENTITY_NAME = "bankPersonalLoanTransaction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PersonalLoanTransactionService personalLoanTransactionService;

    private final PersonalLoanTransactionRepository personalLoanTransactionRepository;

    public PersonalLoanTransactionResource(
        PersonalLoanTransactionService personalLoanTransactionService,
        PersonalLoanTransactionRepository personalLoanTransactionRepository
    ) {
        this.personalLoanTransactionService = personalLoanTransactionService;
        this.personalLoanTransactionRepository = personalLoanTransactionRepository;
    }

    /**
     * {@code POST  /personal-loan-transactions} : Create a new personalLoanTransaction.
     *
     * @param personalLoanTransactionDTO the personalLoanTransactionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new personalLoanTransactionDTO, or with status {@code 400 (Bad Request)} if the personalLoanTransaction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/personal-loan-transactions")
    public ResponseEntity<PersonalLoanTransactionDTO> createPersonalLoanTransaction(
        @RequestBody PersonalLoanTransactionDTO personalLoanTransactionDTO
    ) throws URISyntaxException {
        log.debug("REST request to save PersonalLoanTransaction : {}", personalLoanTransactionDTO);
        if (personalLoanTransactionDTO.getId() != null) {
            throw new BadRequestAlertException("A new personalLoanTransaction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PersonalLoanTransactionDTO result = personalLoanTransactionService.save(personalLoanTransactionDTO);
        return ResponseEntity
            .created(new URI("/api/personal-loan-transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /personal-loan-transactions/:id} : Updates an existing personalLoanTransaction.
     *
     * @param id the id of the personalLoanTransactionDTO to save.
     * @param personalLoanTransactionDTO the personalLoanTransactionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personalLoanTransactionDTO,
     * or with status {@code 400 (Bad Request)} if the personalLoanTransactionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the personalLoanTransactionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/personal-loan-transactions/{id}")
    public ResponseEntity<PersonalLoanTransactionDTO> updatePersonalLoanTransaction(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PersonalLoanTransactionDTO personalLoanTransactionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PersonalLoanTransaction : {}, {}", id, personalLoanTransactionDTO);
        if (personalLoanTransactionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, personalLoanTransactionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!personalLoanTransactionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PersonalLoanTransactionDTO result = personalLoanTransactionService.update(personalLoanTransactionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, personalLoanTransactionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /personal-loan-transactions/:id} : Partial updates given fields of an existing personalLoanTransaction, field will ignore if it is null
     *
     * @param id the id of the personalLoanTransactionDTO to save.
     * @param personalLoanTransactionDTO the personalLoanTransactionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personalLoanTransactionDTO,
     * or with status {@code 400 (Bad Request)} if the personalLoanTransactionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the personalLoanTransactionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the personalLoanTransactionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/personal-loan-transactions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PersonalLoanTransactionDTO> partialUpdatePersonalLoanTransaction(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PersonalLoanTransactionDTO personalLoanTransactionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PersonalLoanTransaction partially : {}, {}", id, personalLoanTransactionDTO);
        if (personalLoanTransactionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, personalLoanTransactionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!personalLoanTransactionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PersonalLoanTransactionDTO> result = personalLoanTransactionService.partialUpdate(personalLoanTransactionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, personalLoanTransactionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /personal-loan-transactions} : get all the personalLoanTransactions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of personalLoanTransactions in body.
     */
    @GetMapping("/personal-loan-transactions")
    public List<PersonalLoanTransactionDTO> getAllPersonalLoanTransactions() {
        log.debug("REST request to get all PersonalLoanTransactions");
        return personalLoanTransactionService.findAll();
    }

    /**
     * {@code GET  /personal-loan-transactions/:id} : get the "id" personalLoanTransaction.
     *
     * @param id the id of the personalLoanTransactionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the personalLoanTransactionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/personal-loan-transactions/{id}")
    public ResponseEntity<PersonalLoanTransactionDTO> getPersonalLoanTransaction(@PathVariable Long id) {
        log.debug("REST request to get PersonalLoanTransaction : {}", id);
        Optional<PersonalLoanTransactionDTO> personalLoanTransactionDTO = personalLoanTransactionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(personalLoanTransactionDTO);
    }

    /**
     * {@code DELETE  /personal-loan-transactions/:id} : delete the "id" personalLoanTransaction.
     *
     * @param id the id of the personalLoanTransactionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/personal-loan-transactions/{id}")
    public ResponseEntity<Void> deletePersonalLoanTransaction(@PathVariable Long id) {
        log.debug("REST request to delete PersonalLoanTransaction : {}", id);
        personalLoanTransactionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
