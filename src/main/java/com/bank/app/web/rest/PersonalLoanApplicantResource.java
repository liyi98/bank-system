package com.bank.app.web.rest;

import com.bank.app.repository.PersonalLoanApplicantRepository;
import com.bank.app.service.PersonalLoanApplicantService;
import com.bank.app.service.dto.PersonalLoanApplicantDTO;
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
 * REST controller for managing {@link com.bank.app.domain.PersonalLoanApplicant}.
 */
@RestController
@RequestMapping("/api")
public class PersonalLoanApplicantResource {

    private final Logger log = LoggerFactory.getLogger(PersonalLoanApplicantResource.class);

    private static final String ENTITY_NAME = "bankPersonalLoanApplicant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PersonalLoanApplicantService personalLoanApplicantService;

    private final PersonalLoanApplicantRepository personalLoanApplicantRepository;

    public PersonalLoanApplicantResource(
        PersonalLoanApplicantService personalLoanApplicantService,
        PersonalLoanApplicantRepository personalLoanApplicantRepository
    ) {
        this.personalLoanApplicantService = personalLoanApplicantService;
        this.personalLoanApplicantRepository = personalLoanApplicantRepository;
    }

    /**
     * {@code POST  /personal-loan-applicants} : Create a new personalLoanApplicant.
     *
     * @param personalLoanApplicantDTO the personalLoanApplicantDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new personalLoanApplicantDTO, or with status {@code 400 (Bad Request)} if the personalLoanApplicant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/personal-loan-applicants")
    public ResponseEntity<PersonalLoanApplicantDTO> createPersonalLoanApplicant(
        @RequestBody PersonalLoanApplicantDTO personalLoanApplicantDTO
    ) throws URISyntaxException {
        log.debug("REST request to save PersonalLoanApplicant : {}", personalLoanApplicantDTO);
        if (personalLoanApplicantDTO.getId() != null) {
            throw new BadRequestAlertException("A new personalLoanApplicant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PersonalLoanApplicantDTO result = personalLoanApplicantService.save(personalLoanApplicantDTO);
        return ResponseEntity
            .created(new URI("/api/personal-loan-applicants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /personal-loan-applicants/:id} : Updates an existing personalLoanApplicant.
     *
     * @param id the id of the personalLoanApplicantDTO to save.
     * @param personalLoanApplicantDTO the personalLoanApplicantDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personalLoanApplicantDTO,
     * or with status {@code 400 (Bad Request)} if the personalLoanApplicantDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the personalLoanApplicantDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/personal-loan-applicants/{id}")
    public ResponseEntity<PersonalLoanApplicantDTO> updatePersonalLoanApplicant(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PersonalLoanApplicantDTO personalLoanApplicantDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PersonalLoanApplicant : {}, {}", id, personalLoanApplicantDTO);
        if (personalLoanApplicantDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, personalLoanApplicantDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!personalLoanApplicantRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PersonalLoanApplicantDTO result = personalLoanApplicantService.update(personalLoanApplicantDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, personalLoanApplicantDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /personal-loan-applicants/:id} : Partial updates given fields of an existing personalLoanApplicant, field will ignore if it is null
     *
     * @param id the id of the personalLoanApplicantDTO to save.
     * @param personalLoanApplicantDTO the personalLoanApplicantDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personalLoanApplicantDTO,
     * or with status {@code 400 (Bad Request)} if the personalLoanApplicantDTO is not valid,
     * or with status {@code 404 (Not Found)} if the personalLoanApplicantDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the personalLoanApplicantDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/personal-loan-applicants/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PersonalLoanApplicantDTO> partialUpdatePersonalLoanApplicant(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PersonalLoanApplicantDTO personalLoanApplicantDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PersonalLoanApplicant partially : {}, {}", id, personalLoanApplicantDTO);
        if (personalLoanApplicantDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, personalLoanApplicantDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!personalLoanApplicantRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PersonalLoanApplicantDTO> result = personalLoanApplicantService.partialUpdate(personalLoanApplicantDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, personalLoanApplicantDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /personal-loan-applicants} : get all the personalLoanApplicants.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of personalLoanApplicants in body.
     */
    @GetMapping("/personal-loan-applicants")
    public List<PersonalLoanApplicantDTO> getAllPersonalLoanApplicants() {
        log.debug("REST request to get all PersonalLoanApplicants");
        return personalLoanApplicantService.findAll();
    }

    /**
     * {@code GET  /personal-loan-applicants/:id} : get the "id" personalLoanApplicant.
     *
     * @param id the id of the personalLoanApplicantDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the personalLoanApplicantDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/personal-loan-applicants/{id}")
    public ResponseEntity<PersonalLoanApplicantDTO> getPersonalLoanApplicant(@PathVariable Long id) {
        log.debug("REST request to get PersonalLoanApplicant : {}", id);
        Optional<PersonalLoanApplicantDTO> personalLoanApplicantDTO = personalLoanApplicantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(personalLoanApplicantDTO);
    }

    /**
     * {@code DELETE  /personal-loan-applicants/:id} : delete the "id" personalLoanApplicant.
     *
     * @param id the id of the personalLoanApplicantDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/personal-loan-applicants/{id}")
    public ResponseEntity<Void> deletePersonalLoanApplicant(@PathVariable Long id) {
        log.debug("REST request to delete PersonalLoanApplicant : {}", id);
        personalLoanApplicantService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
