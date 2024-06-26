package com.navyn.SpringBootStarter.ServiceImpls;

import com.navyn.SpringBootStarter.Models.Customer;
import com.navyn.SpringBootStarter.Payload.CustomerDTO;
import com.navyn.SpringBootStarter.Repositories.CustomerRepository;
import com.navyn.SpringBootStarter.Services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    private final Date getCurrentDate(){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedTime = formatter.format(date);
        return date;
    }


    @Override
    public Optional<Customer> findByEmail(String email){
        return customerRepository.findByEmail(email);
    };

    @Override
    public Optional<Customer> findByAccount(String account) {
        return customerRepository.findByAccount(account);
    };

    @Override
    public Customer updateBalanceByWithdraw(Customer customer, Double balanceChange){
        customer.setBalance(customer.getBalance() - balanceChange);
        return customerRepository.save(customer);
    };

    @Override
    public Customer updateBalanceBySaving(Customer customer, Double balanceChange){
            customer.setBalance(customer.getBalance() + balanceChange);
            return customerRepository.save(customer);
    };

    @Override
    public Customer updateLastUpdateDateTime(Customer customer){
            customer.setLastUpdateDateTime(getCurrentDate());
            return customerRepository.save(customer);
    };

}
