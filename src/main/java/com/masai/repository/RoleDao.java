package com.masai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.masai.model.Role;

@Repository
public interface RoleDao extends JpaRepository<Role,String>{

}
