package com.navyn.SpringBootStarter.Services;

import com.navyn.SpringBootStarter.Models.Customer;
import com.navyn.SpringBootStarter.Payload.CustomerDTO;

import java.util.Optional;


public interface CustomerService {
    public Optional<Customer> findByEmail(String email);
    public Optional<Customer> findByAccount(String account);
    public Customer updateBalanceByWithdraw(Customer customer, Double balanceChange);
    public Customer updateLastUpdateDateTime(Customer customer);
    public Customer updateBalanceBySaving(Customer customer, Double balanceChange);
}
