package com.csi.controller;

import com.csi.model.JwtRequest;
import com.csi.model.Role;
import com.csi.model.UserDetailsInfo;
import com.csi.repo.RoleRepo;
import com.csi.repo.UserDetailsRepo;
import com.csi.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/public")
public class AppController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsRepo userDetailsRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepo roleRepo;

    @PostMapping("/signup")
    public ResponseEntity<UserDetailsInfo> saveUser(@RequestBody UserDetailsInfo userDetailsInfo) {
        userDetailsInfo.setUserPassword(passwordEncoder.encode(userDetailsInfo.getUserPassword()));
        Role role = roleRepo.findById(1002).get();
        userDetailsInfo.setRoles(Collections.singleton(role));
        return new ResponseEntity<>(userDetailsRepo.save(userDetailsInfo), HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<String> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                    (jwtRequest.getUserName(), jwtRequest.getUserPassword()));
        } catch (UsernameNotFoundException ex) {
            ex.printStackTrace();
            throw new Exception("Incorrect username/password");
        }
        return new ResponseEntity<>(jwtUtil.generateToken(jwtRequest.getUserName()), HttpStatus.OK);
    }
}
