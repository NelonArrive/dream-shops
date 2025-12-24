package dev.nelon.dreamshops.service.user;

import dev.nelon.dreamshops.model.User;
import dev.nelon.dreamshops.request.UserCreateRequest;
import dev.nelon.dreamshops.request.UserUpdateRequest;

public interface IUserService {
	User getUserById(Long userId);
	
	User createUser(UserCreateRequest request);
	
	User updateUser(UserUpdateRequest request, Long userId);
	
	void deleteUser(Long userId);
}
