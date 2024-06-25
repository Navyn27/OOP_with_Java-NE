package com.navyn.SpringBootStarter.Repositories;

import com.navyn.SpringBootStarter.Models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByAccount(String account);
}
