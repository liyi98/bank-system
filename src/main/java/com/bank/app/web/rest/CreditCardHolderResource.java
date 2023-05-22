package com.bank.app.web.rest;

import com.bank.app.repository.CreditCardHolderRepository;
import com.bank.app.service.CreditCardHolderService;
import com.bank.app.service.dto.CreditCardHolderDTO;
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
 * REST controller for managing {@link com.bank.app.domain.CreditCardHolder}.
 */
@RestController
@RequestMapping("/api")
public class CreditCardHolderResource {

    private final Logger log = LoggerFactory.getLogger(CreditCardHolderResource.class);

    private static final String ENTITY_NAME = "bankCreditCardHolder";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CreditCardHolderService creditCardHolderService;

    private final CreditCardHolderRepository creditCardHolderRepository;

    public CreditCardHolderResource(
        CreditCardHolderService creditCardHolderService,
        CreditCardHolderRepository creditCardHolderRepository
    ) {
        this.creditCardHolderService = creditCardHolderService;
        this.creditCardHolderRepository = creditCardHolderRepository;
    }

    /**
     * {@code POST  /credit-card-holders} : Create a new creditCardHolder.
     *
     * @param creditCardHolderDTO the creditCardHolderDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new creditCardHolderDTO, or with status {@code 400 (Bad Request)} if the creditCardHolder has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/credit-card-holders")
    public ResponseEntity<CreditCardHolderDTO> createCreditCardHolder(@RequestBody CreditCardHolderDTO creditCardHolderDTO)
        throws URISyntaxException {
        log.debug("REST request to save CreditCardHolder : {}", creditCardHolderDTO);
        if (creditCardHolderDTO.getId() != null) {
            throw new BadRequestAlertException("A new creditCardHolder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CreditCardHolderDTO result = creditCardHolderService.save(creditCardHolderDTO);
        return ResponseEntity
            .created(new URI("/api/credit-card-holders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /credit-card-holders/:id} : Updates an existing creditCardHolder.
     *
     * @param id the id of the creditCardHolderDTO to save.
     * @param creditCardHolderDTO the creditCardHolderDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated creditCardHolderDTO,
     * or with status {@code 400 (Bad Request)} if the creditCardHolderDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the creditCardHolderDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/credit-card-holders/{id}")
    public ResponseEntity<CreditCardHolderDTO> updateCreditCardHolder(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CreditCardHolderDTO creditCardHolderDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CreditCardHolder : {}, {}", id, creditCardHolderDTO);
        if (creditCardHolderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, creditCardHolderDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!creditCardHolderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CreditCardHolderDTO result = creditCardHolderService.update(creditCardHolderDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, creditCardHolderDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /credit-card-holders/:id} : Partial updates given fields of an existing creditCardHolder, field will ignore if it is null
     *
     * @param id the id of the creditCardHolderDTO to save.
     * @param creditCardHolderDTO the creditCardHolderDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated creditCardHolderDTO,
     * or with status {@code 400 (Bad Request)} if the creditCardHolderDTO is not valid,
     * or with status {@code 404 (Not Found)} if the creditCardHolderDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the creditCardHolderDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/credit-card-holders/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CreditCardHolderDTO> partialUpdateCreditCardHolder(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CreditCardHolderDTO creditCardHolderDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CreditCardHolder partially : {}, {}", id, creditCardHolderDTO);
        if (creditCardHolderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, creditCardHolderDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!creditCardHolderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CreditCardHolderDTO> result = creditCardHolderService.partialUpdate(creditCardHolderDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, creditCardHolderDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /credit-card-holders} : get all the creditCardHolders.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of creditCardHolders in body.
     */
    @GetMapping("/credit-card-holders")
    public List<CreditCardHolderDTO> getAllCreditCardHolders() {
        log.debug("REST request to get all CreditCardHolders");
        return creditCardHolderService.findAll();
    }

    /**
     * {@code GET  /credit-card-holders/:id} : get the "id" creditCardHolder.
     *
     * @param id the id of the creditCardHolderDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the creditCardHolderDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/credit-card-holders/{id}")
    public ResponseEntity<CreditCardHolderDTO> getCreditCardHolder(@PathVariable Long id) {
        log.debug("REST request to get CreditCardHolder : {}", id);
        Optional<CreditCardHolderDTO> creditCardHolderDTO = creditCardHolderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(creditCardHolderDTO);
    }

    /**
     * {@code DELETE  /credit-card-holders/:id} : delete the "id" creditCardHolder.
     *
     * @param id the id of the creditCardHolderDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/credit-card-holders/{id}")
    public ResponseEntity<Void> deleteCreditCardHolder(@PathVariable Long id) {
        log.debug("REST request to delete CreditCardHolder : {}", id);
        creditCardHolderService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
