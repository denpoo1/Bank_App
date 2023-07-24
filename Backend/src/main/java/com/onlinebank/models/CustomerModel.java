package com.onlinebank.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Denis Durbalov
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "customer")
public class CustomerModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "password")
    private String password;

    @Column(name = "username")
    private String username;

    @OneToOne(mappedBy = "customerModel", cascade = CascadeType.ALL)
    private AccountModel accountModel;

    @Enumerated(EnumType.STRING)
    private RoleModel roles;

    public CustomerModel(String firstName, String lastName, String email, String phone, String address, String password, String username) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.password = password;
        this.username = username;
    }
}