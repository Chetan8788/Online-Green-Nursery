package com.masai.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.masai.model.Customer;

@Repository
public interface CustomerDao extends JpaRepository<Customer, Integer>{
	
	public List<Customer>  findByEmail(String email);;

}
