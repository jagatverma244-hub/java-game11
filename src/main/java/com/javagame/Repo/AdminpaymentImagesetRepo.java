package com.javagame.Repo;

import com.javagame.Entity.AdminSetpaymentImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminpaymentImagesetRepo extends JpaRepository<AdminSetpaymentImage, Integer> {
    AdminSetpaymentImage findTopByOrderByIdDesc();

}
