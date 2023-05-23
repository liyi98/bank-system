package com.bank.app.web.rest;

import com.bank.app.repository.BankUserRepository;
import com.bank.app.service.BankUserService;
import com.bank.app.service.dto.BankUserDTO;
import com.bank.app.service.dto.BankUserLoginDTO;
import com.bank.app.service.exception.AuthenticationException;
import com.bank.app.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
 * REST controller for managing {@link com.bank.app.domain.BankUser}.
 */
@RestController
@RequestMapping("/api")
public class BankUserResource {

    private final Logger log = LoggerFactory.getLogger(BankUserResource.class);

    private static final String ENTITY_NAME = "bankBankUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BankUserService bankUserService;

    private final BankUserRepository bankUserRepository;

    public BankUserResource(BankUserService bankUserService, BankUserRepository bankUserRepository) {
        this.bankUserService = bankUserService;
        this.bankUserRepository = bankUserRepository;
    }

    /**
     * {@code POST  /bank-users} : Create a new bankUser.
     *
     * @param bankUserDTO the bankUserDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bankUserDTO, or with status {@code 400 (Bad Request)} if the bankUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bank-users")
    public ResponseEntity<BankUserDTO> createBankUser(@Valid @RequestBody BankUserDTO bankUserDTO) throws URISyntaxException {
        log.debug("REST request to save BankUser : {}", bankUserDTO);
        if (bankUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new bankUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BankUserDTO result = bankUserService.save(bankUserDTO);
        return ResponseEntity
            .created(new URI("/api/bank-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code POST  /bank-users} : Create a new bankUser.
     *
     * @param bankUserDTO the bankUserDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bankUserDTO, or with status {@code 400 (Bad Request)} if the bankUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     * @throws AuthenticationException
     */
    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@Valid @RequestBody BankUserLoginDTO bankUserLoginDTO)
        throws URISyntaxException, AuthenticationException {
        log.debug("REST request to login BankUser : {}", bankUserLoginDTO);

        String token = bankUserService.authenticate(bankUserLoginDTO);

        return ResponseEntity.ok().body(token);
    }

    /**
     * {@code PUT  /bank-users/:id} : Updates an existing bankUser.
     *
     * @param id the id of the bankUserDTO to save.
     * @param bankUserDTO the bankUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankUserDTO,
     * or with status {@code 400 (Bad Request)} if the bankUserDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bankUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bank-users/{id}")
    public ResponseEntity<BankUserDTO> updateBankUser(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BankUserDTO bankUserDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BankUser : {}, {}", id, bankUserDTO);
        if (bankUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankUserDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BankUserDTO result = bankUserService.update(bankUserDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bank-users/:id} : Partial updates given fields of an existing bankUser, field will ignore if it is null
     *
     * @param id the id of the bankUserDTO to save.
     * @param bankUserDTO the bankUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankUserDTO,
     * or with status {@code 400 (Bad Request)} if the bankUserDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bankUserDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bankUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bank-users/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BankUserDTO> partialUpdateBankUser(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BankUserDTO bankUserDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BankUser partially : {}, {}", id, bankUserDTO);
        if (bankUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankUserDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BankUserDTO> result = bankUserService.partialUpdate(bankUserDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankUserDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /bank-users} : get all the bankUsers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bankUsers in body.
     */
    @GetMapping("/bank-users")
    public List<BankUserDTO> getAllBankUsers() {
        log.debug("REST request to get all BankUsers");
        return bankUserService.findAll();
    }

    /**
     * {@code GET  /bank-users/:id} : get the "id" bankUser.
     *
     * @param id the id of the bankUserDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bankUserDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bank-users/{id}")
    public ResponseEntity<BankUserDTO> getBankUser(@PathVariable Long id) {
        log.debug("REST request to get BankUser : {}", id);
        Optional<BankUserDTO> bankUserDTO = bankUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bankUserDTO);
    }

    /**
     * {@code DELETE  /bank-users/:id} : delete the "id" bankUser.
     *
     * @param id the id of the bankUserDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bank-users/{id}")
    public ResponseEntity<Void> deleteBankUser(@PathVariable Long id) {
        log.debug("REST request to delete BankUser : {}", id);
        bankUserService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
