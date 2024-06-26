package com.navyn.SpringBootStarter.ServiceImpls;

import com.navyn.SpringBootStarter.Models.SavingsMgmtBanking;
import com.navyn.SpringBootStarter.Repositories.SavingRepository;
import com.navyn.SpringBootStarter.Services.EmailService;
import com.navyn.SpringBootStarter.Services.SavingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SavingServiceImpl implements SavingService {

    @Autowired
   public SavingRepository savingRepository;

    @Autowired
    public EmailService emailService;

    public String emailContent;

    public SavingsMgmtBanking save(String customerFirstName, String accountNumber, Double customerLastAmount, String customerEmail, SavingsMgmtBanking savingsMgmtBanking){
        Double newAmount = customerLastAmount+savingsMgmtBanking.getAmount();
        emailContent="Dear "+customerFirstName+",\n\nA deposit of "+savingsMgmtBanking.getAmount()+" RWF has been successfully made on your account. \nAccount Number: "+accountNumber+"\n\nYour new balance is: "+newAmount+" RWF.\n\nThanks for using our Services\n\nBNR Development Team";
        emailService.sendEmail(customerEmail,"BANK TRANSACTION NOTIFICATION", emailContent);
        return savingRepository.save(savingsMgmtBanking);
    };
}
