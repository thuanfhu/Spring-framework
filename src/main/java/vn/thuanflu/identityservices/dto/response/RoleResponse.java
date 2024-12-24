package vn.thuanflu.identityservices.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.thuanflu.identityservices.entity.Permission;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class RoleResponse {
    String id;
    String name;
    String description;
    Set<Permission> permissions;
}
