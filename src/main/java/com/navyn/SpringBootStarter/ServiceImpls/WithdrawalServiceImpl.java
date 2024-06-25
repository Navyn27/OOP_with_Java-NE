package com.navyn.SpringBootStarter.ServiceImpls;

import com.navyn.SpringBootStarter.Models.WithdrawMgmtBanking;
import com.navyn.SpringBootStarter.Payload.WithdrawalDTO;
import com.navyn.SpringBootStarter.Repositories.WithdrawalRepository;
import com.navyn.SpringBootStarter.Services.WithdrawalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WithdrawalServiceImpl implements WithdrawalService {

    @Autowired
    public WithdrawalRepository withdrawalRepository;

    public WithdrawMgmtBanking save(WithdrawMgmtBanking withdrawal) {
        return withdrawalRepository.save(withdrawal);
    }
}
