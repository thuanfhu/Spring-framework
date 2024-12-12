package vn.thuanflu.identityservices.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UserCreationRequest {
    String username;

    @Size(min = 8, message = "PASSWORD_MIN")
    String password;

    @Size(min = 3, message = "FIRSTNAME_MIN")
    String firstName;
    String lastName;
    LocalDate birthDate;
}
