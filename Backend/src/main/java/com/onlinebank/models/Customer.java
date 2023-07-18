package com.onlinebank.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotEmpty(message = "First name cannot be empty.")
    @Size(max = 30, message = "First name cannot exceed 30 characters.")
    @Column(name = "first_name")
    private String firstName;

    @NotEmpty(message = "Last name cannot be empty.")
    @Size(max = 30, message = "Last name cannot exceed 30 characters.")
    @Column(name = "last_name")
    private String lastName;

    @NotEmpty(message = "Email cannot be empty.")
    @Email(message = "Invalid email format.")
    @Column(name = "email")
    private String email;

    @NotEmpty(message = "Phone number cannot be empty.")
    @Size(max = 15, message = "Phone number cannot exceed 15 characters.")
    @Column(name = "phone")
    private String phone;

    @NotEmpty(message = "Address cannot be empty.")
    @Size(max = 50, message = "Address cannot exceed 50 characters.")
    @Column(name = "address")
    private String address;

    @NotEmpty(message = "Password cannot be empty.")
    @Size(max = 50, message = "Password cannot exceed 50 characters.")
    @Column(name = "password")
    private String password;

    @NotEmpty(message = "Username cannot be empty.")
    @Size(max = 50, message = "Username cannot exceed 50 characters.")
    @Column(name = "username")
    private String username;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private Account account;

    public Customer() {
    }

    public Customer(String firstName, String lastName, String email, String phone, String address, String password, String username) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.password = password;
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", account=" + account +
                '}';
    }
}