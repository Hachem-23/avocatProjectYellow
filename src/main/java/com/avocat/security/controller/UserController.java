package com.avocat.security.controller;

import com.avocat.security.service.UserService;
import com.avocat.security.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private UserService userService;
    @GetMapping("/all")
    public ResponseEntity<List<User>> getUsers(){
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<User> add(@RequestBody User user){
        return new ResponseEntity<>(userService.add(user),
                HttpStatus.CREATED);
    }
    @PutMapping("/patch")
    public ResponseEntity<User> patch(@RequestBody User user){
        return new ResponseEntity<>(userService.patch(user),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/user/{id}")
    public void delete(@PathVariable("id") Integer id){
        userService.delete(id);
    }



}
