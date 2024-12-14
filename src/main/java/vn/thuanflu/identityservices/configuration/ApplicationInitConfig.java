package vn.thuanflu.identityservices.configuration;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import vn.thuanflu.identityservices.entity.User;
import vn.thuanflu.identityservices.enums.Role;
import vn.thuanflu.identityservices.repository.UserRepository;

import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {
    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository){
        return args -> {
            if(userRepository.findByUsername("admin@gmail.com").isEmpty()){
                HashSet<String> roles = new HashSet<>();
                roles.add(Role.ADMIN.name());

                User user = User.builder()
                        .username("admin@gmail.com")
                        .password(passwordEncoder.encode("admin"))
                        .roles(roles).build();

                userRepository.save(user);
                log.info("Admin has created by default password: admin");
            }
        };
    }
}
