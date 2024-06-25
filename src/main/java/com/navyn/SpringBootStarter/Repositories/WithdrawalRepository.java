package com.navyn.SpringBootStarter.Repositories;

import com.navyn.SpringBootStarter.Models.WithdrawMgmtBanking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WithdrawalRepository extends JpaRepository<WithdrawMgmtBanking, Long> {
}
