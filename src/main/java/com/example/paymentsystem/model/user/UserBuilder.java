package com.example.paymentsystem.model.user;

public class UserBuilder {
    private long id;
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private boolean blocked;
    private UserType userType;

    public UserBuilder() {

    }

    public UserBuilder setId(long id) {
        this.id = id;
        return this;
    }

    public UserBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserBuilder setLogin(String login) {
        this.login = login;
        return this;
    }

    public UserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder setBlocked(boolean blocked) {
        this.blocked = blocked;
        return this;
    }

    public UserBuilder setUserType(UserType userType) {
        this.userType = userType;
        return this;
    }

    public User build() {
        return new User(id, firstName, lastName, login, password, blocked, userType);
    }
}
