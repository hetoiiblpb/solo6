package ru.hetoiiblpb.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hetoiiblpb.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);

    User findByUsername(String username);

    User getById(Long id);

    User queryUserByLogin(String login);

    boolean existsByLogin(String login);

    @Query("select distinct u from User u join fetch u.roles order by u.id")
    List<User> findAll();

}