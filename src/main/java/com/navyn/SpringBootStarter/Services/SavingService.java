package com.navyn.SpringBootStarter.Services;

import com.navyn.SpringBootStarter.Models.Customer;
import com.navyn.SpringBootStarter.Models.SavingsMgmtBanking;

public interface SavingService {
    public SavingsMgmtBanking save(String customerFirstName, String accountNumber, Double customerLastAmount, String customerEmail, SavingsMgmtBanking savingsMgmtBanking);
}
