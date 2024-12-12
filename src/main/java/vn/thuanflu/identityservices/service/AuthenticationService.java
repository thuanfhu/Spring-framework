package vn.thuanflu.identityservices.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.thuanflu.identityservices.dto.request.AuthenticationRequest;
import vn.thuanflu.identityservices.dto.response.AuthenticationResponse;
import vn.thuanflu.identityservices.entity.User;
import vn.thuanflu.identityservices.exception.AppException;
import vn.thuanflu.identityservices.exception.ErrorCode;
import vn.thuanflu.identityservices.repository.UserRepository;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;

    public boolean authenticate(AuthenticationRequest request) {
        User currentUser = this.userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10); // Compare password with Bcrypt Algorithm
        return passwordEncoder.matches(request.getPassword(), currentUser.getPassword());
    }
}
