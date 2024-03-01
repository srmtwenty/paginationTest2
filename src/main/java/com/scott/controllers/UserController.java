package com.scott.controllers;


import com.scott.models.User;
import com.scott.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins="http://localhost:3000")
public class UserController {
    private final UserService userService;

    private UserController(UserService userService){
        this.userService=userService;
    }

    @PostMapping("/create")
    public User createUser(@RequestBody User user){
        return userService.createUser(user);
    }
    @GetMapping
    public List<User> getAllUsers(){
        return userService.findAllUsers();
    }
    @GetMapping("/{id}")
    public User findUserById(@PathVariable Long id){
        return userService.findUserById(id);
    }
    @PutMapping("/{id}/update")
    public User updateUser(@PathVariable Long id, @RequestBody User user){
        return userService.updateUser(id, user);
    }
    @DeleteMapping("/{id}/delete")
    public void deleteUser(@PathVariable Long id){
        userService.deleteById(id);
    }

    @GetMapping("/sort/{field}")
    public List<User> findAllUsersByName(@PathVariable String field){
        return userService.findAllUsersWithSortByName(field);
    }

    @GetMapping("/{offset}/{pageSize}")
    public Page<User> findAllUsersPagination(@PathVariable int offset, @PathVariable int pageSize){
        return userService.findAllUsersPagination(offset, pageSize);
    }
    @GetMapping("/{offset}/{pageSize}/{field}")
    public Page<User> findAllUsersPagination(@PathVariable int offset, @PathVariable int pageSize, @PathVariable String field){
        return userService.findAllUsersPaginationAndSorting(offset, pageSize, field);
    }

}
