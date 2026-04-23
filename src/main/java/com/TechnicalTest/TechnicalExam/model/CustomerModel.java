package com.TechnicalTest.TechnicalExam.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "customer")
@Getter
@Setter
public class CustomerModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerNumber;

    private String customerName;
    private String customerMobile;
    private String customerEmail;
    private String address1;
    private String address2;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<AccountModel> accounts = new ArrayList<>();


}
