package ru.hetoiiblpb.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hetoiiblpb.model.UserDTO;
import ru.hetoiiblpb.service.UserService;

import java.util.List;

@org.springframework.web.bind.annotation.RestController

@RequestMapping(value = "/restapi")

public class RestController {
    private final UserService userService;

    public RestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/allUsers")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUserDTOs(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return new ResponseEntity<>(UserDTO.fromUserToDTO(userService.getUserById(id)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO userDTO) {
        System.out.println("полученный юзер " + userDTO);
        userService.addUser(userDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteUserById(@PathVariable(value = "id") Long id) {
        userService.deleteUser(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<UserDTO> editUser(@RequestBody UserDTO userDTO) {
        if (userDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userService.updateUser(userDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
