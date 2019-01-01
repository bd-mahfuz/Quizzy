package com.example.mahfuz.quizzy.model;

import java.io.Serializable;

public class User implements Serializable{

    private String id;
    private String fullName;
    private String instituteName;
    private String email;
    private String password;
    private String gender;
    private String role;

    public User() {
    }

    public User(String id, String fullName, String instituteName, String email, String password, String gender, String role) {
        this.id = id;
        this.fullName = fullName;
        this.instituteName = instituteName;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
