package vn.thuanflu.identityservices.configuration;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import vn.thuanflu.identityservices.entity.Role;
import vn.thuanflu.identityservices.entity.User;
import vn.thuanflu.identityservices.exception.AppException;
import vn.thuanflu.identityservices.exception.ErrorCode;
import vn.thuanflu.identityservices.repository.RoleRepository;
import vn.thuanflu.identityservices.repository.UserRepository;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {
    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository) {
        return args -> {
            // Create default role
            createRoleIfNotExists(roleRepository, vn.thuanflu.identityservices.enums.Role.ADMIN.name(), "Admin can access all endpoints");
            createRoleIfNotExists(roleRepository, vn.thuanflu.identityservices.enums.Role.USER.name(), "User can access basic endpoints");

            // Create default user with role
            createUserIfNotExists(userRepository, roleRepository, "admin@gmail.com", "admin", vn.thuanflu.identityservices.enums.Role.ADMIN);
            createUserIfNotExists(userRepository, roleRepository, "user@gmail.com", "user", vn.thuanflu.identityservices.enums.Role.USER);
        };
    }

    private void createRoleIfNotExists(RoleRepository roleRepository, String roleName, String description) {
        if (roleRepository.findByName(roleName).isEmpty()) {
            Role role = Role.builder()
                    .name(roleName)
                    .description(description)
                    .build();
            roleRepository.save(role);
            log.info("Created default role: {}", roleName);
        }
    }

    private void createUserIfNotExists(UserRepository userRepository, RoleRepository roleRepository, String username, String password, vn.thuanflu.identityservices.enums.Role roleEnum) {
        if (userRepository.findByUsername(username).isEmpty()) {
            Role role = roleRepository.findByName(roleEnum.name()).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
            User user = User.builder()
                    .username(username)
                    .password(passwordEncoder.encode(password))
                    .roles(Set.of(role))
                    .build();
            userRepository.save(user);
            log.info("Created default user: {} with role: {}", username, roleEnum.name());
        }
    }
}
