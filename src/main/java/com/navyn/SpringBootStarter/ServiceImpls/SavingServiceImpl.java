package com.navyn.SpringBootStarter.ServiceImpls;

import com.navyn.SpringBootStarter.Models.SavingsMgmtBanking;
import com.navyn.SpringBootStarter.Repositories.SavingRepository;
import com.navyn.SpringBootStarter.Services.SavingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SavingServiceImpl implements SavingService {

    @Autowired
   public SavingRepository savingRepository;

    public SavingsMgmtBanking save(SavingsMgmtBanking savingsMgmtBanking){
        return savingRepository.save(savingsMgmtBanking);
    };
}
