package dev.nelon.dreamshops.service.user;

import dev.nelon.dreamshops.exception.AlreadyExistException;
import dev.nelon.dreamshops.exception.ResourceNotFoundException;
import dev.nelon.dreamshops.model.User;
import dev.nelon.dreamshops.repository.UserRepository;
import dev.nelon.dreamshops.request.UserCreateRequest;
import dev.nelon.dreamshops.request.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
	private final UserRepository userRepository;
	
	@Override
	public User getUserById(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> new ResourceNotFoundException("User not found!"));
	}
	
	@Override
	public User createUser(UserCreateRequest request) {
		if (userRepository.existsByEmail(request.getEmail())) {
			throw new AlreadyExistException("Email already exists");
		}
		
		User user = new User();
		user.setEmail(request.getEmail());
		user.setPassword(request.getPassword());
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		
		return userRepository.save(user);
	}

	
	@Override
	public User updateUser(UserUpdateRequest request, Long userId) {
		return userRepository.findById(userId).map(exisingUser -> {
			exisingUser.setFirstName(request.getFirstName());
			exisingUser.setLastName(request.getLastName());
			return userRepository.save(exisingUser);
		}).orElseThrow(() -> new ResourceNotFoundException("User not found!"));
	}
	
	@Override
	public void deleteUser(Long userId) {
		userRepository.findById(userId).ifPresentOrElse(userRepository::delete, () -> {
			throw new ResourceNotFoundException("User not found!");
		});
	}
}
