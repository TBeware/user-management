package com.home.exercise.usermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.home.exercise.usermanagement.model.Permissions;

public interface PermissionRepository extends JpaRepository<Permissions, Integer>{
    
}
