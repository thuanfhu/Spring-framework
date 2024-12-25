package vn.thuanflu.identityservices.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vn.thuanflu.identityservices.dto.request.RoleRequest;
import vn.thuanflu.identityservices.dto.response.RoleResponse;
import vn.thuanflu.identityservices.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
