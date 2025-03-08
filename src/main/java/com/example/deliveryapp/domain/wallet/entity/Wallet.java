package com.example.deliveryapp.domain.wallet.entity;

import com.example.deliveryapp.domain.user.entity.User;
import com.example.deliveryapp.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "wallets")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Wallet extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="user_id", unique = true, nullable = false)
    private User user;

    private BigDecimal balance;

    @Builder
    public Wallet(User user, BigDecimal balance) {
        this.user = user;
        this.balance = balance;
    }
}