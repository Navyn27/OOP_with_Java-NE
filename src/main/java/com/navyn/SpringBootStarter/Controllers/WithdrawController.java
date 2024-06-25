package com.navyn.SpringBootStarter.Controllers;

import com.navyn.SpringBootStarter.Services.JwtService;
import com.navyn.SpringBootStarter.Enums.TransactionType;
import com.navyn.SpringBootStarter.Models.Customer;
import com.navyn.SpringBootStarter.Models.WithdrawMgmtBanking;
import com.navyn.SpringBootStarter.Payload.WithdrawalDTO;
import com.navyn.SpringBootStarter.ServiceImpls.CustomerServiceImpl;
import com.navyn.SpringBootStarter.ServiceImpls.WithdrawalServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/withdraw")
public class WithdrawController {

    private WithdrawalServiceImpl withdrawalService;
    private CustomerServiceImpl customerService;
    private JwtService jwtService;

    @PostMapping
    public ResponseEntity<WithdrawMgmtBanking> withdraw(@Valid @RequestBody WithdrawalDTO request) {
        String token = request.getToken();
        Optional<Customer> savedCustomerOptional = customerService.findByEmail(jwtService.extractUsername(token));
        if(savedCustomerOptional.isPresent()) {
            Customer savedCustomer = savedCustomerOptional.get();

            WithdrawMgmtBanking withdrawMgmtBanking = new WithdrawMgmtBanking();
            withdrawMgmtBanking.setCustomer(savedCustomer);
            withdrawMgmtBanking.setAccount(savedCustomer.getAccount());

            withdrawMgmtBanking.setType(TransactionType.WITHDRAW);
            withdrawMgmtBanking.setAmount(request.getAmount());
            try{
                return ResponseEntity.ok(withdrawalService.save(withdrawMgmtBanking));
            }
            catch(Exception e){
                e.printStackTrace();
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }
}
