package com.jsbcrud.www.repository;

import com.jsbcrud.www.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByEmail(String email);
    Optional<Account> findById(Integer id);
    Optional<Account> findByEmailAndStatus(String email, Account.Status status);
}