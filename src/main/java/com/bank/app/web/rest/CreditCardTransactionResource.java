package com.bank.app.web.rest;

import com.bank.app.repository.CreditCardTransactionRepository;
import com.bank.app.service.CreditCardTransactionService;
import com.bank.app.service.dto.CreditCardTransactionDTO;
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
 * REST controller for managing {@link com.bank.app.domain.CreditCardTransaction}.
 */
@RestController
@RequestMapping("/api")
public class CreditCardTransactionResource {

    private final Logger log = LoggerFactory.getLogger(CreditCardTransactionResource.class);

    private static final String ENTITY_NAME = "bankCreditCardTransaction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CreditCardTransactionService creditCardTransactionService;

    private final CreditCardTransactionRepository creditCardTransactionRepository;

    public CreditCardTransactionResource(
        CreditCardTransactionService creditCardTransactionService,
        CreditCardTransactionRepository creditCardTransactionRepository
    ) {
        this.creditCardTransactionService = creditCardTransactionService;
        this.creditCardTransactionRepository = creditCardTransactionRepository;
    }

    /**
     * {@code POST  /credit-card-transactions} : Create a new creditCardTransaction.
     *
     * @param creditCardTransactionDTO the creditCardTransactionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new creditCardTransactionDTO, or with status {@code 400 (Bad Request)} if the creditCardTransaction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/credit-card-transactions")
    public ResponseEntity<CreditCardTransactionDTO> createCreditCardTransaction(
        @RequestBody CreditCardTransactionDTO creditCardTransactionDTO
    ) throws URISyntaxException {
        log.debug("REST request to save CreditCardTransaction : {}", creditCardTransactionDTO);
        if (creditCardTransactionDTO.getId() != null) {
            throw new BadRequestAlertException("A new creditCardTransaction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CreditCardTransactionDTO result = creditCardTransactionService.save(creditCardTransactionDTO);
        return ResponseEntity
            .created(new URI("/api/credit-card-transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /credit-card-transactions/:id} : Updates an existing creditCardTransaction.
     *
     * @param id the id of the creditCardTransactionDTO to save.
     * @param creditCardTransactionDTO the creditCardTransactionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated creditCardTransactionDTO,
     * or with status {@code 400 (Bad Request)} if the creditCardTransactionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the creditCardTransactionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/credit-card-transactions/{id}")
    public ResponseEntity<CreditCardTransactionDTO> updateCreditCardTransaction(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CreditCardTransactionDTO creditCardTransactionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CreditCardTransaction : {}, {}", id, creditCardTransactionDTO);
        if (creditCardTransactionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, creditCardTransactionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!creditCardTransactionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CreditCardTransactionDTO result = creditCardTransactionService.update(creditCardTransactionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, creditCardTransactionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /credit-card-transactions/:id} : Partial updates given fields of an existing creditCardTransaction, field will ignore if it is null
     *
     * @param id the id of the creditCardTransactionDTO to save.
     * @param creditCardTransactionDTO the creditCardTransactionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated creditCardTransactionDTO,
     * or with status {@code 400 (Bad Request)} if the creditCardTransactionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the creditCardTransactionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the creditCardTransactionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/credit-card-transactions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CreditCardTransactionDTO> partialUpdateCreditCardTransaction(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CreditCardTransactionDTO creditCardTransactionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CreditCardTransaction partially : {}, {}", id, creditCardTransactionDTO);
        if (creditCardTransactionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, creditCardTransactionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!creditCardTransactionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CreditCardTransactionDTO> result = creditCardTransactionService.partialUpdate(creditCardTransactionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, creditCardTransactionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /credit-card-transactions} : get all the creditCardTransactions.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of creditCardTransactions in body.
     */
    @GetMapping("/credit-card-transactions")
    public List<CreditCardTransactionDTO> getAllCreditCardTransactions(
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get all CreditCardTransactions");
        return creditCardTransactionService.findAll();
    }

    /**
     * {@code GET  /credit-card-transactions/:id} : get the "id" creditCardTransaction.
     *
     * @param id the id of the creditCardTransactionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the creditCardTransactionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/credit-card-transactions/{id}")
    public ResponseEntity<CreditCardTransactionDTO> getCreditCardTransaction(@PathVariable Long id) {
        log.debug("REST request to get CreditCardTransaction : {}", id);
        Optional<CreditCardTransactionDTO> creditCardTransactionDTO = creditCardTransactionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(creditCardTransactionDTO);
    }

    /**
     * {@code DELETE  /credit-card-transactions/:id} : delete the "id" creditCardTransaction.
     *
     * @param id the id of the creditCardTransactionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/credit-card-transactions/{id}")
    public ResponseEntity<Void> deleteCreditCardTransaction(@PathVariable Long id) {
        log.debug("REST request to delete CreditCardTransaction : {}", id);
        creditCardTransactionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
