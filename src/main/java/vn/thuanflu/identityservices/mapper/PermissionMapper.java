package vn.thuanflu.identityservices.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import vn.thuanflu.identityservices.dto.request.PermissionRequest;
import vn.thuanflu.identityservices.dto.response.PermissionResponse;
import vn.thuanflu.identityservices.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);

    void updatePermission(@MappingTarget Permission permission, PermissionRequest request);
}
