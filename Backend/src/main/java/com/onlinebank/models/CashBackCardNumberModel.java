package com.onlinebank.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Denis Durbalov
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "cashback_Card_Number")
public class CashBackCardNumberModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "card_number")
    private BigDecimal accountNumber;

    @Column(name = "cashback_percentage")
    private Float cashbackPercentage;

    @ManyToMany(mappedBy = "cashBackCardNumberModels")
    private List<AccountModel> accountModels;

    public CashBackCardNumberModel(String name, String description, BigDecimal accountNumber, Float cashbackPercentage) {
        this.name = name;
        this.description = description;
        this.accountNumber = accountNumber;
        this.cashbackPercentage = cashbackPercentage;
    }
}