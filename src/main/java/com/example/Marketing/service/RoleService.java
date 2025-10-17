package com.example.Marketing.service;

import java.util.List;
import com.example.Marketing.dto.RoleRequest;
import com.example.Marketing.dto.RoleResponse;

public interface RoleService {

	List<RoleResponse> findAll();

	RoleResponse findById(Integer roleId);

	RoleResponse findByName(String roleName);

	RoleResponse create(RoleRequest request);

	RoleResponse update(Integer roleId, RoleRequest request);

	void delete(Integer roleId);

	boolean existsByName(String roleName);
}