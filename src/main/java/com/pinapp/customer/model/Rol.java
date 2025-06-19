package com.pinapp.customer.model;


import jakarta.persistence.*;
import lombok.*;

@Table(name = "roles")
@Data
@AllArgsConstructor
@Builder
@Entity
@Setter
@Getter
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

}