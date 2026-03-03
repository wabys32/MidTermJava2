package com.arthurtokarev.bankmanagement.repository;

import com.arthurtokarev.bankmanagement.entity.BankUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<BankUser, Long> {
}