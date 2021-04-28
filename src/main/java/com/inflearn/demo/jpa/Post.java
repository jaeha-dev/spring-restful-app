package com.inflearn.demo.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Post {

    @Id @GeneratedValue
    private Integer id;

    @Column
    private String description;

    @JsonIgnore
    // User : Post -> 1 : (0~N), Main : Sub -> Parent : Child
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}