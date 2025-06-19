package com.pinapp.customer.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

//    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
//    @CollectionTable(name = "usuario_roles", joinColumns = @JoinColumn(name = "usuario_id"))
//    @Enumerated(EnumType.STRING)
//    private Set<Role> roles = EnumSet.noneOf(Role.class);
}
