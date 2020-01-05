package ru.hetoiiblpb.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.hetoiiblpb.model.Role;
import ru.hetoiiblpb.model.User;
import ru.hetoiiblpb.model.UserDTO;
import ru.hetoiiblpb.repository.RoleRepository;
import ru.hetoiiblpb.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleRepository roleRepository;

    public UserServiceImpl(RoleRepository roleRepository, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;

    }

    private User fromDTOtoUser(UserDTO userDTO) {
        User user = new User(userDTO.getLogin(),
                userDTO.getPassword(),
                userDTO.getUsername(),
                userDTO.getMail());
        user.setId(userDTO.getId());
        String[] roles = userDTO.getRoles().split(",");
        Set<Role> roleSet = new HashSet<>();
        for (String role : roles) {
            roleSet.add(roleRepository.findByRole(role));
        }
        user.setRoles(roleSet);
        if (!user.getLogin().isEmpty()) {
            if (!user.getPassword().isEmpty()) {
                user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            } else {
                user.setPassword(userRepository.getById(user.getId()).getPassword());
            }
            user.setActive(true);
            return user;
        }
        return user;
    }

    @Override
    public boolean isExistLogin(String login) {
        return userRepository.existsByLogin(login);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public boolean addUser(UserDTO userDTO) {
        User user = fromDTOtoUser(userDTO);
        if (!userRepository.existsByLogin(user.getLogin())) {
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
    public boolean updateUser(UserDTO userDTO) {
        userRepository.save(fromDTOtoUser(userDTO));
        return true;
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
