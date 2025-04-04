package org.example.generate_number.entity;

public class User {
    private int id;
    private String password;
    private String email;
    private java.sql.Timestamp created_at;
    private Role role;

    public User() {

    }

    public User(int id, String password, String email, java.sql.Timestamp created_at, Role role) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.created_at = created_at;
        this.role = role;
    }

    public User(String password, String email) {
        this.password = password;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public java.sql.Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(java.sql.Timestamp created_at) {
        this.created_at = created_at;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
