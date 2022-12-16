package com.masai.model;
import java.util.HashSet;
import javax.persistence.Embedded;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer customerId;
//	static final String userType = "customer";
	
	@Size(max = 15,min = 4,message = "username should be of max 15 and min 4 characrter")
	private String name;

	@Email(message = "Invalid formate of email")
	@Column(unique = true)
	private String email;
	
	@Size(max = 15,min = 4,message = "username should be of max 15 and min 4 characrter")
	private String username;
	
	@Min(value = 6)
	private String password;

	@Embedded
	private Address address;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
	private Set<Order> orders = new HashSet<Order>();

	@Override
	public int hashCode() {
		return Objects.hash(address, email, name, password, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		return Objects.equals(address, other.address) && Objects.equals(email, other.email)
				&& Objects.equals(name, other.name) && Objects.equals(password, other.password)
				&& Objects.equals(username, other.username);
	}
	
	


}
