package com.avocat.security.service;

import com.avocat.security.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User add(User user);
    List<User> getUsers();
    User patch(User user);
    void delete(Integer id);
    Optional<User> getById(Integer id) throws Exception;
}