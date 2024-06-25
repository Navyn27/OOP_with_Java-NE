package com.navyn.SpringBootStarter.Services;

import com.navyn.SpringBootStarter.Models.WithdrawMgmtBanking;
import com.navyn.SpringBootStarter.Payload.WithdrawalDTO;

public interface WithdrawalService {
    public WithdrawMgmtBanking save(WithdrawMgmtBanking withdrawal);
}
