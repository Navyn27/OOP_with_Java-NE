package com.navyn.SpringBootStarter.Repositories;

import com.navyn.SpringBootStarter.Models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessagingRepository extends JpaRepository<Message, Long> {
}
