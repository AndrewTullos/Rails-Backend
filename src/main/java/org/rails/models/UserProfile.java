package org.rails.models;

import java.time.LocalDateTime;
import java.util.Objects;

public class UserProfile {
    private int userId;
    private String username;
    private String email;
    private String password;
    private String profilePicture;
    private String firstName;
    private String lastName;
    private String city;
    private String state;
    private String postalCode;
    private LocalDateTime createdAt;

    public UserProfile() {
    }

    public UserProfile(int userId, String username, String email, String password, String profilePicture, String firstName, String lastName, String city, String state, String postalCode) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.profilePicture = profilePicture;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime setCreatedAt(LocalDateTime createdAt) {
        return this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserProfile that = (UserProfile) o;
        return userId == that.userId && Objects.equals(username, that.username) && Objects.equals(email, that.email) && Objects.equals(password, that.password) && Objects.equals(profilePicture, that.profilePicture) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(city, that.city) && Objects.equals(state, that.state) && Objects.equals(postalCode, that.postalCode) && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username, email, password, profilePicture, firstName, lastName, city, state, postalCode, createdAt);
    }
}
