package com.nnk.springboot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    UserRepository userRepo;

    public List<User> getUsers() {
        return userRepo.findAll();
    }

    public User getUser(int id) {
        return userRepo.getReferenceById(id);
    }

    public boolean saveUser(User user) {
        userRepo.save(user);
        return true;
    }

    public boolean deleteUser(User user) {
        userRepo.delete(user);
        return true;
    }
}
