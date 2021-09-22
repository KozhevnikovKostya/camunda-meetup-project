package com.example.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "account_claim")
public class AccountClaim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String userName;
    @Column(name = "client_name")
    private String clientName;
    @Column(name = "state")
    private DocumentState state;
    @Column(name = "vip")
    private boolean vip;
    @Column(name = "account_number")
    private String accountNumber;
    @Column(name = "error_message")
    private String errorMessage;


}
