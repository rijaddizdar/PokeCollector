package com.rijad.pokecollector;
import jakarta.persistence.*;


@Entity
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String fname;
    private String lname;
    @Column(unique = true)
    private String email;
    private String passwordHash;
    @Column(unique = true)
    private String username;

    Owner() {}
    Owner(String fname, String lname, String email, String passwordHash, String username) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.passwordHash = passwordHash;
        this.username = username;
    }
    public int getId() {
        return id;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }
}
