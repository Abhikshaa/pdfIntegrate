package com.Buster.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="users")

public class User {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private long id;
     private String name;
     private String email;
     private String body;

}

