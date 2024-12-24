package vn.thuanflu.identityservices.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import vn.thuanflu.identityservices.dto.request.RoleRequest;
import vn.thuanflu.identityservices.dto.response.ApiResponse;
import vn.thuanflu.identityservices.dto.response.RoleResponse;
import vn.thuanflu.identityservices.service.RoleService;

import java.util.List;

@RestController
@RequestMapping("/roles")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RoleController {
    RoleService roleService;

    @PostMapping
    public ApiResponse<RoleResponse> createRole(@RequestBody RoleRequest request) {
        return ApiResponse.<RoleResponse>builder()
                .message("Tạo mới vai trò thành công")
                .result(this.roleService.createRole(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<RoleResponse>> getAllRoles() {
        return ApiResponse.<List<RoleResponse>>builder()
                .message("Lấy danh sách vai trò theo điều kiện truy vấn thành công")
                .result(this.roleService.getAllRoles())
                .build();
    }

    @GetMapping("/{roleId}")
    public ApiResponse<RoleResponse> getRole(@PathVariable("roleId") String id) {
        return ApiResponse.<RoleResponse>builder()
                .message("Lấy vai trò thành công")
                .result(this.roleService.getRole(id))
                .build();
    }

    @DeleteMapping("/{roleId}")
    public ApiResponse<Void> deleteRole(@PathVariable("roleId") String id) {
        this.roleService.deleteRole(id);
        return ApiResponse.<Void>builder()
                .message("Xóa vai trò thành công")
                .build();
    }
}
