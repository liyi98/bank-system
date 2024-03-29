package com.bank.app.web.rest;

import com.bank.app.repository.AdminRepository;
import com.bank.app.security.SecurityUtils;
import com.bank.app.service.AdminService;
import com.bank.app.service.dto.AdminDTO;
import com.bank.app.service.dto.AuthenticationDTO;
import com.bank.app.service.exception.AuthenticationException;
import com.bank.app.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
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
 * REST controller for managing {@link com.bank.app.domain.Admin}.
 */
@RestController
@RequestMapping("/api")
public class AdminResource {

    private final Logger log = LoggerFactory.getLogger(AdminResource.class);

    private static final String ENTITY_NAME = "bankAdmin";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdminService adminService;

    private final AdminRepository adminRepository;

    public AdminResource(AdminService adminService, AdminRepository adminRepository) {
        this.adminService = adminService;
        this.adminRepository = adminRepository;
    }

    /**
     * {@code GET  /admins} : get all the admins.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of admins in body.
     * @throws AuthenticationException
     */
    @GetMapping("/p/profile")
    public ResponseEntity<AdminDTO> getProfile() throws AuthenticationException {
        log.debug("REST request to get all Admins : {}", SecurityUtils.getCurrentUserLogin());
        return ResponseEntity.ok().body(adminService.findOneByLogin(SecurityUtils.getCurrentUserLogin()));
    }

    /**
     * {@code POST  /bank-users} : Create a new bankUser.
     *
     * @param bankUserDTO the bankUserDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bankUserDTO, or with status {@code 400 (Bad Request)} if the bankUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     * @throws AuthenticationException
     */
    @PostMapping("/admin/authenticate")
    public ResponseEntity<AuthenticationDTO> authenticate(@Valid @RequestBody AdminDTO adminDTO)
        throws URISyntaxException, AuthenticationException {
        log.debug("REST request to login BankUser : {}", adminDTO);
        AuthenticationDTO data = new AuthenticationDTO();
        String token = adminService.authenticate(adminDTO);

        data.setToken(token);
        return ResponseEntity.ok().body(data);
    }

    /**
     * {@code POST  /admins} : Create a new admin.
     *
     * @param adminDTO the adminDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adminDTO, or with status {@code 400 (Bad Request)} if the admin has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/p/admins")
    public ResponseEntity<AdminDTO> createAdmin(@RequestBody AdminDTO adminDTO) throws URISyntaxException {
        log.debug("REST request to save Admin : {}", adminDTO);
        if (adminDTO.getId() != null) {
            throw new BadRequestAlertException("A new admin cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdminDTO result = adminService.save(adminDTO);
        return ResponseEntity
            .created(new URI("/api/admins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /admins/:id} : Updates an existing admin.
     *
     * @param id the id of the adminDTO to save.
     * @param adminDTO the adminDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adminDTO,
     * or with status {@code 400 (Bad Request)} if the adminDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adminDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/admins/{id}")
    public ResponseEntity<AdminDTO> updateAdmin(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AdminDTO adminDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Admin : {}, {}", id, adminDTO);
        if (adminDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adminDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adminRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AdminDTO result = adminService.update(adminDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adminDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /admins/:id} : Partial updates given fields of an existing admin, field will ignore if it is null
     *
     * @param id the id of the adminDTO to save.
     * @param adminDTO the adminDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adminDTO,
     * or with status {@code 400 (Bad Request)} if the adminDTO is not valid,
     * or with status {@code 404 (Not Found)} if the adminDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the adminDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/admins/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AdminDTO> partialUpdateAdmin(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AdminDTO adminDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Admin partially : {}, {}", id, adminDTO);
        if (adminDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adminDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adminRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AdminDTO> result = adminService.partialUpdate(adminDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adminDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /admins} : get all the admins.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of admins in body.
     */
    @GetMapping("/admins")
    public List<AdminDTO> getAllAdmins() {
        log.debug("REST request to get all Admins");
        return adminService.findAll();
    }

    /**
     * {@code GET  /admins/:id} : get the "id" admin.
     *
     * @param id the id of the adminDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adminDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/admins/{id}")
    public ResponseEntity<AdminDTO> getAdmin(@PathVariable Long id) {
        log.debug("REST request to get Admin : {}", id);
        Optional<AdminDTO> adminDTO = adminService.findOne(id);
        return ResponseUtil.wrapOrNotFound(adminDTO);
    }

    /**
     * {@code DELETE  /admins/:id} : delete the "id" admin.
     *
     * @param id the id of the adminDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/admins/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        log.debug("REST request to delete Admin : {}", id);
        adminService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
