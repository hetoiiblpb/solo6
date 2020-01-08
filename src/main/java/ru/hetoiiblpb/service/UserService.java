package ru.hetoiiblpb.service;

import org.springframework.transaction.annotation.Transactional;
import ru.hetoiiblpb.model.User;
import ru.hetoiiblpb.model.UserDTO;

import java.util.List;


public interface UserService {

    @Transactional
    boolean isExistLogin(String login);

//    @Transactional
//    boolean notNullDataUser(User user);

    @Transactional
    List<User> getAllUsers();

    List<UserDTO> getAllUserDTOs();

    @Transactional
    boolean addUser(UserDTO userDTO);

    @Transactional
    boolean deleteUser(Long id);

    @Transactional
    boolean updateUser(UserDTO userDTO);

    @Transactional
    User getUserById(Long id);

    @Transactional
    User getUserByName(String name);

}
