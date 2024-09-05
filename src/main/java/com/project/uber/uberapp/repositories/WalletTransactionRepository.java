package com.project.uber.uberapp.repositories;

import com.project.uber.uberapp.entities.WalletTransactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletTransactionRepository extends JpaRepository<WalletTransactions, Long> {
}
