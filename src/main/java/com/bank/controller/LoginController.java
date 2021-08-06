package com.bank.controller;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.model.Role;
import com.bank.model.User;
import com.bank.model.UserRole;
import com.bank.repository.RoleRepo;
import com.bank.repository.UserRepo;
import com.bank.request.SignUpFrom;
import com.bank.response.Response;
import com.bank.util.JwtUtil;

@CrossOrigin(origins="*",allowedHeaders="*")
@RestController
@RequestMapping("/auth")
public class LoginController {
	
	
	
	
//	@GetMapping("/welcome")
//	public ResponseEntity<String> hi (){
//		return new ResponseEntity<> ("Welcome to our application",HttpStatus.OK);
//	}
//	
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepo userRepo;

	@Autowired
	RoleRepo roleRepo;
	
//	@Autowired
//	UserService userService;
//	
//	@Autowired
//	AccountService accountService;
//
//	@Autowired
//	PasswordEncoder encoder;

	@Autowired
	private JwtUtil jwtUtil;
	
	@PostMapping("/register")
	public ResponseEntity<Response> registerUser(@Valid @RequestBody SignUpFrom signUpRequest) {
		Response response = new Response();
		//Check username Already exists
		if (userRepo.existsByUsername(signUpRequest.getUsername())) {
			response.setMessage("Username is already taken!");
			response.setSuccess(false);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		//Check email already exists
		if (userRepo.existsByEmail(signUpRequest.getEmail())) {
			response.setMessage("Email is already in use");
			response.setSuccess(false);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		// Creating user's account
		User user = new User(signUpRequest.getFirstName(), signUpRequest.getLastName(), signUpRequest.getUsername(),
				signUpRequest.getEmail(),signUpRequest.getDob(), signUpRequest.getPassword());

		Set<UserRole> userRoles = new HashSet<>();
		Set<String> strRoles = signUpRequest.getRole();
		strRoles.forEach(roleName -> {
			Role role = roleRepo.findByName(roleName).orElseThrow(() -> new RuntimeException("User Role not found."));
			userRoles.add(new UserRole(user, role));
		});
		user.setUserRoles(userRoles);
	//	user.setAccount(accountService.createAccount());
		userRepo.save(user);
		response.setMessage("User Registered Successfully!");
		response.setSuccess(true);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
