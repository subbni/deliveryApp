package com.example.deliveryapp.domain.menu.entity;

import com.example.deliveryapp.domain.store.entity.Store;
import com.example.deliveryapp.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "menus")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    private String name;

    private BigDecimal price;

    private String description;

    @Enumerated(EnumType.STRING)
    private MenuStatus status;

    @Builder
    public Menu(
            Store store,
            String name,
            BigDecimal price,
            String description,
            MenuStatus status
    ) {
        this.store = store;
        this.name = name;
        this.price = price;
        this.description = description;
        this.status = status;
    }
}
