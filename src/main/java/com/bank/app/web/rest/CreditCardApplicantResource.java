package com.bank.app.web.rest;

import com.bank.app.repository.CreditCardApplicantRepository;
import com.bank.app.service.CreditCardApplicantService;
import com.bank.app.service.FileStorageService;
import com.bank.app.service.dto.CreditCardApplicantDTO;
import com.bank.app.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import jodd.net.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.bank.app.domain.CreditCardApplicant}.
 */
@RestController
@RequestMapping("/api")
public class CreditCardApplicantResource {

    private final Logger log = LoggerFactory.getLogger(CreditCardApplicantResource.class);

    private static final String ENTITY_NAME = "bankCreditCardApplicant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CreditCardApplicantService creditCardApplicantService;

    private final CreditCardApplicantRepository creditCardApplicantRepository;

    private final FileStorageService fileStorageService;

    public CreditCardApplicantResource(
        CreditCardApplicantService creditCardApplicantService,
        CreditCardApplicantRepository creditCardApplicantRepository,
        FileStorageService fileStorageService
    ) {
        this.creditCardApplicantService = creditCardApplicantService;
        this.creditCardApplicantRepository = creditCardApplicantRepository;
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/upload-ic")
    public ResponseEntity<?> uploadIcFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            fileStorageService.save(file);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.ok("");
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
            return ResponseEntity.badRequest().body("Faile to upload file");
        }
    }

    /**
     * {@code POST  /credit-card-applicants} : Create a new creditCardApplicant.
     *
     * @param creditCardApplicantDTO the creditCardApplicantDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new creditCardApplicantDTO, or with status {@code 400 (Bad Request)} if the creditCardApplicant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/credit-card-applicants")
    public ResponseEntity<CreditCardApplicantDTO> createCreditCardApplicant(@RequestBody CreditCardApplicantDTO creditCardApplicantDTO)
        throws URISyntaxException {
        log.debug("REST request to save CreditCardApplicant : {}", creditCardApplicantDTO);
        if (creditCardApplicantDTO.getId() != null) {
            throw new BadRequestAlertException("A new creditCardApplicant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CreditCardApplicantDTO result = creditCardApplicantService.save(creditCardApplicantDTO);
        return ResponseEntity
            .created(new URI("/api/credit-card-applicants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /credit-card-applicants/:id} : Updates an existing creditCardApplicant.
     *
     * @param id the id of the creditCardApplicantDTO to save.
     * @param creditCardApplicantDTO the creditCardApplicantDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated creditCardApplicantDTO,
     * or with status {@code 400 (Bad Request)} if the creditCardApplicantDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the creditCardApplicantDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/credit-card-applicants/{id}")
    public ResponseEntity<CreditCardApplicantDTO> updateCreditCardApplicant(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CreditCardApplicantDTO creditCardApplicantDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CreditCardApplicant : {}, {}", id, creditCardApplicantDTO);
        if (creditCardApplicantDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, creditCardApplicantDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!creditCardApplicantRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CreditCardApplicantDTO result = creditCardApplicantService.update(creditCardApplicantDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, creditCardApplicantDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /credit-card-applicants/:id} : Partial updates given fields of an existing creditCardApplicant, field will ignore if it is null
     *
     * @param id the id of the creditCardApplicantDTO to save.
     * @param creditCardApplicantDTO the creditCardApplicantDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated creditCardApplicantDTO,
     * or with status {@code 400 (Bad Request)} if the creditCardApplicantDTO is not valid,
     * or with status {@code 404 (Not Found)} if the creditCardApplicantDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the creditCardApplicantDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/credit-card-applicants/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CreditCardApplicantDTO> partialUpdateCreditCardApplicant(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CreditCardApplicantDTO creditCardApplicantDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CreditCardApplicant partially : {}, {}", id, creditCardApplicantDTO);
        if (creditCardApplicantDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, creditCardApplicantDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!creditCardApplicantRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CreditCardApplicantDTO> result = creditCardApplicantService.partialUpdate(creditCardApplicantDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, creditCardApplicantDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /credit-card-applicants} : get all the creditCardApplicants.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of creditCardApplicants in body.
     */
    @GetMapping("/credit-card-applicants")
    public List<CreditCardApplicantDTO> getAllCreditCardApplicants(
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get all CreditCardApplicants");
        return creditCardApplicantService.findAll();
    }

    /**
     * {@code GET  /credit-card-applicants/:id} : get the "id" creditCardApplicant.
     *
     * @param id the id of the creditCardApplicantDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the creditCardApplicantDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/credit-card-applicants/{id}")
    public ResponseEntity<CreditCardApplicantDTO> getCreditCardApplicant(@PathVariable Long id) {
        log.debug("REST request to get CreditCardApplicant : {}", id);
        Optional<CreditCardApplicantDTO> creditCardApplicantDTO = creditCardApplicantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(creditCardApplicantDTO);
    }

    /**
     * {@code DELETE  /credit-card-applicants/:id} : delete the "id" creditCardApplicant.
     *
     * @param id the id of the creditCardApplicantDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/credit-card-applicants/{id}")
    public ResponseEntity<Void> deleteCreditCardApplicant(@PathVariable Long id) {
        log.debug("REST request to delete CreditCardApplicant : {}", id);
        creditCardApplicantService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
