package ru.hetoiiblpb.service;

import org.springframework.transaction.annotation.Transactional;
import ru.hetoiiblpb.model.User;

import java.sql.SQLException;
import java.util.List;


public interface UserService {

    @Transactional
    boolean isExistLogin(String login) throws SQLException;

    @Transactional
    boolean notNullDataUser(User user);

    @Transactional
    List<User> getAllUsers() throws SQLException;

    @Transactional
     boolean addUser(User user) throws SQLException;

    @Transactional
     boolean deleteUser(Long id) throws SQLException;

    @Transactional
     boolean updateUser(User user) throws SQLException;

    @Transactional
    User getUserById(Long id) throws SQLException;

    @Transactional
    User getUserByName(String name) throws SQLException;

}
