package com.TechnicalTest.TechnicalExam.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "accounts")
@Getter
@Setter
public class AccountModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountNumber;

    private String accountType; // "Savings" or "Checking"

    private BigDecimal availableBalance = BigDecimal.ZERO;

    @ManyToOne
    @JoinColumn(name = "customer_number")
    private CustomerModel customer;


}
