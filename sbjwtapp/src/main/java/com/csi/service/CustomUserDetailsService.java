package com.csi.service;

import com.csi.model.UserDetailsInfo;
import com.csi.repo.UserDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserDetailsRepo userDetailsRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetailsInfo userDetailsInfo = userDetailsRepo.findByUserName(username);
        return new User(userDetailsInfo.getUserName(), userDetailsInfo.getUserPassword(), new ArrayList<>());
    }
}
