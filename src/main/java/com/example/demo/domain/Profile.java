package com.example.demo.domain;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@SQLDelete(sql = "UPDATE deleted" +
        "SET deleted = now()" +
        "WHERE id = ?")
@Table(name = "profiles")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(notes = "Name of profile owner")
    private String name;

    @ApiModelProperty(notes = "Email of profile owner. Must be unqiue")
    private String email;

    @ApiModelProperty(notes = "Age of profile owner")
    private Integer age;

    private Timestamp created;

    public Profile() {}

    public Profile(Long id, String name, String email, Integer age) {
        this.id = id;
        this.name = name;
        this.email = email.toLowerCase();
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Integer getAge() {
        return age;
    }

    public Timestamp getCreated() {
        return created;
    }

    @PrePersist
    protected void onCreate() {
        email = email.toLowerCase();
        created = new Timestamp(new Date().getTime());
    }
}
