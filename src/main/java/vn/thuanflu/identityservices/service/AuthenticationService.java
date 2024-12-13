package vn.thuanflu.identityservices.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
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

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;

    protected static final String SIGNER_KEY = "+9jcTqa2wQEddDWtn3KdCrIyalW7PKZ0G2Lx3T6QM2rk2LFHZ+6D5XnYeLYyZE6s";

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        User currentUser = this.userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10); // Compare password with Bcrypt Algorithm
        boolean authenticated = passwordEncoder.matches(request.getPassword(), currentUser.getPassword());
        if (!authenticated) throw new AppException(ErrorCode.UNAUTHORIZED);

        // Create and issue token for user login
        String token = this.generateToken(request.getUsername());
        return AuthenticationResponse.builder()
                .authenticated(true)
                .token(token)
                .build();
    }

    private String generateToken(String username) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("thuanflu.com")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(30, ChronoUnit.MINUTES).toEpochMilli()))
                .claim("customClaimKey", "value")
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }
}
