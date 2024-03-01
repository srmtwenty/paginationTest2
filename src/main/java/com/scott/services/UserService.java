package com.scott.services;
import com.scott.models.User;
import com.scott.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    private UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public User createUser(User user){
        return userRepository.save(user);
    }
    public List<User> findAllUsers(){
        return userRepository.findAll();
    }
    public User findUserById(Long id){
        return userRepository.findById(id).orElseThrow(()->new RuntimeException("User has not been found!"));
    }
    public User updateUser(Long id, User user){
        User updateU=this.findUserById(id);
        updateU.setName(user.getName());
        return userRepository.save(updateU);
    }

    public void deleteById(Long id){
        userRepository.deleteById(id);
    }

    public List<User> findAllUsersWithSortByName(String field){
        List<User> users=userRepository.findAll(Sort.by(Sort.Direction.ASC, field));
        return users;
    }
    public Page<User> findAllUsersPagination(int offset, int pageSize){
        Page<User> users=userRepository.findAll(PageRequest.of(offset, pageSize));
        return users;
    }

    public Page<User> findAllUsersPaginationAndSorting(int offset, int pageSize, String field){
        Page<User> users=userRepository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field)));
        return users;
    }
}
