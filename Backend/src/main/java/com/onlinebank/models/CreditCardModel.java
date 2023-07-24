package com.onlinebank.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Denis Durbalov
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "credit_card")
public class CreditCardModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "card_number")
    private BigDecimal cardNumber;

    @Column(name = "cvv")
    private int cvv;

    @Column(name = "billing_address")
    private String billingAddress;

    @Column(name = "credit_limit")
    private int creditLimit;

    @Column(name = "balance")
    private int balance;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "expiration_date")
    private Date expirationDate;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private AccountModel accountModel;

    public CreditCardModel(BigDecimal cardNumber, int cvv, String billingAddress, Integer creditLimit, Integer balance, Date createdAt, Date expirationDate, AccountModel accountModel) {
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.billingAddress = billingAddress;
        this.creditLimit = creditLimit;
        this.balance = balance;
        this.createdAt = createdAt;
        this.expirationDate = expirationDate;
        this.accountModel = accountModel;
    }
}