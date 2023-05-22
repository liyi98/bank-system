package com.bank.app.web.rest;

import com.bank.app.repository.PersonalLoanRepository;
import com.bank.app.service.PersonalLoanService;
import com.bank.app.service.dto.PersonalLoanDTO;
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
 * REST controller for managing {@link com.bank.app.domain.PersonalLoan}.
 */
@RestController
@RequestMapping("/api")
public class PersonalLoanResource {

    private final Logger log = LoggerFactory.getLogger(PersonalLoanResource.class);

    private static final String ENTITY_NAME = "bankPersonalLoan";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PersonalLoanService personalLoanService;

    private final PersonalLoanRepository personalLoanRepository;

    public PersonalLoanResource(PersonalLoanService personalLoanService, PersonalLoanRepository personalLoanRepository) {
        this.personalLoanService = personalLoanService;
        this.personalLoanRepository = personalLoanRepository;
    }

    /**
     * {@code POST  /personal-loans} : Create a new personalLoan.
     *
     * @param personalLoanDTO the personalLoanDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new personalLoanDTO, or with status {@code 400 (Bad Request)} if the personalLoan has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/personal-loans")
    public ResponseEntity<PersonalLoanDTO> createPersonalLoan(@RequestBody PersonalLoanDTO personalLoanDTO) throws URISyntaxException {
        log.debug("REST request to save PersonalLoan : {}", personalLoanDTO);
        if (personalLoanDTO.getId() != null) {
            throw new BadRequestAlertException("A new personalLoan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PersonalLoanDTO result = personalLoanService.save(personalLoanDTO);
        return ResponseEntity
            .created(new URI("/api/personal-loans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /personal-loans/:id} : Updates an existing personalLoan.
     *
     * @param id the id of the personalLoanDTO to save.
     * @param personalLoanDTO the personalLoanDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personalLoanDTO,
     * or with status {@code 400 (Bad Request)} if the personalLoanDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the personalLoanDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/personal-loans/{id}")
    public ResponseEntity<PersonalLoanDTO> updatePersonalLoan(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PersonalLoanDTO personalLoanDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PersonalLoan : {}, {}", id, personalLoanDTO);
        if (personalLoanDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, personalLoanDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!personalLoanRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PersonalLoanDTO result = personalLoanService.update(personalLoanDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, personalLoanDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /personal-loans/:id} : Partial updates given fields of an existing personalLoan, field will ignore if it is null
     *
     * @param id the id of the personalLoanDTO to save.
     * @param personalLoanDTO the personalLoanDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personalLoanDTO,
     * or with status {@code 400 (Bad Request)} if the personalLoanDTO is not valid,
     * or with status {@code 404 (Not Found)} if the personalLoanDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the personalLoanDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/personal-loans/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PersonalLoanDTO> partialUpdatePersonalLoan(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PersonalLoanDTO personalLoanDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PersonalLoan partially : {}, {}", id, personalLoanDTO);
        if (personalLoanDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, personalLoanDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!personalLoanRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PersonalLoanDTO> result = personalLoanService.partialUpdate(personalLoanDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, personalLoanDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /personal-loans} : get all the personalLoans.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of personalLoans in body.
     */
    @GetMapping("/personal-loans")
    public List<PersonalLoanDTO> getAllPersonalLoans(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all PersonalLoans");
        return personalLoanService.findAll();
    }

    /**
     * {@code GET  /personal-loans/:id} : get the "id" personalLoan.
     *
     * @param id the id of the personalLoanDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the personalLoanDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/personal-loans/{id}")
    public ResponseEntity<PersonalLoanDTO> getPersonalLoan(@PathVariable Long id) {
        log.debug("REST request to get PersonalLoan : {}", id);
        Optional<PersonalLoanDTO> personalLoanDTO = personalLoanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(personalLoanDTO);
    }

    /**
     * {@code DELETE  /personal-loans/:id} : delete the "id" personalLoan.
     *
     * @param id the id of the personalLoanDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/personal-loans/{id}")
    public ResponseEntity<Void> deletePersonalLoan(@PathVariable Long id) {
        log.debug("REST request to delete PersonalLoan : {}", id);
        personalLoanService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
