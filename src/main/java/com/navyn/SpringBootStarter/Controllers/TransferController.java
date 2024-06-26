package com.navyn.SpringBootStarter.Controllers;

import com.navyn.SpringBootStarter.Enums.ResponseType;
import com.navyn.SpringBootStarter.Enums.TransactionType;
import com.navyn.SpringBootStarter.Exceptions.AuthenticationFailedException;
import com.navyn.SpringBootStarter.Exceptions.NotEnoughFundsException;
import com.navyn.SpringBootStarter.Models.Customer;
import com.navyn.SpringBootStarter.Models.SavingsMgmtBanking;
import com.navyn.SpringBootStarter.Models.WithdrawMgmtBanking;
import com.navyn.SpringBootStarter.Payload.Response;
import com.navyn.SpringBootStarter.Payload.TransferDTO;
import com.navyn.SpringBootStarter.ServiceImpls.CustomerServiceImpl;
import com.navyn.SpringBootStarter.ServiceImpls.SavingServiceImpl;
import com.navyn.SpringBootStarter.ServiceImpls.WithdrawalServiceImpl;
import com.navyn.SpringBootStarter.Services.JwtService;
import com.navyn.SpringBootStarter.Utils.ExceptionHandlerUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/transaction/transfer")
public class TransferController {

    @Autowired
    private WithdrawalServiceImpl withdrawalService;
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
    public ResponseEntity<Response> transferDeposit(@Valid @RequestBody TransferDTO request, HttpServletRequest headerRequest) {
        final String authHeader = headerRequest.getHeader("Authorization");

        try {
        String token = authHeader.substring(7);
        String customerEmail = jwtService.extractUsername(token);
        Optional<Customer> savedCustomerOptional = Optional.ofNullable(customerService.findByEmail(customerEmail).orElseThrow(() -> new AuthenticationFailedException("The user is not authenticated")));
        Optional<Customer> destinationCustomerOptional = Optional.ofNullable(customerService.findByEmail(request.getDestinationCustomerEmail()).orElseThrow(() -> new BadCredentialsException("The requested user does not exist")));

        if (savedCustomerOptional.isPresent() && destinationCustomerOptional.isPresent()) {
            Customer savedCustomer = savedCustomerOptional.get();
            Customer destinationSavedCustomer = destinationCustomerOptional.get();

            WithdrawMgmtBanking withdrawMgmtBanking = new WithdrawMgmtBanking();
            withdrawMgmtBanking.setCustomer(savedCustomer);
            withdrawMgmtBanking.setAccount(savedCustomer.getAccount());
            withdrawMgmtBanking.setType(TransactionType.OUT_TRANSFER);
            withdrawMgmtBanking.setBankingDateTime(getCurrentDate());

            if(request.getAmount()>savedCustomer.getBalance()) throw new NotEnoughFundsException("You don't have enough funds to withdraw the specified amount");
            withdrawMgmtBanking.setAmount(request.getAmount());

            SavingsMgmtBanking savingsMgmtBanking = new SavingsMgmtBanking();
            savingsMgmtBanking.setCustomer(destinationSavedCustomer);
            savingsMgmtBanking.setAccount(savedCustomer.getAccount());

            savingsMgmtBanking.setType(TransactionType.IN_TRANSFER);
            savingsMgmtBanking.setAmount(request.getAmount());
            savingsMgmtBanking.setBankingDateTime(getCurrentDate());

            withdrawMgmtBanking = withdrawalService.save(savedCustomer.getFirstname(), savedCustomer.getAccount(), savedCustomer.getBalance(),savedCustomer.getEmail(),withdrawMgmtBanking);
            savingsService.save(destinationSavedCustomer.getFirstname(), destinationSavedCustomer.getAccount(), destinationSavedCustomer.getBalance(),destinationSavedCustomer.getEmail(),savingsMgmtBanking);

            customerService.updateBalanceByWithdraw(savedCustomer, request.getAmount());
            customerService.updateBalanceBySaving(destinationSavedCustomer, request.getAmount());
            customerService.updateLastUpdateDateTime(savedCustomer);
            customerService.updateLastUpdateDateTime(destinationSavedCustomer);
            return ResponseEntity.status(200).body(new Response().setResponseType(ResponseType.SUCCESS).setPayload(ResponseEntity.ok(withdrawMgmtBanking)));
        }
        } catch (Exception e) {
            return ExceptionHandlerUtil.handleException(e);
        }
        return ResponseEntity.badRequest().build();
    }
}