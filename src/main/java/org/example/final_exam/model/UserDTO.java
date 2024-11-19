package org.example.final_exam.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "users")
public class UserDTO implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username",unique = true)
    private String username;

    @Column(name = "nickname")
    private String nickname;


    @Column(name = "role")
    private String role = "user";
}
