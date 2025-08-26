package com.javagame.Repo;

import com.javagame.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Long> {

    boolean existsByUEmail(String uEmail);

    boolean existsByMobileNumber(String mobileNumber);

    Optional<User> findByReferralCode(String referralCode);

    User findByUEmail(String uEmail);

    User findByRole(String role);


//    @Query("SELECT  u from User  u where "+"LOWER(u.UEmail) like lower(concat('%',:keyword,'%'))"+"or LOWER(u.mobileNumber)like lower(concat(:keyword,'%'))")
//
//    Page<User> searchUser(@Param("keyword") String keyword, Pageable pageable);
Page<User> findByUEmailContainingIgnoreCaseOrMobileNumberContaining(String email, String mobile, Pageable pageable);

}
