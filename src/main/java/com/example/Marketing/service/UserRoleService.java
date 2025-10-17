package com.example.Marketing.service;

package com.example.Marketing.service;

import com.example.Marketing.dto.UserRoleRequest;
import com.example.Marketing.dto.UserRoleResponse;

import java.util.List;

public interface UserRoleService {

	List<UserRoleResponse> getAllUserRoles();

	UserRoleResponse getUserRoleById(Integer id);

	UserRoleResponse createUserRole(UserRoleRequest request);

	UserRoleResponse updateUserRole(Integer id, UserRoleRequest request);

	void deleteUserRole(Integer id);
}
