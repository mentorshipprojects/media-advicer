package org.example.entity;

import java.util.Date;
import java.util.Objects;

public class Profile {
    private long user_id;
    private String name;
    private String password;
    private String email;
    private Date createdAt;
    private Date deletedAt;
    public Profile() {
    }

    public Profile(long user_id, String name, String password, String email, Date createdAt, Date deletedAt) {
        this.user_id = user_id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                ", deletedAt=" + deletedAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile user = (Profile) o;
        return user_id == user.user_id &&
                Objects.equals(name, user.name) &&
                Objects.equals(password, user.password) &&
                Objects.equals(email, user.email) &&
                Objects.equals(createdAt, user.createdAt) &&
                Objects.equals(deletedAt, user.deletedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, name, password, email, createdAt, deletedAt);
    }
}
