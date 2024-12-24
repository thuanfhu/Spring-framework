package vn.thuanflu.identityservices.mapper;

import org.mapstruct.Mapper;
import vn.thuanflu.identityservices.dto.response.RoleResponse;
import vn.thuanflu.identityservices.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleResponse toRoleResponse(Role role);
}
