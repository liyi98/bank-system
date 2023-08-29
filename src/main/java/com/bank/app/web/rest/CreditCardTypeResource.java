package com.bank.app.web.rest;

import com.bank.app.repository.CreditCardTypeRepository;
import com.bank.app.security.SecurityUtils;
import com.bank.app.service.CreditCardTypeService;
import com.bank.app.service.dto.CreditCardTypeDTO;
import com.bank.app.service.exception.AuthenticationException;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.bank.app.domain.CreditCardType}.
 */
@RestController
@RequestMapping("/api")
public class CreditCardTypeResource {

    private final Logger log = LoggerFactory.getLogger(CreditCardTypeResource.class);

    private static final String ENTITY_NAME = "bankCreditCardType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CreditCardTypeService creditCardTypeService;

    private final CreditCardTypeRepository creditCardTypeRepository;

    public CreditCardTypeResource(CreditCardTypeService creditCardTypeService, CreditCardTypeRepository creditCardTypeRepository) {
        this.creditCardTypeService = creditCardTypeService;
        this.creditCardTypeRepository = creditCardTypeRepository;
    }

    /**
     * {@code POST  /credit-card-types} : Create a new creditCardType.
     *
     * @param creditCardTypeDTO the creditCardTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new creditCardTypeDTO, or with status {@code 400 (Bad Request)} if the creditCardType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/credit-card-types")
    public ResponseEntity<CreditCardTypeDTO> createCreditCardType(@RequestBody CreditCardTypeDTO creditCardTypeDTO)
        throws URISyntaxException {
        log.debug("REST request to save CreditCardType : {}", creditCardTypeDTO);
        if (creditCardTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new creditCardType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CreditCardTypeDTO result = creditCardTypeService.save(creditCardTypeDTO);
        return ResponseEntity
            .created(new URI("/api/credit-card-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /credit-card-types/:id} : Updates an existing creditCardType.
     *
     * @param id the id of the creditCardTypeDTO to save.
     * @param creditCardTypeDTO the creditCardTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated creditCardTypeDTO,
     * or with status {@code 400 (Bad Request)} if the creditCardTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the creditCardTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     * @throws AuthenticationException
     */
    @PutMapping("/credit-card-types/{id}")
    public ResponseEntity<CreditCardTypeDTO> updateCreditCardType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CreditCardTypeDTO creditCardTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CreditCardType : {}, {}", id, creditCardTypeDTO);
        if (creditCardTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, creditCardTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!creditCardTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CreditCardTypeDTO result = creditCardTypeService.update(creditCardTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, creditCardTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /credit-card-types/:id} : Partial updates given fields of an existing creditCardType, field will ignore if it is null
     *
     * @param id the id of the creditCardTypeDTO to save.
     * @param creditCardTypeDTO the creditCardTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated creditCardTypeDTO,
     * or with status {@code 400 (Bad Request)} if the creditCardTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the creditCardTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the creditCardTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/credit-card-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CreditCardTypeDTO> partialUpdateCreditCardType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CreditCardTypeDTO creditCardTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CreditCardType partially : {}, {}", id, creditCardTypeDTO);
        if (creditCardTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, creditCardTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!creditCardTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CreditCardTypeDTO> result = creditCardTypeService.partialUpdate(creditCardTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, creditCardTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /credit-card-types} : get all the creditCardTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of creditCardTypes in body.
     * @throws AuthenticationException
     */
    @GetMapping("/p/credit-card-types")
    public List<CreditCardTypeDTO> getAllCreditCardTypes() throws AuthenticationException {
        log.info("REST request to get all CreditCardTypes");
        String login = SecurityUtils.getCurrentUserLogin();

        log.info("login : {}", login);

        return creditCardTypeService.findAll();
    }

    /**
     * {@code GET  /credit-card-types/:id} : get the "id" creditCardType.
     *
     * @param id the id of the creditCardTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the creditCardTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/credit-card-types/{id}")
    public ResponseEntity<CreditCardTypeDTO> getCreditCardType(@PathVariable Long id) {
        log.debug("REST request to get CreditCardType : {}", id);
        Optional<CreditCardTypeDTO> creditCardTypeDTO = creditCardTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(creditCardTypeDTO);
    }

    /**
     * {@code DELETE  /credit-card-types/:id} : delete the "id" creditCardType.
     *
     * @param id the id of the creditCardTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/credit-card-types/{id}")
    public ResponseEntity<Void> deleteCreditCardType(@PathVariable Long id) {
        log.debug("REST request to delete CreditCardType : {}", id);
        creditCardTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
