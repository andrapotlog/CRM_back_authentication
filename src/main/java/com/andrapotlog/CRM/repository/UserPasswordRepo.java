package com.andrapotlog.CRM.repository;

import com.andrapotlog.CRM.entity.UserPassword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPasswordRepo extends JpaRepository<UserPassword,String> {
    boolean existsByEmail(String email);
}
