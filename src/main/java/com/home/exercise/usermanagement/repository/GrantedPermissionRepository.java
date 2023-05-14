package com.home.exercise.usermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.home.exercise.usermanagement.model.GrantedPermissionKey;
import com.home.exercise.usermanagement.model.GrantedPermissions;

public interface GrantedPermissionRepository extends JpaRepository<GrantedPermissions, GrantedPermissionKey>{
    
}
