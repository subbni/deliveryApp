package com.example.deliveryapp.domain.store.entity;

import com.example.deliveryapp.domain.user.entity.User;
import com.example.deliveryapp.domain.wallet.entity.Wallet;
import com.example.deliveryapp.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalTime;

@Entity
@Table(name = "stores")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private StoreCategory category;

    @Enumerated(EnumType.STRING)
    private StoreStatus status;

    private LocalTime openingTime;

    private LocalTime closingTime;

    private BigDecimal minOrderPrice;

    private BigDecimal deliveryFee;

    private Double averageRating = 0.0;

    @Builder
    public Store(
            User user,
            StoreCategory category,
            LocalTime openingTime,
            LocalTime closingTime,
            BigDecimal minOrderPrice,
            BigDecimal deliveryFee,
            StoreStatus status
    ) {
        this.user = user;
        this.category = category;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.minOrderPrice = minOrderPrice;
        this.deliveryFee = deliveryFee;
        this.status = status;
    }
}
