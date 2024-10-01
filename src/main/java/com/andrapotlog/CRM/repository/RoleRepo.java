package com.andrapotlog.CRM.repository;

import com.andrapotlog.CRM.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByRoleName(String name);
}