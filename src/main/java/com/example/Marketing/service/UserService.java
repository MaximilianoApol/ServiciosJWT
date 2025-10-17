package com.example.Marketing.service;

import java.util.List;
import com.example.Marketing.dto.UserRequest;
import com.example.Marketing.dto.UserResponse;

public interface UserService {

	List<UserResponse> findAll();

	List<UserResponse> findAll(int page, int pageSize);

	UserResponse findById(Integer userId);

	UserResponse findByEmail(String email);

	UserResponse create(UserRequest request);

	UserResponse update(Integer userId, UserRequest request);

	void delete(Integer userId);

	List<UserResponse> findActiveUsers();

	List<UserResponse> searchByName(String name);

	boolean existsByEmail(String email);
}