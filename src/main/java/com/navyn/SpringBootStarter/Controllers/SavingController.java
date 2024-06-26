package com.navyn.SpringBootStarter.Controllers;

import com.navyn.SpringBootStarter.Enums.ResponseType;
import com.navyn.SpringBootStarter.Enums.TransactionType;
import com.navyn.SpringBootStarter.Exceptions.AuthenticationFailedException;
import com.navyn.SpringBootStarter.Models.Customer;
import com.navyn.SpringBootStarter.Models.SavingsMgmtBanking;
import com.navyn.SpringBootStarter.Payload.Response;
import com.navyn.SpringBootStarter.Payload.SavingDTO;
import com.navyn.SpringBootStarter.ServiceImpls.CustomerServiceImpl;
import com.navyn.SpringBootStarter.ServiceImpls.SavingServiceImpl;
import com.navyn.SpringBootStarter.Services.JwtService;
import com.navyn.SpringBootStarter.Utils.ExceptionHandlerUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/transaction/saving")
public class SavingController {

    @Autowired
    private SavingServiceImpl savingsService;
    @Autowired
    private CustomerServiceImpl customerService;

    @Autowired
    private JwtService jwtService;

    private final Date getCurrentDate(){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedTime = formatter.format(date);
        return date;
    }

    @PostMapping
    public ResponseEntity<Response> savingDeposit(@Valid @RequestBody SavingDTO request, HttpServletRequest headerRequest) {

        final String authHeader= headerRequest.getHeader("Authorization");
        try{
        String token =authHeader.substring(7);
        String customerEmail = jwtService.extractUsername(token);
        System.out.println(customerEmail);
        Optional<Customer> savedCustomerOptional = Optional.ofNullable(customerService.findByEmail(customerEmail).orElseThrow(() -> new AuthenticationFailedException("The user is not authenticated")));
        if(savedCustomerOptional.isPresent()) {
            Customer savedCustomer = savedCustomerOptional.get();

            SavingsMgmtBanking savingsMgmtBanking = new SavingsMgmtBanking();
            savingsMgmtBanking.setCustomer(savedCustomer);
            savingsMgmtBanking.setAccount(savedCustomer.getAccount());

            savingsMgmtBanking.setType(TransactionType.SAVING);
            savingsMgmtBanking.setAmount(request.getAmount());
            savingsMgmtBanking.setBankingDateTime(getCurrentDate());
            savingsMgmtBanking = savingsService.save(savedCustomer.getFirstname(),savedCustomer.getAccount(), savedCustomer.getBalance(),savedCustomer.getEmail(),savingsMgmtBanking);
            customerService.updateBalanceBySaving(savedCustomer, request.getAmount());
            customerService.updateLastUpdateDateTime(savedCustomer);

            return ResponseEntity.status(200).body(new Response().setResponseType(ResponseType.SUCCESS).setPayload(ResponseEntity.ok(savingsMgmtBanking)));
            }
        }
        catch (Exception e){
            return ExceptionHandlerUtil.handleException(e);
        }
        return null;
    }
}
