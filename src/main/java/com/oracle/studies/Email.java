package com.oracle.studies;

import javax.annotation.Generated;
import javax.persistence.*;

@Entity
@Table(name="emails")
public class Email {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    String firstname;
    String lastname;
    @Column
    String name;
    @Column
    String email;

    public Email(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = firstname + "." + lastname + "@acme.com";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
