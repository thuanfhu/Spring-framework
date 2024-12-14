package vn.thuanflu.identityservices.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import vn.thuanflu.identityservices.dto.request.AuthenticationRequest;
import vn.thuanflu.identityservices.dto.request.IntrospectRequest;
import vn.thuanflu.identityservices.dto.response.AuthenticationResponse;
import vn.thuanflu.identityservices.dto.response.IntrospectResponse;
import vn.thuanflu.identityservices.entity.User;
import vn.thuanflu.identityservices.exception.AppException;
import vn.thuanflu.identityservices.exception.ErrorCode;
import vn.thuanflu.identityservices.repository.UserRepository;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;

    @NonFinal // don't inject in constructor
    @Value("${jwt.signerKey}") // read value from file application.yaml
    protected String SIGNER_KEY;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        User currentUser = this.userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10); // Compare password with Bcrypt Algorithm
        boolean authenticated = passwordEncoder.matches(request.getPassword(), currentUser.getPassword());
        if (!authenticated) throw new AppException(ErrorCode.UNAUTHORIZED);

        // Create and issue token for user login
        String token = this.generateToken(currentUser);
        return AuthenticationResponse.builder()
                .authenticated(true)
                .token(token)
                .build();
    }

    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        String token = request.getToken();

        // Verify token
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        boolean verified = signedJWT.verify(verifier);

        // Check valid token and token expired
        Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        return IntrospectResponse.builder()
                .valid(verified && expirationTime.after(new Date()))
                .build();
    }

    private String generateToken(User user) {

        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("thuanflu.com") // domain
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(30, ChronoUnit.MINUTES).toEpochMilli()))
                .claim("userId", user.getId())
                .claim("scope", buildScope(user))
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize(); // Convert jwsObject to string
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    private String buildScope(User user) {
        StringJoiner scopeJoiner = new StringJoiner(" ");
        if(!CollectionUtils.isEmpty(user.getRoles())) user.getRoles().forEach(scopeJoiner::add);
        return scopeJoiner.toString();
    }
}
