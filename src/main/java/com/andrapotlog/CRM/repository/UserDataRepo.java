package com.andrapotlog.CRM.repository;

import com.andrapotlog.CRM.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserDataRepo extends JpaRepository<UserData, Long> {
    boolean existsByCnp(String cnp);

    UserData findByEmail(String email);

    @Query("SELECT u.email FROM UserData u " +
            "WHERE u.sendEmail = TRUE " +
            "AND (:location = 0 OR u.city = :location)")
    List<String> findAllEmails(Integer location);
}
