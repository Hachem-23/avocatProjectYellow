package com.avocat.security.service;

import com.avocat.security.repository.UserRepository;
import com.avocat.security.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;


    @Override
    public User add(User user) {

        return userRepository.save(user);
    }

    @Override
    public List<User> getUsers() {

        return userRepository.findAll();
    }

    @Override
    public User patch(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(Integer id) {
        userRepository.deleteById(id);

    }

    @Override
    public Optional<User> getById(Integer id) throws Exception {
        return Optional.ofNullable(userRepository.findById(id)
                .orElseThrow(() -> new Exception("User not found!!")));
    }
}
