package vn.thuanflu.identityservices.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.thuanflu.identityservices.dto.request.AuthenticationRequest;
import vn.thuanflu.identityservices.dto.response.ApiResponse;
import vn.thuanflu.identityservices.dto.response.AuthenticationResponse;
import vn.thuanflu.identityservices.service.AuthenticationService;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/auth")
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        return ApiResponse.<AuthenticationResponse>builder()
                .message("Đăng nhập thành công!")
                .result(this.authenticationService.authenticate(request))
                .build();
    }
}
