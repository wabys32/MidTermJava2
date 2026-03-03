package com.arthurtokarev.bankmanagement.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "user_profiles")
@Data
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;
    private String phone;
    private LocalDate birthDate;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private BankUser bankUser;
}