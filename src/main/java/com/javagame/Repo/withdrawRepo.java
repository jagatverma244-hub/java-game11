package com.javagame.Repo;

import com.javagame.Entity.withdraw;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface withdrawRepo extends JpaRepository<withdraw,Long> {
    @Query("SELECT MAX(t.withdrawNumber) FROM withdraw t WHERE t.user.UId = :userId")
    Integer findMaxDepositNumberByUser(@Param("userId") Long userId);

    List<withdraw> findAllByUser_UId(Long uId);
}
