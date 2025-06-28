package com.website.websites.service;

import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.website.websites.dto.UserDTO;
import com.website.websites.entity.User;
import com.website.websites.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class UserService {
	private final UserRepository userRepository;
	
	@Cacheable(value = "GetUser" , key = "#id")
	public User getUserById(Long id) {
		Optional<User> userBulunan = userRepository.findById(id);
		if(userBulunan.isPresent()) {
			User user = userBulunan.get();
			return user;
		} else { throw new RuntimeException("User Bulunamadı"); }
	}
	
	@CacheEvict(value = "deleteUser" , key = "#id")
	@Transactional
	public void deleteUser(Long id) {
		Optional<User> userd = userRepository.findById(id);
		if(userd.isPresent()) {
			User user = userd.get();
			userRepository.delete(user);
		} else { throw new RuntimeException("User Bulunamadı"); }
	}
	
	@Transactional
	public void updateUser(Long id , UserDTO dto) {
		Optional<User> userB = userRepository.findById(id);
		if(userB.isPresent()) {
			User user = userB.get();
			user.setUsername(dto.getUsername());
			userRepository.save(user);
		} else { throw new RuntimeException("User Bulunamadı"); }
	}

}
