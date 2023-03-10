package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	private final static Logger log = LoggerFactory.getLogger(UserController.class);

	@GetMapping("/id/{id}")
	public ResponseEntity<User> findById(@PathVariable Long id) {
		Optional<User> user = userRepository.findById(id);

		if (!user.isPresent()) {
			log.warn("findById(" + id  + ") | user not found");
			return ResponseEntity.notFound().build();
		}

		log.info("findById(" + id  + ") | user found");
		return ResponseEntity.ok(user.get());
	}
	
	@GetMapping("/{username}")
	public ResponseEntity<User> findByUserName(@PathVariable String username) {
		User user = userRepository.findByUsername(username);
		log.info("findByUserName(" + username  + ") | user found");
		return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
	}
	
	@PostMapping("/create")
	public ResponseEntity<User> createUser(@RequestBody CreateUserRequest createUserRequest) {
		User user = new User();
		user.setUsername(createUserRequest.getUsername());

		if (!meetsTheRequirements(createUserRequest.getPassword()) ||
		!createUserRequest.getPassword().equals(createUserRequest.getConfirmPassword())) {
			log.error("createUser | error creating user | password requirement not met.");
			return ResponseEntity.badRequest().build();
		}

		user.setPassword(bCryptPasswordEncoder.encode(createUserRequest.getPassword()));
		Cart cart = new Cart();
		cartRepository.save(cart);
		user.setCart(cart);
		userRepository.save(user);

		log.info("createUser() created new user with id: " + user.getId());

		return ResponseEntity.ok(user);
	}

	private boolean meetsTheRequirements(String password) {
		return password != null && password.length() > 7;
	}
}
