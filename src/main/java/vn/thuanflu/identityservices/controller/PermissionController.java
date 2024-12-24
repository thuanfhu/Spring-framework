package vn.thuanflu.identityservices.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import vn.thuanflu.identityservices.dto.request.PermissionRequest;
import vn.thuanflu.identityservices.dto.response.ApiResponse;
import vn.thuanflu.identityservices.dto.response.PermissionResponse;
import vn.thuanflu.identityservices.service.PermissionService;

import java.util.List;

@RestController
@RequestMapping("/permissions")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PermissionController {
    PermissionService permissionService;

    @PostMapping
    public ApiResponse<PermissionResponse> createPermission(@RequestBody PermissionRequest request) {
        return ApiResponse.<PermissionResponse>builder()
                .message("Tạo mới quyền hạn người dùng thành công")
                .result(this.permissionService.createPermission(request))
                .build();
    }

    @GetMapping("/{permissionId}")
    public ApiResponse<PermissionResponse> getPermissionById(@PathVariable("permissionId") String permissionId) {
        return ApiResponse.<PermissionResponse>builder()
                .message("Lấy một quyền hạn thành công")
                .result(this.permissionService.getPermissionById(permissionId))
                .build();
    }

    @GetMapping
    public ApiResponse<List<PermissionResponse>> getPermissions() {
        return ApiResponse.<List<PermissionResponse>>builder()
                .message("Lấy tất cả quyền hạn với điều kiện truy vấn thành công")
                .result(this.permissionService.getAllPermissions())
                .build();
    }

    @DeleteMapping("/{permissionId}")
    public ApiResponse<Void> deletePermissionById(@PathVariable("permissionId") String permissionId) {
        this.permissionService.deletePermissionById(permissionId);
        return ApiResponse.<Void>builder()
                .message("Xóa quyền hạn thành công")
                .build();
    }

    @PatchMapping("/{permissionId}")
    public ApiResponse<PermissionResponse> updatePermissionById(@PathVariable("permissionId") String permissionId, @RequestBody PermissionRequest request) {
        return ApiResponse.<PermissionResponse>builder()
                .message("Cập nhật quyền hạn mới thành công")
                .result(this.permissionService.updatePermissionById(permissionId, request))
                .build();
    }
}
