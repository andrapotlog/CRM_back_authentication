package com.andrapotlog.CRM.repository;

import com.andrapotlog.CRM.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDataRepo extends JpaRepository<UserData, Long> {
    boolean existsByCnp(String cnp);

    UserData findByEmail(String email);
}
