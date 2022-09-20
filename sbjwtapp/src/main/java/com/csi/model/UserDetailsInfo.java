package com.csi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "UserDetails")

public class UserDetailsInfo {

    @Id
    @GeneratedValue
    private int userId;

    private String userName;

    private String userEmail;

    private String userPassword;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "UserRole",
            joinColumns = @JoinColumn(name = "UserID", referencedColumnName = "userId"),
            inverseJoinColumns = @JoinColumn(name = "RoleID", referencedColumnName = "roleId")
    )
    private Set<Role> roles = new HashSet<>();
}
