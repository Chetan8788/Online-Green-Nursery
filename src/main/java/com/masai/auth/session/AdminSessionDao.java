package com.masai.auth.session;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminSessionDao extends JpaRepository<AdminSession, String> {
	Optional<AdminSession> findByAdminId(Integer adminId);
}
