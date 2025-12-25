package dev.nelon.dreamshops.service.user;

import dev.nelon.dreamshops.dto.UserDto;
import dev.nelon.dreamshops.model.User;
import dev.nelon.dreamshops.request.CreateUserRequest;
import dev.nelon.dreamshops.request.UpdateUserRequest;

public interface IUserService {
	User getUserById(Long userId);
	
	User createUser(CreateUserRequest request);
	
	User updateUser(UpdateUserRequest request, Long userId);
	
	void deleteUser(Long userId);
	
	UserDto convertedToUserDto(User user);
}
