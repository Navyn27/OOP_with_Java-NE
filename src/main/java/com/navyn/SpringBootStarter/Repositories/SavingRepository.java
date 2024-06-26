package com.navyn.SpringBootStarter.Repositories;

import com.navyn.SpringBootStarter.Models.SavingsMgmtBanking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingRepository extends JpaRepository<SavingsMgmtBanking, Long> {
}
