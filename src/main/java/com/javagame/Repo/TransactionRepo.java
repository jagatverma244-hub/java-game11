package com.javagame.Repo;

import com.javagame.Entity.Transaction;
import com.javagame.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction,Long> {
    @Query("SELECT MAX(t.depositNumber) FROM Transaction t WHERE t.user.UId = :userId")
    Integer findMaxDepositNumberByUser(@Param("userId") Long userId);
    Transaction findByUTRNumber(String utrNumber);

    void findByUser(User user);

    List<Transaction> findAllByUser_UId(Long uId);
//    List<Transaction> findAllByUser(User user);
}
