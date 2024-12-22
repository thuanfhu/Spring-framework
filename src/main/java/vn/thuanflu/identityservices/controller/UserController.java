package vn.thuanflu.identityservices.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import vn.thuanflu.identityservices.dto.request.UserCreationRequest;
import vn.thuanflu.identityservices.dto.request.UserUpdateRequest;
import vn.thuanflu.identityservices.dto.response.ApiResponse;
import vn.thuanflu.identityservices.dto.response.UserResponse;
import vn.thuanflu.identityservices.entity.User;
import vn.thuanflu.identityservices.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserController {
    UserService userService;

    @PostMapping
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request){
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(this.userService.createUser(request));
        return apiResponse;
    }

    @GetMapping
    public List<UserResponse> getAllUsers(){
        return this.userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public UserResponse getUserById(@PathVariable("userId") String id){
        return this.userService.getUserById(id);
    }

    @PutMapping("/{userId}")
    public UserResponse updateUserById(@PathVariable String userId, @RequestBody UserUpdateRequest request){
        return this.userService.updateUserById(userId, request);
    }

    @DeleteMapping("/{userId}")
    public String deleteUserById(@PathVariable String userId){
        this.userService.deleteUserById(userId);
        return "User has been deleted";
    }

    @GetMapping("/account")
    public ApiResponse<UserResponse> getUserAccount(){
        return ApiResponse.<UserResponse>builder()
                .message("Lấy thông tin người dùng đang đăng nhập thành công")
                .result(this.userService.getMyInfo())
                .build();
    }
}
