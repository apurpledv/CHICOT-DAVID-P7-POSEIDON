package com.nnk.springboot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.DBUser;
import com.nnk.springboot.repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    UserRepository userRepo;

    public List<DBUser> getUsers() {
        return userRepo.findAll();
    }

    public DBUser getUser(int id) {
        return userRepo.getReferenceById(id);
    }

    public boolean saveUser(DBUser user) {
        userRepo.save(user);
        return true;
    }

    public boolean deleteUser(DBUser user) {
        userRepo.delete(user);
        return true;
    }
}
