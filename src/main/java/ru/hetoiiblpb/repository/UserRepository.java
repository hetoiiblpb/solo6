package ru.hetoiiblpb.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.hetoiiblpb.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);

    User findByUsername(String username);

    User getById(Long id);

    User queryUserByLogin(String login);

    boolean existsByLogin(String login);

}