package com.csi.controller;

import com.csi.exception.UserNotFound;
import com.csi.model.JwtRequest;
import com.csi.model.UserDetailsInfo;
import com.csi.repo.UserDetailsRepo;
import com.csi.service.CustomUserDetailsService;
import com.csi.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class AppController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsRepo userDetailsRepo;

    @GetMapping("/")
    public String newService() {
        return "Spring Boot JWT";
    }

    @PostMapping("/save")
    public ResponseEntity<UserDetailsInfo> saveUser(@RequestBody UserDetailsInfo userDetailsInfo) {
        return new ResponseEntity<>(userDetailsRepo.save(userDetailsInfo), HttpStatus.CREATED);
    }

    @PostMapping("/authenticate")
    public String generateToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                    (jwtRequest.getUserName(), jwtRequest.getUserPassword()));
        } catch (UsernameNotFoundException ex) {
            ex.printStackTrace();
            throw new Exception("Incorrect username/password");
        }
        return jwtUtil.generateToken(jwtRequest.getUserName());
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserDetailsInfo>> getAllData() {
        return new ResponseEntity<>(userDetailsRepo.findAll(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable int userId) {
        userDetailsRepo.deleteById(userId);
        return new ResponseEntity<>("User Deleted", HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUser(@PathVariable int userId) {
        return new ResponseEntity<>(userDetailsRepo.findById(userId), HttpStatus.OK);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable int userId, @RequestBody UserDetailsInfo userDetailsInfo)
            throws UserNotFound {
        UserDetailsInfo user = userDetailsRepo.findById(userId).orElseThrow(() -> new UserNotFound("User Not Found"));
        user.setUserName(userDetailsInfo.getUserName());
        user.setUserPassword(userDetailsInfo.getUserPassword());
        return new ResponseEntity<>(userDetailsRepo.save(user), HttpStatus.OK);
    }
}
