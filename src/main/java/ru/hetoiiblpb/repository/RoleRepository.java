package ru.hetoiiblpb.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.hetoiiblpb.model.Role;

import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @Transactional
    Role findByRole(String role);

    @Transactional
    Set<Role> findAllByRolesIsIn(Set<String> roles);

}