package com.onlinebank.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author Denis Durbalov
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "account")
public class AccountModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToMany(mappedBy = "accountModel", cascade = CascadeType.ALL)
    private List<PiggyBankModel> piggyBankModels;

    @Column(name = "date_opened")
    private Date date;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private CustomerModel customerModel;

    @Column(name = "rounding_transaction_as_a_percentage")
    private float transactionRoundingPercentage;

    @OneToMany(mappedBy = "accountModel", cascade = CascadeType.ALL)
    private List<CreditCardModel> creditCardModels;

    @ManyToMany
    @JoinTable(
            name = "cashback_account",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "cashback_id")
    )
    private List<CashBackCardNumberModel> cashBackCardNumberModels;

    public AccountModel(Date date, float transactionRoundingPercentage) {
        this.date = date;
        this.transactionRoundingPercentage = transactionRoundingPercentage;
    }
}