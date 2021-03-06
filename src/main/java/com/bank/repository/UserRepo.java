package com.bank.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.bank.model.User;

public interface UserRepo extends CrudRepository<User, Long> {
	
	Optional<User> findByUsername(String username);

	boolean existsByUsername(String username);

	boolean existsByEmail(String email);
	

	List<User> findAll();
	
	long count();
}
