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

import java.util.*;


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
        User user = userRepository.getById(userDTO.getId());
        if (!(user.getLogin().equals(userDTO.getLogin()))) user.setLogin(userDTO.getLogin());
        if (!(user.getUsername().equals(userDTO.getUsername()))) user.setUsername(userDTO.getUsername());
        if (!(user.getMail().equals(userDTO.getMail()))) user.setMail(userDTO.getMail());
        if (!userDTO.getPassword().isEmpty() ^ bCryptPasswordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
            user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
            System.out.println("Устанавливаем пароль '" + userDTO.getPassword() + "'   ***********************************");
        }
        setRolesToUser(userDTO, user);
        System.out.println("Возвращаем такого юзера для update: " + user);
        return user;
    }

    private void setRolesToUser(UserDTO userDTO, User user) {
        if (userDTO.getRoles() == null || userDTO.getRoles().isEmpty()) {
            user.setRoles(Collections.singleton(new Role(2L, "USER")));
        } else {
            String[] roles = userDTO.getRoles()
                    .replaceAll("\\[", "")
                    .replaceAll("\\]", "")
                    .split(",");
            Set<Role> roleSet = new HashSet<>();
            for (String role : roles) {
                System.out.println("Находим роль изменяемого юзера - " + role);
                roleSet.add(roleRepository.findByRole(role));
            }
            user.setRoles(roleSet);
        }
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
    public List<UserDTO> getAllUserDTOs() {
        List<UserDTO> userDTOList = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            userDTOList.add(UserDTO.fromUserToDTO(user));
        }
        return userDTOList;
    }


    @Override
    public boolean addUser(UserDTO userDTO) {
        User user = new User(userDTO.getLogin(), bCryptPasswordEncoder.encode(userDTO.getPassword()), userDTO.getUsername(), userDTO.getMail());
        setRolesToUser(userDTO, user);
        user.setActive(true);
        if (!isExistLogin(user.getLogin())) {
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
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return userRepository.findByUsername(userName);
    }

}
