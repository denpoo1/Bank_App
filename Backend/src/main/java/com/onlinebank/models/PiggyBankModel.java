package com.onlinebank.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Denis Durbalov
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "piggy_bank")
public class PiggyBankModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "amount")
    private Integer amount;

    @Column
    private int limitAmount;

    @Column
    private String description;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "account_id")
    private AccountModel accountModel;

    public PiggyBankModel(AccountModel accountModel, Date createdAt, Integer amount) {
        this.accountModel = accountModel;
        this.createdAt = createdAt;
        this.amount = amount;
    }
}