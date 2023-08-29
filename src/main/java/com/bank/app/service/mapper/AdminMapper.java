package com.bank.app.service.mapper;

import com.bank.app.domain.Admin;
import com.bank.app.service.dto.AdminDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Admin} and its DTO {@link AdminDTO}.
 */
@Mapper(componentModel = "spring")
public interface AdminMapper extends EntityMapper<AdminDTO, Admin> {}
