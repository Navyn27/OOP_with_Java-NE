package com.navyn.SpringBootStarter.ServiceImpls;

import com.navyn.SpringBootStarter.Models.WithdrawMgmtBanking;
import com.navyn.SpringBootStarter.Payload.WithdrawalDTO;
import com.navyn.SpringBootStarter.Repositories.WithdrawalRepository;
import com.navyn.SpringBootStarter.Services.EmailService;
import com.navyn.SpringBootStarter.Services.WithdrawalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WithdrawalServiceImpl implements WithdrawalService {

    @Autowired
    public WithdrawalRepository withdrawalRepository;

    @Autowired
    EmailService emailService;

    public String emailContent;
    public WithdrawMgmtBanking save(String customerFirstName, String accountNumber, Double customerLastAmount, String customerEmail, WithdrawMgmtBanking withdrawal) {
        Double newAmount = customerLastAmount-withdrawal.getAmount();
        emailContent="Dear "+customerFirstName+",\n\nA withdrawal of "+withdrawal.getAmount()+" RWF has been successfully made on your account. \nAccount Number: "+accountNumber+"\n\nYour new balance is: "+newAmount+" RWF.\n\nThanks for using our Services\n\nBNR Development Team";
        emailService.sendEmail(customerEmail,"BANK TRANSACTION NOTIFICATION", emailContent);
        return withdrawalRepository.save(withdrawal);
    }
}
