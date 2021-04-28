package com.inflearn.demo.jpa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

    @Id @GeneratedValue
    private Integer id;

    @Column
    private String name;

    @Column
    private String password;

    @Column
    private Date createdAt;

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();
}