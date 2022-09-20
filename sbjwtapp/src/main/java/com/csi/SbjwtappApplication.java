package com.csi;

import com.csi.model.Role;
import com.csi.repo.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
@EnableWebMvc

public class SbjwtappApplication {


    @Autowired
    RoleRepo roleRepo;

    @PostConstruct
    public void addRoles() {
        List<Role> roleList = Stream.of(new Role(1001, "ROLE_ADMIN"),
                new Role(1002, "ROLE_USER")).collect(Collectors.toList());
        roleRepo.saveAll(roleList);
    }

    public static void main(String[] args) {
        SpringApplication.run(SbjwtappApplication.class, args);
    }

}
