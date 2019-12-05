package ru.hetoiiblpb.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.hetoiiblpb.model.Role;
import ru.hetoiiblpb.model.User;
import ru.hetoiiblpb.repository.UserRepository;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public boolean isExistLogin(String login) throws SQLException {
        return userRepository.existsByLogin(login);
    }

    @Override
    public boolean notNullDataUser(User user) {
        return (!user.getLogin().isEmpty() && !user.getPassword().isEmpty());
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        return userRepository.findAll();
    }

    @Override
    public boolean addUser(User user) throws SQLException {
        if (!userRepository.existsByLogin(user.getLogin())) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setActive(true);
            user.setRoles(Collections.singleton(Role.USER));
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteUser(Long id) throws SQLException {
        userRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean updateUser(User user) throws SQLException {
        if (notNullDataUser(user)) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setActive(true);
            user.setRoles(Collections.singleton(Role.USER));
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public User getUserById(Long id) throws SQLException {
        return userRepository.getById(id);
    }

    @Override
    public User getUserByName(String name) throws SQLException {
        return userRepository.findByUsername(name);
    }
}
