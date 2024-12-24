package vn.thuanflu.identityservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.thuanflu.identityservices.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
}
