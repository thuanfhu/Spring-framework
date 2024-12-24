package vn.thuanflu.identityservices.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.thuanflu.identityservices.dto.request.RoleRequest;
import vn.thuanflu.identityservices.dto.response.RoleResponse;
import vn.thuanflu.identityservices.entity.Permission;
import vn.thuanflu.identityservices.entity.Role;
import vn.thuanflu.identityservices.exception.AppException;
import vn.thuanflu.identityservices.exception.ErrorCode;
import vn.thuanflu.identityservices.mapper.RoleMapper;
import vn.thuanflu.identityservices.repository.PermissionRepository;
import vn.thuanflu.identityservices.repository.RoleRepository;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    public RoleResponse createRole(RoleRequest roleRequest) {
        List<Permission> permissions = this.permissionRepository.findAllById(roleRequest.getPermissions());

        if (permissions.size() != roleRequest.getPermissions().size()) throw new AppException(ErrorCode.PERMISSION_NOT_FOUND);

        Role role = Role.builder().name(roleRequest.getName())
                .description(roleRequest.getDescription())
                .permissions(new HashSet<>(permissions))
                .build();

        return roleMapper.toRoleResponse(roleRepository.save(role));
    }

    public List<RoleResponse> getAllRoles() {
        return this.roleRepository.findAll().stream().map(roleMapper::toRoleResponse).toList();
    }

    public RoleResponse getRole(String id) {
        return roleMapper.toRoleResponse(this.roleRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND)));
    }

    public void deleteRole(String id) {
        this.roleRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
        this.roleRepository.deleteById(id);
    }
}
