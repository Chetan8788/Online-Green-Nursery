package com.masai.auth.session;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "customer_session")
public class CustomerSession {
	@Id
	String token;
//	@Column(unique = true)
	Integer customerId;
	LocalDateTime timestamp;
}
