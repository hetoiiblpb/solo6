package ru.hetoiiblpb.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.hetoiiblpb.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @Transactional
    Role findByRole(String role);


}