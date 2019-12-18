package ru.hetoiiblpb.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.hetoiiblpb.model.Role;
import ru.hetoiiblpb.model.User;
import ru.hetoiiblpb.repository.UserRepository;

import java.util.Collections;
import java.util.List;


@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;

    }

    @Override
    public boolean isExistLogin(String login) {
        return userRepository.existsByLogin(login);
    }

    @Override
    public boolean notNullDataUser(User user) {
        return (!user.getLogin().isEmpty() && !user.getPassword().isEmpty());
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public boolean addUser(User user) {
        if (!userRepository.existsByLogin(user.getLogin())) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setActive(true);
            user.setRoles(Collections.singleton(new Role(2L, "USER")));

            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteUser(Long id) {
        userRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean updateUser(User user) {
        if (notNullDataUser(user)) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setActive(true);
            user.setRoles(Collections.singleton(new Role(2L, "USER")));
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.getById(id);
    }

    @Override
    public User getUserByName(String name) {
        return userRepository.findByUsername(name);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s);
    }
}
