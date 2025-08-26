package com.javagame.Repo;

import com.javagame.Entity.OtpToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpTokenRepo extends JpaRepository<OtpToken,Long> {
}
