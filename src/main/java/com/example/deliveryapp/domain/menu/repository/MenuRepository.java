package com.example.deliveryapp.domain.menu.repository;

import com.example.deliveryapp.domain.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
