package com.masai.auth.session;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerSessionDao extends JpaRepository<CustomerSession, String> {

}
