package entity;

import entity.util.Role;

import java.util.List;

public class User {

    private long id;
    private final String firstName;
    private final String secondName;
    private final String email;
    private final List<String> phones;
    private final List<Role> roles;

    public User(String firstName, String secondName, String email, List<String> phones, List<Role> roles) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.phones = phones;
        this.roles = roles;
    }

    public User(long id, String firstName, String secondName, String email, List<String> phones, List<Role> roles) {
        this(firstName, secondName, email, phones, roles);
        this.id = id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getEmail() {
        return email;
    }

    public List<String> getPhones() {
        return phones;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public long getId() {
        return id;
    }

    public String asString() {
        return toString();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", email='" + email + '\'' +
                ", phones=" + phones +
                ", roles=" + roles +
                '}';
    }
}
