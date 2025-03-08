package com.example.deliveryapp.domain.wallet.repository;

import com.example.deliveryapp.domain.wallet.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
