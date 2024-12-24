package vn.thuanflu.identityservices.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import vn.thuanflu.identityservices.dto.request.PermissionRequest;
import vn.thuanflu.identityservices.dto.response.PermissionResponse;
import vn.thuanflu.identityservices.entity.Permission;
import vn.thuanflu.identityservices.exception.AppException;
import vn.thuanflu.identityservices.exception.ErrorCode;
import vn.thuanflu.identityservices.mapper.PermissionMapper;
import vn.thuanflu.identityservices.repository.PermissionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    public PermissionResponse createPermission(PermissionRequest request) {
        return permissionMapper.toPermissionResponse(permissionRepository.save(permissionMapper.toPermission(request)));
    }

    public PermissionResponse getPermissionById(String id) {
        return permissionMapper.toPermissionResponse(permissionRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND)));
    }

    public List<PermissionResponse> getAllPermissions() {
        List<Permission> permissions = permissionRepository.findAll();
        return permissions.stream().map(permissionMapper::toPermissionResponse).toList();
    }

    public PermissionResponse updatePermissionById(String id, PermissionRequest request) {
        Permission currentPermission = permissionRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND));
        permissionMapper.updatePermission(currentPermission, request);
        return permissionMapper.toPermissionResponse(permissionRepository.save(currentPermission));
    }

    public void deletePermissionById(String id) {
        permissionRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND));
        this.permissionRepository.deleteById(id);
    }
}
