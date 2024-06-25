package com.navyn.SpringBootStarter.Services;

import com.navyn.SpringBootStarter.Models.Customer;
import com.navyn.SpringBootStarter.Payload.CustomerDTO;

import java.util.Optional;


public interface CustomerService {
    public Optional<Customer> findByEmail(String email);
    public Optional<Customer> findByAccount(String account);
    public Customer updateBalanceByWithdraw(CustomerDTO customer, Double balanceChange);
    public Customer updateLastUpdateDateTime(CustomerDTO customer);
    public Customer updateBalanceBySaving(CustomerDTO customer, Double balanceChange);
}
