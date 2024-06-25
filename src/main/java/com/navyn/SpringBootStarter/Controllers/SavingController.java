package com.navyn.SpringBootStarter.Controllers;

import com.navyn.SpringBootStarter.Enums.TransactionType;
import com.navyn.SpringBootStarter.Models.Customer;
import com.navyn.SpringBootStarter.Models.SavingsMgmtBanking;
import com.navyn.SpringBootStarter.Payload.SavingDTO;
import com.navyn.SpringBootStarter.ServiceImpls.CustomerServiceImpl;
import com.navyn.SpringBootStarter.ServiceImpls.SavingServiceImpl;
import com.navyn.SpringBootStarter.Services.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/saving")
public class SavingController {

    @Autowired
    private SavingServiceImpl withdrawalService;
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
    public ResponseEntity<SavingsMgmtBanking> withdraw(@Valid @RequestBody SavingDTO request) {
        String token = request.getToken();
        Optional<Customer> savedCustomerOptional = customerService.findByEmail(request.getCustomerEmail());
        if(savedCustomerOptional.isPresent()) {
            Customer savedCustomer = savedCustomerOptional.get();

            SavingsMgmtBanking savingsMgmtBanking = new SavingsMgmtBanking();
            savingsMgmtBanking.setCustomer(savedCustomer);
            savingsMgmtBanking.setAccount(savedCustomer.getAccount());

            savingsMgmtBanking.setType(TransactionType.SAVING);
            savingsMgmtBanking.setAmount(request.getAmount());
            savingsMgmtBanking.setBankingDateTime(getCurrentDate());
            try{
                return ResponseEntity.ok(withdrawalService.save(savingsMgmtBanking));
            }
            catch(Exception e){
                e.printStackTrace();
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }
}
