package com.navyn.SpringBootStarter.Controllers;

import com.navyn.SpringBootStarter.Enums.ResponseType;
import com.navyn.SpringBootStarter.Enums.TransactionType;
import com.navyn.SpringBootStarter.Exceptions.AuthenticationFailedException;
import com.navyn.SpringBootStarter.Exceptions.NotEnoughFundsException;
import com.navyn.SpringBootStarter.Models.Customer;
import com.navyn.SpringBootStarter.Models.WithdrawMgmtBanking;
import com.navyn.SpringBootStarter.Payload.Response;
import com.navyn.SpringBootStarter.Payload.WithdrawalDTO;
import com.navyn.SpringBootStarter.ServiceImpls.CustomerServiceImpl;
import com.navyn.SpringBootStarter.ServiceImpls.WithdrawalServiceImpl;
import com.navyn.SpringBootStarter.Services.JwtService;
import com.navyn.SpringBootStarter.Utils.ExceptionHandlerUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/transaction/withdraw")
public class WithdrawController {

    @Autowired
    private WithdrawalServiceImpl withdrawalService;
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
    public ResponseEntity<Response> withdraw(@Valid @RequestBody WithdrawalDTO request, HttpServletRequest headerRequest) {

        final String authHeader= headerRequest.getHeader("Authorization");
        try{
        String token =authHeader.substring(7);
        String customerEmail = jwtService.extractUsername(token);
        System.out.println(customerEmail);
        Optional<Customer> savedCustomerOptional = Optional.ofNullable(customerService.findByEmail(customerEmail).orElseThrow(() -> new AuthenticationFailedException("The requested user does not exist")));
        if(savedCustomerOptional.isPresent()) {
            Customer savedCustomer = savedCustomerOptional.get();

            WithdrawMgmtBanking withdrawMgmtBanking = new WithdrawMgmtBanking();
            withdrawMgmtBanking.setCustomer(savedCustomer);
            withdrawMgmtBanking.setAccount(savedCustomer.getAccount());

            withdrawMgmtBanking.setType(TransactionType.WITHDRAW);
            if(request.getAmount()>savedCustomer.getBalance()) throw new NotEnoughFundsException("You don't have enough funds to withdraw the specified amount");
            withdrawMgmtBanking.setAmount(request.getAmount());
            withdrawMgmtBanking.setBankingDateTime(getCurrentDate());

                withdrawMgmtBanking = withdrawalService.save(savedCustomer.getFirstname(), savedCustomer.getAccount(), savedCustomer.getBalance(),savedCustomer.getEmail(),withdrawMgmtBanking);;
                customerService.updateBalanceByWithdraw(savedCustomer, request.getAmount());
                customerService.updateLastUpdateDateTime(savedCustomer);

                return ResponseEntity.status(200).body(new Response().setResponseType(ResponseType.SUCCESS).setPayload(ResponseEntity.ok(withdrawMgmtBanking)));
            }
        } catch (Exception e) {
            return ExceptionHandlerUtil.handleException(e);
        }
        return null;
    }
}
