package com.example.deliveryapp.domain.order.entity;

import com.example.deliveryapp.domain.menu.entity.Menu;
import com.example.deliveryapp.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    private BigDecimal price;

    private int quantity;

    @Builder
    public OrderItem(
            Order order,
            Menu menu,
            BigDecimal price,
            int quantity
    ) {
        this.order = order;
        this.menu = menu;
        this.price = price;
        this.quantity = quantity;
    }
}
