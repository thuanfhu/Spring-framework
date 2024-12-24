package vn.thuanflu.identityservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.thuanflu.identityservices.entity.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {
}
